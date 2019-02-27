package com.coffee.just.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

import androidx.annotation.Nullable;
/**
 * Created by Administrator on 2018/1/12.
 */

public class ClockView extends View{

    private Paint paint;
    private float x = 0;
    private float y = 0;
    private float radio=30;
    private float stroke=20;
    private int alpha=255;
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(radio/3);
        paint.setAlpha(alpha);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x==0 && y==0){
            return;
        }
        canvas.drawCircle(x,y,radio,paint);//参数：坐标+半径
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initPaint();
            alpha=alpha-20;//透明度降低
            radio=radio+10;//半径增大
            if (alpha <= 20){
                alpha=0;
                paint.setAlpha(alpha);
            }else {
                sendEmptyMessageDelayed(0,40);
            }
            invalidate();//强制重绘
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                radio=30;
                stroke=20;
                alpha=255;
                x = event.getX();
                y = event.getY();
                //利用handler机制，设置画笔属性，在很短的时间内重新绘制，达到波纹效果
                handler.sendEmptyMessage(0);
                break;
        }
        return true;
    }
}


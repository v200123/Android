package com.coffee.just.View;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee.just.R;
import com.coffee.just.Service.MyService;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyViewActivity extends Activity implements View.OnClickListener {
    private Button bindService, unbindService, getCount,showNotification,mPlay,mPause;
    private MyService mMyService;
    private MyService.ConnectBinder binder;
    private ServiceConnection mConnection;
    private TextView MusicInformation;
    private MediaPlayer mMediaPlayer;
    private AssetManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myview);
        bindService = findViewById(R.id.bindService);
        unbindService = findViewById(R.id.unbindService);
        bindService.setOnClickListener(this);
        showNotification = findViewById(R.id.showNotification);
        mPlay = findViewById(R.id.PlayMusic);
        mPause = findViewById(R.id.pauseMusic);
        MusicInformation = findViewById(R.id.MusicInformation);

        mPlay .setOnClickListener(this);
        mPause.setOnClickListener(this);

        showNotification.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        getCount = findViewById(R.id.getCount);
        getCount.setOnClickListener(this);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (MyService.ConnectBinder) service;
                mMyService = binder.getMyservice();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

        public void sendMessage(String Message)
        {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = manager.getNotificationChannel("chat");
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    startActivity(intent);
                    Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                }
            }
            Notification notification = new NotificationCompat.Builder(getApplicationContext(),"chat")
                    .setContentTitle("犯罪记录器")
                    .setContentText(Message)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.icon1)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon1))
                    .setAutoCancel(true)
                    .setVibrate(new long[] {0,2000,1000,2000})
                    .build();
            manager.notify(1,notification);


        }

    private void initMusicPlayer(){
        mMediaPlayer = new MediaPlayer();
        File file = new File(Environment.getExternalStorageDirectory(),"Cash Cash,Christina Perri - Hero.mp3");
        Log.d("2", "initMusicPlayer: "+Environment.getExternalStorageDirectory());
        try {
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
    }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bindService:
                Intent intent = new Intent(this, MyService.class);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                if (mMyService != null) {
                    unbindService(mConnection);
                    mMyService=null;
                    Log.d("解除服务", "onClick() returned: " );
                }
                break;

            case R.id.getCount:
                if (mMyService != null) {
                    int i = mMyService.getCount();
                    Log.d("获取Count", "onClick: " + i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"请先绑定服务",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.showNotification:
                    sendMessage("成功弹出");
                break;
            case  R.id.PlayMusic :/*播放音乐*/
                    if (mMediaPlayer==null)
                        initMusicPlayer();
                    if(!mMediaPlayer.isPlaying())
                        mMediaPlayer.start();
                Log.e("3", "onClick: "+"播放了" );
                break;
            case  R.id.pauseMusic:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause(); // 暂停播放
                }
                break;
        }
    }
}

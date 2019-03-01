package com.coffee.just.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service{
    private ConnectBinder mService = new ConnectBinder();
    private int count;
    private final String TAG = "com.Service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mService;
    }

   public class ConnectBinder extends Binder{

        public MyService getMyservice(){
             return MyService.this;
         }
    }
    public  int getCount(){
        return count;
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "MyService    onCreate() called");
        super.onCreate();
        Thread runnable = new Thread(()->{
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            });
        runnable.start();


    }
}

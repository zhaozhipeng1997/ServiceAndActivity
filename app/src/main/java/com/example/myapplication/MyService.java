package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private static boolean running;
    public MyService() {
    }

    public class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }


    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        running=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (running){
                    i++;
                    if(callBack!=null){
                        callBack.onDataChange(i);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        super.onCreate();
    }

    public interface CallBack{
        void onDataChange(int data);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onDestroy() {
        running=false;
        super.onDestroy();
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
   private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tvShowService);
        Button button = (Button) findViewById(R.id.btnBindService);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBindService(tv);
            }
        });
    }
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final MyService.MyBinder binder= (MyService.MyBinder) service;
            MyService myService = binder.getService();
            myService.setCallBack(new MyService.CallBack() {
                public void onDataChange(int data) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("count", data);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void btnBindService(View view) {
        bindService(new Intent(this,MyService.class),connection,BIND_AUTO_CREATE);
    }

    public void btnUnBindService(View view) {
        unbindService(connection);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Integer i = bundle.getInt("count");
            tv.setText(String.valueOf(i));

        }
    };

}

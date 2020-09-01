package com.example.messageruse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Messenger mMessenger = null;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        //startService(...) //可以省略，调用bindService时，如果没有启动，系统会自动启动服务
        Intent intent = new Intent("com.example.messageruse.REMOTE_SERVICE");
        intent.setPackage("com.example.messageruse");//android 5.0后需要显示声明
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder ibinder) {
            mMessenger = new Messenger(ibinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                //创建Message对象
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("client","Hello");
                message.setData(bundle);
                try{
                    mMessenger.send(message);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
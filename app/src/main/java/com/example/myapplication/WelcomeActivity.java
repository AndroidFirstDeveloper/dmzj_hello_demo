package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.browse.BrowseActivity1;

import java.lang.ref.WeakReference;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class WelcomeActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();

        MyHandler handler = new MyHandler(this);
        Message message = new Message();
        message.what = 1000;
        handler.sendMessageDelayed(message, 10);
    }

    private void exit() {
        if (intent != null) {
            String page = intent.getStringExtra("page");
            if (!TextUtils.isEmpty(page)) {
                if (SampleApplication.activityCount > 1) {
                    if (TextUtils.equals("1", page)) {
                        intent.setClass(this, BrowseActivity1.class);
                        startActivity(intent);
                    }
                } else {
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                }
            }else{
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }

    static class MyHandler extends Handler {

        private WeakReference<WelcomeActivity> weakReference;

        public MyHandler(WelcomeActivity welcomeActivity) {
            weakReference = new WeakReference<>(welcomeActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1000) {
                weakReference.get().exit();
            }
        }
    }
}

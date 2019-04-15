package com.example.myapplication.browse;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.lang.ref.WeakReference;

public class BrowseActivity2 extends AppCompatActivity {

    private MyHandler handler=new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse2);
        init();
    }

    private void init() {
        Toolbar activity_browse2_toolbar = findViewById(R.id.activity_browse2_toolbar);
        setSupportActionBar(activity_browse2_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button activity_browse2_btn2 = findViewById(R.id.activity_browse2_btn2);
        activity_browse2_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseActivity2.this, BrowseActivity3.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent2 = new Intent(BrowseActivity2.this, BrowseActivity3.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                    }
                },1000);

            }
        });
    }

    static class MyHandler extends Handler{
        WeakReference<BrowseActivity2> weakReference;
        public MyHandler(BrowseActivity2 activity2){
            weakReference=new WeakReference<>(activity2);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}

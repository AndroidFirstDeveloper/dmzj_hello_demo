package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class SingleTaskActivity2 extends AppCompatActivity {

    private final String TAG = "SingleTaskActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singletask2);
        findView();
    }

    private void findView() {
        Toolbar activity_launch_model_toolbar1 = findViewById(R.id.activity_singletask2_toolbar1);
        activity_launch_model_toolbar1.setTitle("SingleTaskActivity2 设置任务名称");
        setSupportActionBar(activity_launch_model_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_launch_model_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.activity_singletask2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity2.this, SingleTaskActivity2.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: ");
    }
}

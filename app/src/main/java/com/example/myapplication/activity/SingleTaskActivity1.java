package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class SingleTaskActivity1 extends AppCompatActivity {

    private final String TAG = "SingleTaskActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singletask1);
        findView();
    }

    private void findView() {
        Toolbar activity_launch_model_toolbar1 = findViewById(R.id.activity_singletask1_toolbar1);
        activity_launch_model_toolbar1.setTitle("SingleTaskActivity1 未设置任务名称");
        setSupportActionBar(activity_launch_model_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_launch_model_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.activity_singletask1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity1.this, SingleTaskActivity1.class);
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

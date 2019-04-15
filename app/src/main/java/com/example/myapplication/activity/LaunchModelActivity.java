package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class LaunchModelActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_model);
        findView();
    }

    private void findView() {
        Toolbar activity_launch_model_toolbar = findViewById(R.id.activity_launch_model_toolbar);
        setSupportActionBar(activity_launch_model_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity_launch_model_toolbar.setNavigationOnClickListener(this);

        Button activity_launch_model_btn1 = findViewById(R.id.activity_launch_model_btn1);
        Button activity_launch_model_btn2 = findViewById(R.id.activity_launch_model_btn2);
        Button activity_launch_model_btn3 = findViewById(R.id.activity_launch_model_btn3);
        Button activity_launch_model_btn4 = findViewById(R.id.activity_launch_model_btn4);
        Button activity_launch_model_btn5 = findViewById(R.id.activity_launch_model_btn5);
        Button activity_launch_model_btn6 = findViewById(R.id.activity_launch_model_btn6);

        activity_launch_model_btn1.setOnClickListener(this);
        activity_launch_model_btn2.setOnClickListener(this);
        activity_launch_model_btn3.setOnClickListener(this);
        activity_launch_model_btn4.setOnClickListener(this);
        activity_launch_model_btn5.setOnClickListener(this);
        activity_launch_model_btn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_launch_model_btn1:
                Intent intent1 = new Intent(LaunchModelActivity.this, StandardActivity.class);
                startActivity(intent1);
                break;
            case R.id.activity_launch_model_btn2:
                Intent intent2 = new Intent(LaunchModelActivity.this, SingleTopActivity.class);
                startActivity(intent2);
                break;
            case R.id.activity_launch_model_btn3:
                Intent intent3 = new Intent(LaunchModelActivity.this, SingleTaskActivity1.class);
                startActivity(intent3);
                break;
            case R.id.activity_launch_model_btn4:
                Intent intent4 = new Intent(LaunchModelActivity.this, SingleTaskActivity2.class);
                startActivity(intent4);
                break;
            case R.id.activity_launch_model_btn5:
                Intent intent5 = new Intent(LaunchModelActivity.this, SingleInstanceActivity.class);
                startActivity(intent5);
                break;
            case R.id.activity_launch_model_btn6:
                Intent intent6 = new Intent(LaunchModelActivity.this, NewTaskActivity.class);
//                intent6.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent6);
                break;
            default:
                finish();
                break;
        }
    }
}

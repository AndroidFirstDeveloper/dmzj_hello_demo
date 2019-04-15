package com.example.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.myapplication.R;

public class SingleInstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance);
        findView();
    }

    private void findView() {
        Toolbar activity_launch_model_toolbar1 = findViewById(R.id.activity_singleinstance_toolbar1);
        activity_launch_model_toolbar1.setTitle("SingleInstanceActivity");
        setSupportActionBar(activity_launch_model_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_launch_model_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

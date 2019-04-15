package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.myapplication.R;

public class StandardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        findView();
    }

    private void findView() {
        Toolbar activity_standard_toolbar1 = findViewById(R.id.activity_standard_toolbar1);
        activity_standard_toolbar1.setTitle("StandardActivity");
        setSupportActionBar(activity_standard_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_standard_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.activity_standard_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });
    }
}

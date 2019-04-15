package com.example.myapplication.coordinator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.myapplication.R;

public class CoordinatorActivity9 extends AppCompatActivity {

    private Toolbar activity_coordinator9_layout_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator9);
        findView();
        setListener();
    }

    private void findView() {
        activity_coordinator9_layout_toolbar = findViewById(R.id.activity_coordinator9_layout_toolbar);
        activity_coordinator9_layout_toolbar.setTitle("Behavior");
        setSupportActionBar(activity_coordinator9_layout_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setListener() {
        activity_coordinator9_layout_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

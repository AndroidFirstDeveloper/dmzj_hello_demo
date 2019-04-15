package com.example.myapplication.browse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class BrowseActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse1);
        init();
    }

    private void init() {
        Toolbar activity_browse1_toolbar = findViewById(R.id.activity_browse1_toolbar);
        setSupportActionBar(activity_browse1_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button activity_browse1_btn = findViewById(R.id.activity_browse1_btn);
        activity_browse1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseActivity1.this, BrowseActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}

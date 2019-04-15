package com.example.myapplication.browse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class BrowseActivity3 extends AppCompatActivity {

    private final String TAG = "BrowseActivity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse3);
        init();
    }

    private void init() {
        Toolbar activity_browse3_toolbar = findViewById(R.id.activity_browse3_toolbar);
        setSupportActionBar(activity_browse3_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button activity_browse3_btn = findViewById(R.id.activity_browse3_btn);
        activity_browse3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseActivity3.this, BrowseActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                /*Intent intent = new Intent(BrowseActivity3.this, BrowseActivity1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: ");
    }
}

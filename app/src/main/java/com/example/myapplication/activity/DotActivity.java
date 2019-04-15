package com.example.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


import com.example.myapplication.R;


public class DotActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG="DotActivity";
    private Button insertBtn, selectBtn, activity_dot_show_btn;
    private SeekBar activity_dot_seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);
        findView();
        setListener();
    }

    private void findView() {
        Toolbar activity_dot_toolbar = findViewById(R.id.activity_dot_toolbar);
        setSupportActionBar(activity_dot_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        insertBtn = findViewById(R.id.activity_dot_insert_btn);
        selectBtn = findViewById(R.id.activity_dot_select_btn);
        activity_dot_show_btn = findViewById(R.id.activity_dot_select_btn);
        activity_dot_seekbar = findViewById(R.id.activity_dot_seekbar);
    }

    private void setListener() {
        insertBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);
        activity_dot_show_btn.setOnClickListener(this);

        activity_dot_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e(TAG, "onProgressChanged: progress="+progress );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e(TAG, "onProgressChanged: progress="+seekBar.getProgress() );
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e(TAG, "onProgressChanged: progress="+seekBar.getProgress() );
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_dot_insert_btn: {
//                MFDotUtil.addDotDatas(128, "张三", 6328);
            }
            break;
            case R.id.activity_dot_select_btn: {
//                MFDotUtil.getDotDatas();
            }
            break;
            case R.id.activity_dot_show_btn:
                break;
        }
    }
}

package com.example.myapplication.screenchange;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

public class ScreenActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "ScreenActivity";
    private Button activity_screen_btn1, activity_screen_btn2, activity_screen_btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_screen_vertical);
        findView();
    }

    private void findView() {
       /* Toolbar activity_screen_toolbar1 = findViewById(R.id.activity_screen_toolbar1);
        setSupportActionBar(activity_screen_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_screen_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        activity_screen_btn1 = findViewById(R.id.activity_screen_btn1);
        activity_screen_btn2 = findViewById(R.id.activity_screen_btn2);
        activity_screen_btn3 = findViewById(R.id.activity_screen_btn3);

        activity_screen_btn1.setOnClickListener(this);
        activity_screen_btn2.setOnClickListener(this);
        activity_screen_btn3.setOnClickListener(this);

        View activity_screen_vertical_toolbar = findViewById(R.id.activity_screen_vertical_toolbar);
        View title_bar_layout_status_view = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_bar_layout_title_tv = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        ImageView title_bar_layout_back_iv = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_back_iv);
        title_bar_layout_back_iv.setOnClickListener(this);
        title_bar_layout_title_tv.setText("页面A");
        ImmersionBar.with(this).destroy();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarView(title_bar_layout_status_view).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e(TAG, "onStart: 横屏");
        } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e(TAG, "onStart: 竖屏");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e(TAG, "onPause: 横屏");
        } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e(TAG, "onPause: 竖屏");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: ");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_screen_horizontal);
            findView();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_screen_vertical);
            findView();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_screen_btn1:
                Intent intent = new Intent(ScreenActivity.this, ScreenActivity2.class);
                startActivity(intent);
                break;
            case R.id.activity_screen_btn2:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case R.id.activity_screen_btn3:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case R.id.title_bar_layout_back_iv:
                finish();
                break;
        }
    }
}

package com.example.myapplication.screenchange;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

public class ScreenActivity2 extends BaseActivity {

    private final String TAG = "ScreenActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        findView();
    }

    private void findView() {
        /*Toolbar activity_screen_toolbar2 = findViewById(R.id.activity_screen_toolbar2);
        setSupportActionBar(activity_screen_toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_screen_toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        View activity_screen_vertical_toolbar = findViewById(R.id.activity_screen2_vertical_toolbar);
        View title_bar_layout_status_view = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_bar_layout_title_tv = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        ImageView title_bar_layout_back_iv = activity_screen_vertical_toolbar.findViewById(R.id.title_bar_layout_back_iv);
        title_bar_layout_title_tv.setText("页面B");
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarView(title_bar_layout_status_view).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        title_bar_layout_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}

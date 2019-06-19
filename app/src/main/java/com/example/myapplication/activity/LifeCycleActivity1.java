package com.example.myapplication.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

public class LifeCycleActivity1 extends AppCompatActivity implements DialogInterface.OnDismissListener, View.OnClickListener {

    private final String TAG = "LifeActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_life_cycle1);

        View activity_life_cycle1_toolbar = findViewById(R.id.activity_life_cycle1_toolbar);
        View status_view = activity_life_cycle1_toolbar.findViewById(R.id.title_bar_layout_status_view);
        ImmersionBar.with(this).statusBarView(status_view).navigationBarColor(android.R.color.white).init();

        Button activity_life_cycle1_btn1 = findViewById(R.id.activity_life_cycle1_btn1);
        Button activity_life_cycle1_btn2 = findViewById(R.id.activity_life_cycle1_btn2);
        activity_life_cycle1_btn1.setOnClickListener(this);
        activity_life_cycle1_btn2.setOnClickListener(this);
        initFragment();
    }


    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("LifeFragment");
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.activity_life_cycle1_container, new LifeCycleFragment(), "LifeFragment");
            ft.commit();
        }
    }

    private Dialog dialog;
    private String CACHE_DIALOG = "CacheDialog";

    private void showClearCacheDialog(String size) {
        dialog = new AlertDialog.Builder(this, R.style.dialog_bottom_full).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        dialog.setContentView(R.layout.clear_cache_dialog_layout);
        dialog.setCancelable(true);
        dialog.setOnDismissListener(this);
        TextView clear_cache_dialog_layout_title_tv = window.findViewById(R.id.clear_cache_dialog_layout_title_tv);
        TextView clear_cache_dialog_layout_confirm_tv = window.findViewById(R.id.clear_cache_dialog_layout_confirm_tv);
        TextView clear_cache_dialog_layout_cancel_tv = window.findViewById(R.id.clear_cache_dialog_layout_cancel_tv);
        clear_cache_dialog_layout_confirm_tv.setOnClickListener(this);
        clear_cache_dialog_layout_cancel_tv.setOnClickListener(this);
        clear_cache_dialog_layout_title_tv.setText("是否删除缓存数据(" + size + ")？");
        ImmersionBar.with(this, dialog, CACHE_DIALOG).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }


    private void showActivityDialog() {
        startActivity(new Intent(this, DialogTestActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_life_cycle1_btn1: {
                Intent intent = new Intent(LifeCycleActivity1.this, LifeCycleActivity2.class);
                startActivity(intent);
            }
            break;
            case R.id.activity_life_cycle1_btn2: {
                showActivityDialog();
            }
            break;
            case R.id.clear_cache_dialog_layout_confirm_tv:
                dialog.dismiss();
                break;
            case R.id.clear_cache_dialog_layout_cancel_tv:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (dialog != null && dialog instanceof Dialog) {
            ImmersionBar.with(this, (Dialog) dialog, CACHE_DIALOG).destroy();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
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
    }
}

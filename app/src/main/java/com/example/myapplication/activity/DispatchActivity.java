package com.example.myapplication.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DispatchActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "DispatchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch6);
        findView();
        initDialog();
    }

    private void findView() {
        View activity_dispatch6_toolbar = findViewById(R.id.activity_dispatch6_toolbar);
        View status_view = activity_dispatch6_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_view = activity_dispatch6_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        TextView activity_dispatch6_btn = findViewById(R.id.activity_dispatch6_btn);
        title_view.setText("事件分发");
        ImmersionBar.with(this).statusBarView(status_view).init();

        activity_dispatch6_btn.setOnClickListener(this);
    }


    public void onBack(View view) {
        finish();
    }

    private MyRunnable myRunnable = new MyRunnable(this);

    private void initDialog() {
        Dialog dialog = new AlertDialog.Builder(this, R.style.dialog_bottom_full).create();
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.thread_dialog_layout);
        dialog.show();
//        new Thread(myRunnable).start();
    }

    static class MyRunnable implements Runnable {
        WeakReference<DispatchActivity> weakReference;

        public MyRunnable(DispatchActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Dialog dialog = new AlertDialog.Builder(weakReference.get(), R.style.dialog_bottom_full).create();
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.thread_dialog_layout);
            dialog.show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_dispatch6_btn) {
            Intent intent = new Intent(DispatchActivity.this, DispatchActivity.class);
            intent.putExtra("data", 100);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int result = intent.getIntExtra("data", -1);
            Log.e(TAG, "onNewIntent: " + result);
        } else {
            Log.e(TAG, "onNewIntent: ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

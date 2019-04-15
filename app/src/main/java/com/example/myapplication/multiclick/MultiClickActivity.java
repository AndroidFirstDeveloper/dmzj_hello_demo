package com.example.myapplication.multiclick;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.CustomClickListener;
import com.example.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MultiClickActivity extends AppCompatActivity {

    private final String TAG = "MultiClickActivity";
    private MyListener myListener = new MyListener(this, 2000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_click);
        findView();
    }

    private void findView() {
        Toolbar activity_multi_click_toolbar = findViewById(R.id.activity_multi_click_toolbar);
        setSupportActionBar(activity_multi_click_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_multi_click_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button activity_multi_click_btn1 = findViewById(R.id.activity_multi_click_btn1);
        Button activity_multi_click_btn2 = findViewById(R.id.activity_multi_click_btn2);
        activity_multi_click_btn1.setOnClickListener(myListener);
        activity_multi_click_btn2.setOnClickListener(myListener);

        RecyclerView recyclerView = findViewById(R.id.activity_multi_click_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MultiClickAdapter multiClickAdapter = new MultiClickAdapter(this, getData());
        recyclerView.setAdapter(multiClickAdapter);
    }

    private void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.activity_multi_click_btn1: {
                Log.e(TAG, "onSingleClick: 1");
            }
            break;
            case R.id.activity_multi_click_btn2:
                Log.e(TAG, "onSingleClick: 2 ");
                break;
        }
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item   " + i);
        }
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
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

    private static class MyListener extends CustomClickListener {
        WeakReference<Activity> weakReference;

        public MyListener(Activity activity, long time) {
            super(time);
            weakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onSingleClick(View v) {
            if (weakReference.get() instanceof MultiClickActivity) {
                ((MultiClickActivity) weakReference.get()).onViewClicked(v);
            }
        }
    }
}

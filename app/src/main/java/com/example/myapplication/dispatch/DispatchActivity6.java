package com.example.myapplication.dispatch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

public class DispatchActivity6 extends AppCompatActivity {

    private final String TAG = "DispatchActivity6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch7);
        findView();
    }

    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_dispatch6_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("事件分发6");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: -------->" + getActionType(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return false;
//        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: -------->" + getActionType(event.getAction()));
        return super.onTouchEvent(event);
//        return true;
    }

    private String getActionType(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (action == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else if (action == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else if (action == MotionEvent.ACTION_CANCEL) {
            return "ACTION_CANCEL";
        } else {
            return "Other Action";
        }
    }
}

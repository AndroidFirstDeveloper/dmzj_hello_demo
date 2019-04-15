package com.example.myapplication;

import android.os.SystemClock;
import android.view.View;

public abstract class CustomClickListener implements View.OnClickListener {
    private long mLastClickTime = 0;
    private long timeInterval = 1000L;

    public CustomClickListener() {

    }

    public CustomClickListener(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = SystemClock.elapsedRealtime();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        }
    }

    protected abstract void onSingleClick(View v);
}
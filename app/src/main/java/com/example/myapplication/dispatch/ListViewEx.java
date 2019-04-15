package com.example.myapplication.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by blueberry on 2016/6/20.
 * 内部拦截事件
 */
public class ListViewEx extends ListView {

    private float lastXIntercepted, lastYIntercepted;

    private HorizontalEx2 mHorizontalEx2;
    private int counter = 0;

    private final String TAG = "ListViewEx";

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalEx2 getmHorizontalEx2() {
        return mHorizontalEx2;
    }

    public void setmHorizontalEx2(HorizontalEx2 mHorizontalEx2) {
        this.mHorizontalEx2 = mHorizontalEx2;
    }

    /**
     * 使用 outter.requestDisallowInterceptTouchEvent();
     * 来决定父控件是否对事件进行拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        counter++;
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
//                mHorizontalEx2.requestDisallowInterceptTouchEvent(true);
                lastXIntercepted = x;
                lastYIntercepted = y;
                break;
            case MotionEvent.ACTION_MOVE:
//                if (counter % 10 == 1)
                    Log.e(TAG, "ACTION_MOVE");
                final float deltaX = x - lastXIntercepted;
                final float deltaY = y - lastYIntercepted;
//                Log.e(TAG, "deltaX=" + deltaX + "\t\tdeltaY=" + deltaY + "\t\tdeltaX-deltaY=" + (Math.abs(deltaX) - Math.abs(deltaY)));
              /*  if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    mHorizontalEx2.requestDisallowInterceptTouchEvent(false);
                }*/
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                break;
        }
        lastXIntercepted = x;
        lastYIntercepted = y;
        return super.dispatchTouchEvent(ev);
    }
}

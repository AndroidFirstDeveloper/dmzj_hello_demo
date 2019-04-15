package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;

public class DefineToastActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView activity_define_toast_first_tv1, activity_define_toast_first_tv2, activity_define_toast_first_tv3, activity_define_toast_first_tv4;
    private View activity_define_toast_tablayout;
    private View activity_define_toast_indicator_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_toast);
        findView();
    }

    private int tabItemWidth = 0;
    private int indicatorWidth = 0;

    private void findView() {
        View activity_define_toast_status_view = findViewById(R.id.activity_define_toast_status_view);
        activity_define_toast_tablayout = findViewById(R.id.activity_define_toast_tablayout);
        activity_define_toast_first_tv1 = findViewById(R.id.activity_define_toast_first_tv1);
        activity_define_toast_first_tv2 = findViewById(R.id.activity_define_toast_first_tv2);
        activity_define_toast_first_tv3 = findViewById(R.id.activity_define_toast_first_tv3);
        activity_define_toast_first_tv4 = findViewById(R.id.activity_define_toast_first_tv4);
        activity_define_toast_indicator_iv = findViewById(R.id.activity_define_toast_indicator_iv);
        Button activity_define_toast_btn = findViewById(R.id.activity_define_toast_btn);
        activity_define_toast_first_tv1.setOnClickListener(this);
        activity_define_toast_first_tv2.setOnClickListener(this);
        activity_define_toast_first_tv3.setOnClickListener(this);
        activity_define_toast_first_tv4.setOnClickListener(this);
        activity_define_toast_btn.setOnClickListener(this);
        activity_define_toast_first_tv4.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int width = activity_define_toast_first_tv4.getMeasuredWidth();
                if (width > 0) {
                    int totalMargin = dip2px(22) * 8;
                    int totalWidth = width * 4 + totalMargin;
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) activity_define_toast_tablayout.getLayoutParams();
                    layoutParams.width = totalWidth;
                    activity_define_toast_tablayout.setLayoutParams(layoutParams);
                    activity_define_toast_first_tv4.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    tabItemWidth = totalWidth / 4;
                }
            }
        });

        activity_define_toast_indicator_iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int indicatorWid = activity_define_toast_indicator_iv.getMeasuredWidth();
                if (indicatorWid > 0 && tabItemWidth > 0) {
                    indicatorWidth = indicatorWid;
                    activity_define_toast_indicator_iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    final int startMargin = getScreenWidth() / 2 - tabItemWidth * 3 / 2 - indicatorWidth / 2;
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) activity_define_toast_indicator_iv.getLayoutParams();
                    layoutParams.leftMargin = startMargin;
                    activity_define_toast_indicator_iv.setLayoutParams(layoutParams);
                    setTab(0);
                }
            }
        });
        ImmersionBar.with(this).statusBarView(activity_define_toast_status_view).init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_define_toast_btn: {
                Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                TextView textView = new TextView(this);
                SpannableString spannableString = new SpannableString("赠送：1100Mcoin");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 3, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_orange_dark)), 3, spannableString.length(), SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(spannableString);
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                View view = linearLayout.getChildAt(0);
                if (view instanceof TextView) {
                    LinearLayout.LayoutParams linearLayout1 = (LinearLayout.LayoutParams) view.getLayoutParams();
                    linearLayout1.leftMargin = 0;
                    linearLayout1.rightMargin = 0;
                    linearLayout1.topMargin = 0;
                    linearLayout1.rightMargin = 0;
                    linearLayout1.width = 1;
                    linearLayout1.height = 1;
                    view.setLayoutParams(linearLayout1);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 100;
                linearLayout.addView(textView, layoutParams);
                toast.show();
            }
            break;
            case R.id.activity_define_toast_first_tv1:
                setTab(0);
                break;
            case R.id.activity_define_toast_first_tv2:
                setTab(1);
                break;
            case R.id.activity_define_toast_first_tv3:
                setTab(2);
                break;
            case R.id.activity_define_toast_first_tv4:
                setTab(3);
                break;
        }
    }

    private int currentItem = 0;

    private void setTab(int position) {
        if (position == 0) {
            activity_define_toast_first_tv1.setTextSize(22);
            activity_define_toast_first_tv1.setTextColor(Color.parseColor("#333333"));
        } else {
            activity_define_toast_first_tv1.setTextSize(16);
            activity_define_toast_first_tv1.setTextColor(Color.parseColor("#999999"));
        }
        if (position == 1) {
            activity_define_toast_first_tv2.setTextSize(22);
            activity_define_toast_first_tv2.setTextColor(Color.parseColor("#333333"));
        } else {
            activity_define_toast_first_tv2.setTextSize(16);
            activity_define_toast_first_tv2.setTextColor(Color.parseColor("#999999"));
        }
        if (position == 2) {
            activity_define_toast_first_tv3.setTextSize(22);
            activity_define_toast_first_tv3.setTextColor(Color.parseColor("#333333"));
        } else {
            activity_define_toast_first_tv3.setTextSize(16);
            activity_define_toast_first_tv3.setTextColor(Color.parseColor("#999999"));
        }
        if (position == 3) {
            activity_define_toast_first_tv4.setTextSize(22);
            activity_define_toast_first_tv4.setTextColor(Color.parseColor("#333333"));
        } else {
            activity_define_toast_first_tv4.setTextSize(16);
            activity_define_toast_first_tv4.setTextColor(Color.parseColor("#999999"));
        }
        int offsetX = (position - currentItem) * tabItemWidth;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) activity_define_toast_indicator_iv.getLayoutParams();
        layoutParams.leftMargin = offsetX + layoutParams.leftMargin;
        activity_define_toast_indicator_iv.setLayoutParams(layoutParams);
        currentItem = position;
    }

    private int dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }
}

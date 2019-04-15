package com.example.myapplication.constraints;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.lang.ref.WeakReference;

public class ConstraintActivity extends AppCompatActivity implements View.OnClickListener {

    private MyHandler handler;
    private int counter = 0;
    private int counter_ratio = 0;
    private int counter_guideline = 0;
    private Button activity_constraint_button4, activity_constraint_button5, activity_constraint_button11,
            activity_constraint_button12, activity_constraint_button28, activity_constraint_button29,
            activity_constraint_button33, activity_constraint_button34, activity_constraint_button35;

    private ImageView activity_constraint_image_view1, activity_constraint_image_view2, activity_constraint_image_view3;
    private Guideline activity_constraint_guideline1;
    private Group activity_constraint_group;
    private StringBuilder stringBuilder = new StringBuilder("BBBBBBBBBBB");
    private StringBuilder stringBuilder2 = new StringBuilder("AAAAAAAAAAA");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        findViews();
        setListener();
        initData();
    }

    private void findViews() {
        handler = new MyHandler(this);
        activity_constraint_button4 = findViewById(R.id.activity_constraint_button4);
        activity_constraint_button5 = findViewById(R.id.activity_constraint_button5);
        activity_constraint_button11 = findViewById(R.id.activity_constraint_button11);
        activity_constraint_button12 = findViewById(R.id.activity_constraint_button12);
        activity_constraint_image_view1 = findViewById(R.id.activity_constraint_image_view1);
        activity_constraint_image_view2 = findViewById(R.id.activity_constraint_image_view2);
        activity_constraint_image_view3 = findViewById(R.id.activity_constraint_image_view3);
        activity_constraint_button28 = findViewById(R.id.activity_constraint_button28);
        activity_constraint_guideline1 = findViewById(R.id.activity_constraint_guideline1);
        activity_constraint_button29 = findViewById(R.id.activity_constraint_button29);
        activity_constraint_group = findViewById(R.id.activity_constraint_group);
        activity_constraint_button33 = findViewById(R.id.activity_constraint_button33);
        activity_constraint_button34 = findViewById(R.id.activity_constraint_button34);
        activity_constraint_button35 = findViewById(R.id.activity_constraint_button35);


    }

    private void setListener() {
        activity_constraint_button5.setOnClickListener(this);
        activity_constraint_button11.setOnClickListener(this);
        activity_constraint_button12.setOnClickListener(this);
        activity_constraint_button28.setOnClickListener(this);
        activity_constraint_button29.setOnClickListener(this);
        activity_constraint_button33.setOnClickListener(this);
        activity_constraint_button35.setOnClickListener(this);

    }

    private void initData() {
        Glide.with(this)
                .load("http://s13.sinaimg.cn/mw690/001nNDxEgy6Rb2ZJf2Y5c&690")
                .into(activity_constraint_image_view1);
        Glide.with(this)
                .load("http://s13.sinaimg.cn/mw690/001nNDxEgy6Rb2ZJf2Y5c&690")
                .into(activity_constraint_image_view2);
        Glide.with(this)
                .load("http://s13.sinaimg.cn/mw690/001nNDxEgy6Rb2ZJf2Y5c&690")
                .into(activity_constraint_image_view3);

    }

    private final String TAG = "ConstraintActivity";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_constraint_button5:
                if (activity_constraint_button4.getVisibility() == View.GONE) {
                    activity_constraint_button4.setVisibility(View.VISIBLE);
                } else {
                    activity_constraint_button4.setVisibility(View.GONE);
                }
               /* //测试as断点功能
                int sum = 0;
                for (int i = 0; i < 20; i++) {
                    sum += i;
                    sum *= 2;
                }
                Log.e(TAG, "sum=" + sum);*/
                break;
            case R.id.activity_constraint_button11:
                counter++;
                if (counter % 3 == 0) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button11.getLayoutParams();
                    layoutParams.leftToLeft = R.id.activity_constraint_button10;
                    layoutParams.rightToRight = R.id.activity_constraint_button10;
                    activity_constraint_button11.setLayoutParams(layoutParams);
                } else if (counter % 3 == 1) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button11.getLayoutParams();
                    layoutParams.leftToLeft = R.id.activity_constraint_button9;
                    layoutParams.rightToRight = R.id.activity_constraint_button9;
                    activity_constraint_button11.setLayoutParams(layoutParams);
                } else if (counter % 3 == 2) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button11.getLayoutParams();
                    layoutParams.leftToLeft = 0;
                    layoutParams.rightToRight = 0;
                    activity_constraint_button11.setLayoutParams(layoutParams);
                }
                break;
            case R.id.activity_constraint_button12:
                counter_ratio++;
                if (counter_ratio % 3 == 0) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button12.getLayoutParams();
                    layoutParams.dimensionRatio = "2:1";
                    activity_constraint_button12.setLayoutParams(layoutParams);
                } else if (counter_ratio % 3 == 1) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button12.getLayoutParams();
                    layoutParams.dimensionRatio = "4:1";
                    activity_constraint_button12.setLayoutParams(layoutParams);
                } else if (counter_ratio % 3 == 2) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_button12.getLayoutParams();
                    layoutParams.dimensionRatio = "8:1";
                    activity_constraint_button12.setLayoutParams(layoutParams);
                }
                break;
            case R.id.activity_constraint_button28:
                counter_guideline++;
                if (counter_guideline % 3 == 0) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_guideline1.getLayoutParams();
                    layoutParams.guidePercent = 0.2f;
                    activity_constraint_guideline1.setLayoutParams(layoutParams);
                } else if (counter_guideline % 3 == 1) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_guideline1.getLayoutParams();
                    layoutParams.guidePercent = 0.4f;
                    activity_constraint_guideline1.setLayoutParams(layoutParams);
                } else if (counter_guideline % 3 == 2) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activity_constraint_guideline1.getLayoutParams();
                    layoutParams.guidePercent = 0.5f;
                    activity_constraint_guideline1.setLayoutParams(layoutParams);
                }
                break;
            case R.id.activity_constraint_button29:
                activity_constraint_group.setVisibility(View.GONE);
                handler.sendEmptyMessageDelayed(1, 2000);
                /*//为了测试handler发送的消息和发送消息后的代码执行顺序
                handler.sendEmptyMessage(1);
                activity_constraint_group.setTag(1);
                int sum = 0;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    sum += i;
                    int multi = sum / 3 + 100;
                    int maxNumber = Math.max(sum, multi);
                    String str = String.valueOf(maxNumber);
                    Log.e(TAG, "maxNum=" + str);
                }*/
                break;
            case R.id.activity_constraint_button33:
                stringBuilder.append("BBBBBBBBBBB");
                activity_constraint_button33.setText(stringBuilder.toString());
                break;
            case R.id.activity_constraint_button35:
                stringBuilder2.append("AAAAAAAAAAA");
                activity_constraint_button34.setText(stringBuilder2.toString());
                break;
        }
    }


    private void updateGroup() {
        activity_constraint_group.setVisibility(View.VISIBLE);
    }

    private static class MyHandler extends Handler {
        private WeakReference<ConstraintActivity> weakReference;

        public MyHandler(ConstraintActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                weakReference.get().updateGroup();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }




}

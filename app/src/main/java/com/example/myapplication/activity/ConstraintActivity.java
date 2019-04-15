package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.myapplication.R;

public class ConstraintActivity extends AppCompatActivity {

    private ImageView activity_constraint_iv1, activity_constraint_iv2, activity_constraint_iv3, activity_constraint_iv4;

    private final String TAG = "ConstraintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint2);
        findView();
    }

    private void findView() {
        Toolbar activity_constraint_toolbar1 = findViewById(R.id.activity_constraint_toolbar1);
        activity_constraint_toolbar1.setTitle("约束布局");
        setSupportActionBar(activity_constraint_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_constraint_toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_constraint_iv1 = findViewById(R.id.activity_constraint_iv1);
        activity_constraint_iv2 = findViewById(R.id.activity_constraint_iv2);
        activity_constraint_iv3 = findViewById(R.id.activity_constraint_iv3);
        activity_constraint_iv4 = findViewById(R.id.activity_constraint_iv4);

        activity_constraint_iv1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width1 = activity_constraint_iv1.getMeasuredWidth();
                int height1 = activity_constraint_iv1.getMeasuredHeight();
                if (width1 > 0 && height1 > 0) {
                    Log.e(TAG, "onGlobalLayout: width1=" + width1 + "\theight1=" + height1);
                    activity_constraint_iv1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        activity_constraint_iv2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width2 = activity_constraint_iv2.getMeasuredWidth();
                int height2 = activity_constraint_iv2.getMeasuredHeight();
                if (width2 > 0 && height2 > 0) {
                    Log.e(TAG, "onGlobalLayout: width2=" + width2 + "\theight2=" + height2);
                    activity_constraint_iv2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        activity_constraint_iv3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width3 = activity_constraint_iv3.getMeasuredWidth();
                int height3 = activity_constraint_iv3.getMeasuredHeight();
                if (width3 > 0 && height3 > 0) {
                    Log.e(TAG, "onGlobalLayout: width3=" + width3 + "\theight3=" + height3);
                    activity_constraint_iv3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        activity_constraint_iv4.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width4 = activity_constraint_iv4.getMeasuredWidth();
                int height4 = activity_constraint_iv4.getMeasuredHeight();
                if (width4 > 0 && height4 > 0) {
                    Log.e(TAG, "onGlobalLayout: width4=" + width4 + "\theight4=" + height4);
                    activity_constraint_iv4.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

}

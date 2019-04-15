package com.example.myapplication.coordinator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class CoordinatorActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator3);
        findviews();
    }

    private void findviews() {
        TextView textView1 = (TextView) findViewById(R.id.activity_coordinator3_tv1);
        TextView textView2 = (TextView) findViewById(R.id.activity_coordinator3_tv2);
        textView1.setText("CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：" + "作为顶层布局；" + "作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。");

        textView2.setText("CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：" + "作为顶层布局；" + "作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。" +
                "CoordinatorLayout 作为一个 “super-powered FrameLayout”，主要有以下两个作用：\" + \"作为顶层布局；\" + \"作为协调子 View 之间交互的容器。");
    }

    public void back(View view) {
        this.finish();
    }

    public void next(View view){
        Intent intent=new Intent(this,CoordinatorActivity4.class);
        startActivity(intent);
    }
}

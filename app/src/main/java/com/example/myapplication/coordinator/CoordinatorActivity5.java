package com.example.myapplication.coordinator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class CoordinatorActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator5);
        findviews();
    }

    private void findviews() {
        TextView textView1 = (TextView) findViewById(R.id.activity_coordinator5_tv1);
        TextView textView2 = (TextView) findViewById(R.id.activity_coordinator5_tv2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_coordinator5_toolbar);
        toolbar.setTitle("协调布局5");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoordinatorActivity5.this.finish();
            }
        });

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

    public void next(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nextmenu_next:
                Intent intent=new Intent(this,CoordinatorActivity6.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

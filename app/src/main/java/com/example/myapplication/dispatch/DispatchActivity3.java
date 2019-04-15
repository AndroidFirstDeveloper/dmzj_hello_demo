package com.example.myapplication.dispatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.layouts.ViewStubAdapter;

public class DispatchActivity3 extends AppCompatActivity {

    ImageView activity_dispatch3_imageview;
    RecyclerView activity_dispatch3_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch3);
        findView();
        initData();
    }

    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_dispatch3_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("事件分发3");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_dispatch3_imageview = findViewById(R.id.activity_dispatch3_imageview);
        activity_dispatch3_recyclerview = findViewById(R.id.activity_dispatch3_recyclerview);

        activity_dispatch3_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HorizontalAdapter adapter = new HorizontalAdapter(this);
        activity_dispatch3_recyclerview.setAdapter(adapter);
    }

    private void initData() {
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545828409586&di=65be4035a7a95f235433c3751543f941&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F39%2F74%2F87v58PICKBP_1024.jpg")
                .into(activity_dispatch3_imageview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextmenu_next:
                Intent intent = new Intent(DispatchActivity3.this, DispatchActivity4.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

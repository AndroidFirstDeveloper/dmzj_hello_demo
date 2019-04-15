package com.example.myapplication.dispatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.R;
import com.example.myapplication.layouts.ViewStubAdapter;

public class DispatchActivity4 extends AppCompatActivity {
    private ImageView activity_dispatch4_image_view1, activity_dispatch4_image_view2;
    private RecyclerView activity_dispatch4_recycler_view;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch4);
        initParams();
        findView();
        initData();
    }

    private void initParams() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display display = windowManager.getDefaultDisplay();
            display.getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
        } else {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            screenWidth = display.getWidth();
        }
    }

    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_dispatch4_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("事件分发4");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activity_dispatch4_image_view1 = findViewById(R.id.activity_dispatch4_image_view1);
        activity_dispatch4_image_view2 = findViewById(R.id.activity_dispatch4_image_view2);
        activity_dispatch4_recycler_view = findViewById(R.id.activity_dispatch4_recycler_view);
    }

    private void initData() {
        Glide.with(this)
                .asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545891810671&di=f0fb3f619dc49d7d08fe9a1d3535a8cf&imgtype=0&src=http%3A%2F%2Fp.chanyouji.cn%2F1427425953%2FFF0BD91A-00D7-4896-9881-A8A73CC9DE71.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        final int imageWidth = resource.getWidth();
                        final int imageHeight = resource.getHeight();
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) activity_dispatch4_image_view1.getLayoutParams();
                        layoutParams.width = screenWidth;
                        layoutParams.height = (int) ((screenWidth * imageHeight * 1.0f) / imageWidth);
                        activity_dispatch4_image_view1.setLayoutParams(layoutParams);
                        activity_dispatch4_image_view1.setImageBitmap(resource);
                    }
                });
        Glide.with(this)
                .asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545891834516&di=6444545b0b4c44c571726d394a698ef3&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4610b912c8fcc3ce9f0398c69845d688d53f2007.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        final int imageWidth = resource.getWidth();
                        final int imageHeight = resource.getHeight();
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) activity_dispatch4_image_view2.getLayoutParams();
                        layoutParams.width = screenWidth;
                        layoutParams.height = (int) ((screenWidth * imageHeight * 1.0f) / imageWidth);
                        activity_dispatch4_image_view2.setLayoutParams(layoutParams);
                        activity_dispatch4_image_view2.setImageBitmap(resource);
                    }
                });

        ViewStubAdapter viewStubAdapter = new ViewStubAdapter(this);
        activity_dispatch4_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activity_dispatch4_recycler_view.setAdapter(viewStubAdapter);
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
                Intent intent = new Intent(DispatchActivity4.this, DispatchActivity5.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

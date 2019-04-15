package com.example.myapplication.layouts;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.myapplication.R;

import java.lang.ref.WeakReference;

public class ViewStubActivity extends AppCompatActivity implements OnLoadMoreListener, View.OnClickListener {

    private RecyclerView activity_view_stub_recycler_view;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ViewStub viewStub;
    private View netErrorView;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        findViews();
        setListener();
        initData();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.activity_view_stub_toolbar);
        toolbar.setTitle("ViewStub");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewStub = findViewById(R.id.activity_view_stub_vs);
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);

        activity_view_stub_recycler_view = findViewById(R.id.swipe_target);
        activity_view_stub_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setListener() {
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void initData() {
        ViewStubAdapter viewStubAdapter = new ViewStubAdapter(this);
        activity_view_stub_recycler_view.setAdapter(viewStubAdapter);

        handler = new MyHandler(this);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                if (netErrorView == null) {
                    netErrorView = viewStub.inflate();
                    ImageView imageView = netErrorView.findViewById(R.id.net_error_layout_imageview);
                    imageView.setOnClickListener(ViewStubActivity.this);
                }
                netErrorView.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.net_error_layout_imageview) {
            if (netErrorView != null) {
                netErrorView.setVisibility(View.GONE);
            }
        }
    }

    public static class MyHandler extends Handler {
        public WeakReference<ViewStubActivity> weakReference;

        public MyHandler(ViewStubActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

            }
        }
    }
}

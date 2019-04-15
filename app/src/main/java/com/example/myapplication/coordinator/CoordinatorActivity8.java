package com.example.myapplication.coordinator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorActivity8 extends AppCompatActivity {

    private RecyclerView recycler_view;
    private CoordinatorActivity8.MyAdapter myAdapter;
    private List<String> list;
    private Toolbar activity_coordinator8_toolbar;
    private ImageView activity_coordinator8_bg_image_view;
    private CollapsingToolbarLayout activity_coordinator8_collapsing_layout;
    private AppBarLayout activity_coordinator8_appbar_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator8);
        findView();
        setListener();
        initData();
    }

    private void findView() {
        activity_coordinator8_toolbar = findViewById(R.id.activity_coordinator8_toolbar);
        activity_coordinator8_toolbar.setTitle("Coordinator");
        recycler_view = findViewById(R.id.activity_coordinator8_recycler_view);
        setSupportActionBar(activity_coordinator8_toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity_coordinator8_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);

        activity_coordinator8_bg_image_view = findViewById(R.id.activity_coordinator8_bg_image_view);
        activity_coordinator8_collapsing_layout = findViewById(R.id.activity_coordinator8_collapsing_layout);
        activity_coordinator8_appbar_layout = findViewById(R.id.activity_coordinator8_appbar_layout);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("item " + i);
        }
        myAdapter = new CoordinatorActivity8.MyAdapter(this, list);
        recycler_view.setAdapter(myAdapter);
    }

    private void setListener() {
        activity_coordinator8_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Glide.with(this)
                .asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378047620&di=95b6db2f9bec723a80dfa7b42c28a77a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Dbf7d77745ffbb2fb2026505127234ad1%2F42166d224f4a20a4a0b043ee9a529822720ed069.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int screenWidth;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            screenWidth = displayMetrics.widthPixels;
                        } else {
                            screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                        }
                        int appbarHeight = (int) (screenWidth * imageHeight * 1.0f / imageWidth);
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) activity_coordinator8_appbar_layout.getLayoutParams();
                        layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT;
                        layoutParams.height = appbarHeight;
                        activity_coordinator8_appbar_layout.setLayoutParams(layoutParams);
                        activity_coordinator8_bg_image_view.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextmenu_next:
                Intent intent = new Intent(CoordinatorActivity8.this, CoordinatorActivity9.class);
                startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends RecyclerView.Adapter<CoordinatorActivity8.MyAdapter.MyViewHolder> {
        private Context context;
        private List<String> list;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public CoordinatorActivity8.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.coordinator7_item_layout, parent, false);
            return new CoordinatorActivity8.MyAdapter.MyViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(@NonNull CoordinatorActivity8.MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.coordinator7_item_layout_tv);
            }
        }
    }

}

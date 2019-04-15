package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.view.MyImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity implements OnRefreshListener {

    private final String TAG = "BannerActivity";
    private ViewPager top_cartoon_recommend_layout_view_pager;
    private LinearLayout top_cartoon_recommend_layout_indicator;
    private SwipeToLoadLayout swipeToLoadLayout;
    private Toolbar activity_banner_toolbar;
    private int VIEW_PAGER_HEIGHT = 0;
    private MyHandler myHandler = new MyHandler();
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        findView();
        initData();
        setListener();
    }

    private void findView() {
        activity_banner_toolbar = findViewById(R.id.activity_banner_toolbar);
        top_cartoon_recommend_layout_view_pager = findViewById(R.id.top_cartoon_recommend_layout_view_pager);
        top_cartoon_recommend_layout_indicator = findViewById(R.id.top_cartoon_recommend_layout_indicator);
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        setSupportActionBar(activity_banner_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final int VIEW_PAGER_WIDTH = getScreenWidth() - dip2px(this, 20 * 2);
        VIEW_PAGER_HEIGHT = (int) ((VIEW_PAGER_WIDTH * 414 * 1.0f) / 670);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) top_cartoon_recommend_layout_view_pager.getLayoutParams();
        layoutParams.height = VIEW_PAGER_HEIGHT;
        Log.e(TAG, "findView:VIEW_PAGER_HEIGHT= " + VIEW_PAGER_HEIGHT);
        top_cartoon_recommend_layout_view_pager.setLayoutParams(layoutParams);
        top_cartoon_recommend_layout_view_pager.setPageMargin(dip2px(this, 10));
        top_cartoon_recommend_layout_view_pager.setOffscreenPageLimit(3);
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("https://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/5/pic_2f63b7014224a2eaea4f42ef394e46f7.jpg@!auto");
        list.add("https://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/72/pic_12b2ad18ef103576d34afc67e6d16bf0.jpg@!auto");
        list.add("https://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/73/pic_0e65c6c6087155447740112b50d22f53.jpg@!auto");
        list.add("https://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/114/pic_edb81cb3fd104e86e7ac0cc371ce8210.jpg@!auto");
        list.add("https://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/113/pic_ab97ab4189d99e54840be5b064eb2630.jpg@!auto");
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(list, this);
        top_cartoon_recommend_layout_view_pager.setAdapter(myPagerAdapter);
        startImageTimerTask();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        top_cartoon_recommend_layout_view_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
//                            Toast.makeText(BannerActivity.this, "action_up", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onTouch: ACTION_UP");
                        // 开始图片滚动
                        if (!swipeToLoadLayout.isRefreshing()) {
                            startImageTimerTask();
                        }
                        break;
                    default:
                        Log.e(TAG, "onTouch: other action");
                        // 停止图片滚动
                        if (!swipeToLoadLayout.isRefreshing()) {
                            stopImageTimerTask();
                        }
                        break;
                }
                return false;
            }
        });

        activity_banner_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeToLoadLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        stopImageTimerTask();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                Log.e(TAG, "run: isRefresh=" + swipeToLoadLayout.isRefreshing());
                initData();
            }
        }, 300);
    }

    static class MyPagerAdapter extends PagerAdapter {
        private List<String> list;
        private WeakReference<BannerActivity> weakReference;

        public MyPagerAdapter(List<String> list, BannerActivity activity) {
            this.list = list;
            weakReference = new WeakReference<>(activity);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MyImageView imageView = new MyImageView(weakReference.get());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(android.R.color.holo_green_dark);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final int index = position % list.size();
            Glide.with(weakReference.get())
                    .load(list.get(index))
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(weakReference.get(), "Banner图 " + index, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((MyImageView) object);
        }
        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
//        stopImageTimerTask();
        // 图片滚动
        isStop = false;
        myHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        if (!isStop) {
            isStop = true;
            myHandler.removeCallbacks(mImageTimerTask);
        }
    }

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            top_cartoon_recommend_layout_view_pager.setCurrentItem(top_cartoon_recommend_layout_view_pager.getCurrentItem() + 1);
            if (!isStop) {  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                myHandler.postDelayed(mImageTimerTask, 3000);
            }
        }
    };

    private int getScreenWidth() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;
        return width2;
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}

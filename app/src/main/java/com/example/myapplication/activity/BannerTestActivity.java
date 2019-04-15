package com.example.myapplication.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BannerTestActivity extends AppCompatActivity implements OnRefreshListener, OnBannerListener {

    private final String TAG = "BannerTestActivity";
    private Banner banner;
    private SwipeToLoadLayout activity_banner_test_swipelayout;
    private MyHandler handler = new MyHandler(this);
    private List<String> images = new ArrayList<>();
    private String[] imageArray = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554190984543&di=42a560cef5863cc650470b16c9974f74&imgtype=0&src=http%3A%2F%2Fpic.lvmama.com%2Ftrip%2Fpc%2Fplace2%2F2014-12-16%2F4dda9862-5eab-4989-92a6-968cab8c7d87.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554191030603&di=cdf8c3a3c8da2f5dd976b439ce3114d7&imgtype=0&src=http%3A%2F%2F2990273.s21i-2.faidns.com%2F2%2FABUIABACGAAg2qy4qQUotbbnvwIw-wU4jQM.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554785768&di=815e9778460a0e0977f90b9ec18a0496&imgtype=jpg&er=1&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ftg%2F665%2F620%2F476%2Fc34b66cea9864ea196a9d489ee098ba7.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554191064484&di=383120971efe44390c917534f93d3194&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0162d05548ccd50000019ae971908f.jpg%401280w_1l_2o_100sh.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554785799&di=5eaff9e67fec6700e82d01a22d71c2c8&imgtype=jpg&er=1&src=http%3A%2F%2Fs3.lvjs.com.cn%2Fuploads%2Fpc%2Fplace2%2F2015-12-21%2Ff2790a0c-274b-4ade-882e-d50672284046.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554191095110&di=e566bd50230b07704b3a9b42a39aa0f2&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F150822%2F14-150R2145630Z1.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554191120479&di=8264e31856dcc3681658e01d296b9cb6&imgtype=0&src=http%3A%2F%2Fp.chanyouji.cn%2F101943%2F1394115632515p18ibs3fjugdui8t1bl3cv1n8uf.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554191143916&di=ff40c03c5c660248cf22927267eda71c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01397256de516f6ac72531cba95b27.jpg%401280w_1l_2o_100sh.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_test);
        findView();
        setListener();
    }

    private void findView() {
        activity_banner_test_swipelayout = findViewById(R.id.activity_banner_test_swipelayout);
        banner = findViewById(R.id.activity_banner_test_banner);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setDelayTime(2000);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setOnBannerListener(this);
        banner.start();
    }

    private void setListener() {
        activity_banner_test_swipelayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        requestImages();
    }

    private void requestImages() {
        banner.isAutoPlay(false);
        banner.stopAutoPlay();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity_banner_test_swipelayout.setRefreshing(false);
                int count = (int) ((Math.random() * 6) + 2);
                List<String> myImages = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    myImages.add(imageArray[i]);
                }
                banner.setOffscreenPageLimit(count);
                banner.isAutoPlay(true);
                banner.update(myImages);
            }
        }, 2000);
    }

    @Override
    public void OnBannerClick(int position) {
        Log.e(TAG, "OnBannerClick: ");
    }

    private static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load((String) path).into(imageView);
        }
    }

    static class MyHandler extends Handler {
        WeakReference<BannerTestActivity> weakReference;

        public MyHandler(BannerTestActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        if (banner != null) {
            banner.stopAutoPlay();
        }
        super.onDestroy();
    }
}

package com.example.myapplication.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity2 extends AppCompatActivity implements OnRefreshListener {

    private SwipeToLoadLayout activity_banner2_swipe_layout;
    private MyHandler myHandler = new MyHandler();
    private Banner banner;

    private final String path1 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2108161404,2776888218&fm=26&gp=0.jpg";
    private final String path2 = "http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/114/pic_edb81cb3fd104e86e7ac0cc371ce8210.jpg@!q90_no_make";
    private final String path3 = "http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/113/pic_6f3ecac919be66e08ee67fb3ca33a7d0.jpg@!q90_no_make";
    private final String path4 = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2108161404,2776888218&fm=26&gp=0.jpg";
    private final String path5 = "http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/5/pic_2f63b7014224a2eaea4f42ef394e46f7.jpg@!q90_no_make";
    private final String path6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550981832630&di=5a61a7e42159f5ecb4ccc312c58f5521&imgtype=0&src=http%3A%2F%2Fwww.pptbz.com%2Fpptpic%2FUploadFiles_6909%2F201203%2F2012031220134655.jpg";

    String[] pathArray = new String[]{path1, path2, path3, path4, path5, path6};
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner2);

        Toolbar activity_banner2_toolbar = findViewById(R.id.activity_banner2_toolbar);
        setSupportActionBar(activity_banner2_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findView();
        init();
    }

    private void findView() {
        activity_banner2_swipe_layout = findViewById(R.id.activity_banner2_swipe_layout);
        activity_banner2_swipe_layout.setOnRefreshListener(this);
    }

    private void init() {
        List<String> images = new ArrayList<>();
        images.add("http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/114/pic_edb81cb3fd104e86e7ac0cc371ce8210.jpg@!q90_no_make");
        images.add("http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/113/pic_6f3ecac919be66e08ee67fb3ca33a7d0.jpg@!q90_no_make");
        images.add("http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/5/pic_2f63b7014224a2eaea4f42ef394e46f7.jpg@!q90_no_make");
        images.add("http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/72/pic_0ec9610f209c04771bbaf98ffc56f75b.jpg@!q90_no_make");
        images.add("http://cdnmyfcomicadmin.myfcomic.com/images/upin_recommends/6/pic_2939bc2d6545225da03261c558b571d7.jpg@!q90_no_make");

        banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

           /* //Picasso 加载图片简单用法
            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);*/
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        /*@Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }*/
    }

    @Override
    public void onRefresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity_banner2_swipe_layout.setRefreshing(false);
                currentPage++;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    list.add(pathArray[currentPage % 6]);
                }
                banner.update(list);
            }
        }, 2000);
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}

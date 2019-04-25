package com.example.myapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.myapplication.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initSystemUI();
        findView();
    }

    private void initSystemUI() {
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    private void findView() {

//        ImageView activity_guid_iv = findViewById(R.id.activity_guid_iv);
//        Glide.with(this)
//                .load(R.mipmap.guide_page1)
//                .into(activity_guid_iv);


        ViewPager image_preview_item_layout_vp = findViewById(R.id.image_preview_item_layout_vp);
        image_preview_item_layout_vp.setAdapter(new MyAdapter(this));
    }


    private static class MyAdapter extends PagerAdapter {
        private Context context;
        private Integer[] ids = new Integer[]{R.mipmap.guide_page1, R.mipmap.guide_page2, R.mipmap.guide_page3, R.mipmap.guide_page4,R.mipmap.guide_page5};

        public MyAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            return ids == null ? 0 : ids.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View convertview = LayoutInflater.from(context).inflate(R.layout.image_preview_item_layout, container, false);
            final ImageView image_preview_item_layout_iv = convertview.findViewById(R.id.image_preview_item_layout_iv);
//            image_preview_item_layout_iv.setTag(position);
            container.addView(convertview);
            Glide.with(context)
                    .load(ids[position])
                    .into(image_preview_item_layout_iv);
            return convertview;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
//        title_tv.setText((position + 1) + "/" + images.size());
    }

}

package com.example.myapplication.zoomtest;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;

public class ZoomImageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    int resIds[] = {R.drawable.dmzj_image0, R.drawable.dmzj_image0, R.drawable.dmzj_image0, R.drawable.dmzj_image0, R.drawable.dmzj_image0, R.drawable.dmzj_image0};
    int colorIds[]={android.R.color.holo_red_dark,android.R.color.holo_orange_dark,android.R.color.holo_green_dark,android.R.color.holo_green_light,
            android.R.color.holo_blue_dark,android.R.color.holo_purple};
    ImageView[] imageViews = new ImageView[resIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        findViews();
    }

    private void findViews() {
        viewPager = (ViewPager) this.findViewById(R.id.activity_zoom_image_viewpager);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView zoomImageView=new ZoomImageView(getApplicationContext());
                zoomImageView.setImageResource(resIds[position]);
                zoomImageView.setBackgroundColor(getResources().getColor(colorIds[position]));
                container.addView(zoomImageView);
                imageViews[position]=zoomImageView;
                return zoomImageView;

                /*ScaleImageView scaleImageView=new ScaleImageView(getApplicationContext());
                scaleImageView.setImageResource(resIds[position]);
                scaleImageView.setBackgroundColor(getResources().getColor(colorIds[position]));
                container.addView(scaleImageView);
                imageViews[position]=scaleImageView;
                return scaleImageView;*/
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews[position]);
            }

            @Override
            public int getCount() {
                return imageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view==object);
            }
        });
    }
}

package com.example.myapplication.refresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.myapplication.R;

public class RefreshView extends RelativeLayout implements SwipeTrigger, SwipeRefreshTrigger {

    private ImageView refreshImageView;
    private AnimationDrawable animationDrawable;

    public RefreshView(Context context) {
        this(context,null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setBackgroundColor(Color.WHITE);
        if(refreshImageView==null)
            refreshImageView=new ImageView(context);
        refreshImageView.setBackgroundResource(R.drawable.refresh_animation);
        animationDrawable=(AnimationDrawable)refreshImageView.getBackground();
        LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        addView(refreshImageView,params);
    }

   /* @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refreshImageView = (ImageView) findViewById(R.id.refresh_header_layout_imageview);
        animationDrawable = (AnimationDrawable) refreshImageView.getBackground();
    }*/

    @Override
    public void onRefresh() {
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

    }

    @Override
    public void onRelease() {
        if (!animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        animationDrawable.stop();
    }
}

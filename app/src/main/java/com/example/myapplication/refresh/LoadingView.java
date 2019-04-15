package com.example.myapplication.refresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.myapplication.R;

public class LoadingView extends RelativeLayout implements SwipeLoadMoreTrigger, SwipeTrigger {
    private ImageView myimageView;
    private AnimationDrawable myanimationDrawable;

    public LoadingView(Context context) {
//        super(context);
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        if (myimageView == null)
            myimageView = new ImageView(context);
        myimageView.setBackgroundResource(R.drawable.refresh_animation);
        myanimationDrawable = (AnimationDrawable) myimageView.getBackground();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(myimageView,params);
    }

   /* @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = (ImageView) findViewById(R.id.loading_footer_layout_imageview);
        imageDrawable = (AnimationDrawable) imageView.getBackground();
    }*/

    @Override
    public void onLoadMore() {
        /*if (!imageDrawable.isRunning()) {
            imageDrawable.start();
        }*/

        if (!myanimationDrawable.isRunning()) {
            myanimationDrawable.start();
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
        /*if (!imageDrawable.isRunning()) {
            imageDrawable.start();
        }*/
        if (!myanimationDrawable.isRunning()) {
            myanimationDrawable.start();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {
        /*if (imageDrawable != null)
            imageDrawable.stop();*/

        if (myanimationDrawable != null)
            myanimationDrawable.stop();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

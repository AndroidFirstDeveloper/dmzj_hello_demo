package com.example.myapplication.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.myapplication.R;

public class LoadMoreView extends RelativeLayout implements SwipeLoadMoreTrigger, SwipeTrigger {

    private TextView textView;
    private boolean animation_is_end = true;
    private LoadMoreView.LayoutParams shadeViewParams;
    private View view;
    private int loading_content_width;

    public LoadMoreView(Context context) {
        super(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.loadmore_view_layout_textview);
        view = findViewById(R.id.loadmore_view_layout_shade);
//        LoadMoreView.LayoutParams params = (LoadMoreView.LayoutParams) textView.getLayoutParams();
//        loading_content_width = params.width;
        loading_content_width = view.getMeasuredWidth();
        System.out.println("------XX-----------" + loading_content_width);
        shadeViewParams = (LoadMoreView.LayoutParams) view.getLayoutParams();
//        shadeViewParams.width = loading_content_width;
//        view.setLayoutParams(shadeViewParams);
    }

    @Override
    public void onLoadMore() {
        startAnimation();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {
    }

    private void startAnimation() {
        if (animation_is_end) {
            animation_is_end = false;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(420, 0);
            valueAnimator.setDuration(2000);
            valueAnimator.setRepeatCount(0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    shadeViewParams.width = value;
                    view.setLayoutParams(shadeViewParams);
                    /*switch (value % 5) {
                        case 0:
                            textView.setText(".");
                            break;
                        case 1:
                            textView.setText("..");
                            break;
                        case 2:
                            textView.setText("...");
                            break;
                        case 3:
                            textView.setText("....");
                            break;
                        case 4:
                            textView.setText(".....");
                            break;
                    }*/
                }
            });

            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animation_is_end = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    animation_is_end = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            valueAnimator.start();
        }
    }
}

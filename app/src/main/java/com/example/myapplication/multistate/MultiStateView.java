package com.example.myapplication.multistate;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class MultiStateView extends LinearLayout {

    private View emptyView;
    private View loadingNormalView;
    private View loadingErrorView;
    private View netErrorView;

    private int emptyViewResourceId;
    private int loadingNormalViewResourceId;
    private int loadingErrorViewResourceId;
    private int netErrorViewResourceId;

    private int mcurrentState;
    private final int EMPTY_STATE = 0;
    private final int LOADING_NORMAL_STATE = 1;
    private final int LOADING_ERROR_STATE = 2;
    private final int NET_ERROR_STATE = 3;
    private final int SUCCESS_STATE = 4;

    private LayoutInflater mInflate;
    private LayoutParams layoutParams;


    public MultiStateView(Context context) {
//        super(context);
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        emptyViewResourceId = typedArray.getResourceId(R.styleable.MultiStateView_empty_layout, 0);
        loadingNormalViewResourceId = typedArray.getResourceId(R.styleable.MultiStateView_loading_normal_layout, 0);
        loadingErrorViewResourceId = typedArray.getResourceId(R.styleable.MultiStateView_loading_error_layout, 0);
        netErrorViewResourceId = typedArray.getResourceId(R.styleable.MultiStateView_net_error_layout, 0);
        typedArray.recycle();

        mInflate = LayoutInflater.from(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void showSuccess() {
        mcurrentState = SUCCESS_STATE;
        showViewByState(mcurrentState);
    }

    public void showNetError() {
        mcurrentState = NET_ERROR_STATE;
        if (null == netErrorView) {
            netErrorView = mInflate.inflate(netErrorViewResourceId, null);
            addView(netErrorView, layoutParams);
        }
        showViewByState(mcurrentState);
    }

    public void showLoadingError() {
        mcurrentState = LOADING_ERROR_STATE;
        if (null == loadingErrorView) {
            loadingErrorView = mInflate.inflate(loadingErrorViewResourceId, null);
            addView(loadingErrorView, layoutParams);
        }
        showViewByState(mcurrentState);
    }

    public void showLoadingNormal() {
        mcurrentState = LOADING_NORMAL_STATE;
        if (null == loadingNormalView) {
            loadingNormalView = mInflate.inflate(loadingNormalViewResourceId, null);
            addView(loadingNormalView, layoutParams);
        }
        showViewByState(mcurrentState);
    }

    public void showEmpty() {
        mcurrentState = EMPTY_STATE;
        if (null == emptyView) {
            emptyView = mInflate.inflate(emptyViewResourceId, null);
            addView(emptyView, layoutParams);
        }
        showViewByState(mcurrentState);
    }

    private void showViewByState(int state) {
        this.setVisibility(state == SUCCESS_STATE ? GONE : VISIBLE);

        if (emptyView != null) {
            emptyView.setVisibility(state == EMPTY_STATE ? VISIBLE : GONE);
        }
        if (loadingNormalView != null) {
            loadingNormalView.setVisibility(state == LOADING_NORMAL_STATE ? VISIBLE : GONE);
        }
        if (loadingErrorView != null) {
            loadingErrorView.setVisibility(state == LOADING_ERROR_STATE ? VISIBLE : GONE);
        }
        if (netErrorView != null) {
            netErrorView.setVisibility(state == NET_ERROR_STATE ? VISIBLE : GONE);
        }
    }


}

package com.example.myapplication.view;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MyImageView extends AppCompatImageView {

	public static final String TAG = "MyImageView" ;

	protected MotionEvent downEvent ;

	protected MotionEvent upEvent ;

	public MyImageView(Context context) {
		super(context);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}

	private boolean lock_color_filter = false ;

	public void lockColorFilter(){
		lock_color_filter = true ;
	}

	//已经放弃,即:用户已经把手指滑出控件区域
	private boolean unlock = false ;

	public OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
		// 变暗(三个-50，值越大则效果越深)
		public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50,
				0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				downEvent = event ;
				ImageView iv = (ImageView) v;
				if (!lock_color_filter) {
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));
				}
				unlock = false ;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				upEvent = event ;
				//合理区域内
				if (event.getX() < v.getWidth() && event.getX()>0 && event.getY() < v.getHeight() && event.getY() > 0  && !unlock) {
					ImageView iv = (ImageView) v;
					iv.clearColorFilter();
					mPerformClick();
				}

			}else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				//不在合理区域内
				if (!(event.getX() < v.getWidth() && event.getX()>0 && event.getY() < v.getHeight() && event.getY() > 0 )) {
					ImageView iv = (ImageView) v;
					iv.clearColorFilter();
					unlock = true ;
				}

			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				ImageView iv = (ImageView) v;
				iv.clearColorFilter();
			}

			return true; // 如为false，执行ACTION_DOWN后不再往下执行
		}
	};

	protected void mPerformClick() {
		MyImageView.this.performClick();
	}



}

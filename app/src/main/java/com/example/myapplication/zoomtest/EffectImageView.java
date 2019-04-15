package com.example.myapplication.zoomtest;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;


public class EffectImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private boolean enterFirst = true;
    private float baseScale = 1;
    private float midScale = 1;
    private float maxScale = 1;
    private float curPosX;
    private float curPosY;
    private float lastPosX;
    private float lastPosY;

    private int scaledTouchSlop;
    private int lastPointerCount = 0;


    private Matrix matrix;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;


    public EffectImageView(Context context) {
//        super(context);
        this(context, null);
    }

    public EffectImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public EffectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        matrix = new Matrix();
        setImageMatrix(matrix);
        setOnTouchListener(this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                final float currentScale = getScale();
                if (currentScale < midScale) {
                    float scale = midScale / currentScale;
                    matrix.postScale(scale, scale, e.getX(), e.getY());
                }

                if (midScale <= currentScale && currentScale <= maxScale) {
                    float scale = baseScale / currentScale;
                    matrix.postScale(scale, scale, e.getX(), e.getY());
                }

                checkBorderWhenScale();
                setImageMatrix(matrix);
                return true;
            }
        });
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private float getScale() {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    private RectF getRectF() {
        if (getDrawable() != null) {
            RectF rectF = new RectF(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            matrix.mapRect(rectF);
            return rectF;
        }
        return new RectF();
    }

    @Override
    public void onGlobalLayout() {
        if (enterFirst) {
            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable != null) {
                int dw = drawable.getIntrinsicWidth();
                int dh = drawable.getIntrinsicHeight();

                if (width < dw && dh < height) {
                    baseScale = width * 1.0f / dw;
                }

                if (width > dw && dh > height) {
                    baseScale = height * 1.0f / dh;
                }

                if ((width < dw && dh > height) || (width > dw && dh > height)) {
                    baseScale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
                }

                midScale = baseScale * 2;
                maxScale = baseScale * 4;

                matrix.postScale(baseScale, baseScale);
                RectF rectF = getRectF();
                matrix.postTranslate(width / 2 - (rectF.left + rectF.right) / 2, height / 2 - (rectF.top + rectF.bottom) / 2);
                setImageMatrix(matrix);
                enterFirst = false;
            }
        }
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        final float scaleFactor = detector.getScaleFactor();
        final float currentScale = getScale();
        final float focusX = detector.getFocusX();
        final float focusY = detector.getFocusY();

        if (scaleFactor > 1.0f && (currentScale < maxScale)) {
            float scale = scaleFactor * currentScale > maxScale ? maxScale / currentScale : scaleFactor;
            matrix.postScale(scale, scale, focusX, focusY);
        }

        if (scaleFactor < 1.0f && (currentScale > baseScale)) {
            float scale = scaleFactor * currentScale > baseScale ? scaleFactor : baseScale / currentScale;
            matrix.postScale(scale, scale, focusX, focusY);
        }
        checkBorderWhenScale();
        setImageMatrix(matrix);

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.print("onTouch\t\t\t\t\t");
        if (gestureDetector.onTouchEvent(event)) {
//            System.out.println("gestureDetector.onTouchEvent(event)");
            return true;
        }
        if (scaleGestureDetector.onTouchEvent(event)) {
//            System.out.println("scaleGestureDetector.onTouchEvent(event)");
        }

        final int pointerCount = event.getPointerCount();
        switch (event.getAction()) {
//event.getActionIndex();
//event.get
            case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_POINTER_UP:

                    break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("ACTION_DOWN");
                for (int i = 0; i < pointerCount; i++) {
                    lastPosX += event.getX(i);
                    lastPosY += event.getY(i);
                }
                lastPosX = lastPosX / pointerCount;
                lastPosY = lastPosY / pointerCount;
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("ACTION_MOVE");
                curPosX = 0;
                curPosY = 0;
                for (int i = 0; i < pointerCount; i++) {
                    curPosX += event.getX(i);
                    curPosY += event.getY(i);
                }
                curPosX = curPosX / pointerCount;
                curPosY = curPosY / pointerCount;
                RectF rectF = getRectF();
                float offsetX = (rectF.width() > getWidth()) ? (curPosX - lastPosX) : 0;
                float offsetY = (rectF.height() > getHeight()) ? (curPosY - lastPosY) : 0;
                matrix.postTranslate(offsetX, offsetY);
                checkBorderWhenTranslate();
                setImageMatrix(matrix);
                lastPosX = curPosX;
                lastPosY = curPosY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                System.out.println("ACTION_UP");
                lastPosX = 0;
                lastPosY = 0;
                lastPointerCount = 0;
                break;
        }
        return true;
    }

    private void checkBorderWhenTranslate() {
        RectF rectF = getRectF();
        float offsetX = 0;
        float offsetY = 0;
        if (rectF.width() > getWidth()) {
            if (rectF.left > 0) {
                offsetX = -rectF.left;
            }

            if (rectF.right < getWidth()) {
                offsetX = getWidth() - rectF.right;
            }
        }

        if (rectF.height() > getHeight()) {
            if (rectF.top > 0) {
                offsetY = -rectF.top;
            }

            if (rectF.bottom < getHeight()) {
                offsetY = getHeight() - rectF.bottom;
            }
        }
        matrix.postTranslate(offsetX, offsetY);
    }

    private void checkBorderWhenScale() {
        RectF rectF = getRectF();
        float offsetX = 0;
        float offsetY = 0;

        if (rectF.width() >= getWidth()) {
            if (rectF.left > 0) {
                offsetX = -rectF.left;
            }

            if (rectF.right < getWidth()) {
                offsetX = getWidth() - rectF.right;
            }
        }

        if (rectF.height() >= getHeight()) {
            if (rectF.top > 0) {
                offsetY = -rectF.top;
            }

            if (rectF.bottom < getHeight()) {
                offsetY = getHeight() - rectF.bottom;
            }
        }

        if (rectF.width() < getWidth()) {
            offsetX = getWidth() / 2 - (rectF.left + rectF.right) / 2;
        }
        if (rectF.height() < getHeight()) {
            offsetY = getHeight() / 2 - (rectF.top + rectF.bottom) / 2;
        }
        matrix.postTranslate(offsetX, offsetY);
    }


    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > scaledTouchSlop;
    }
}

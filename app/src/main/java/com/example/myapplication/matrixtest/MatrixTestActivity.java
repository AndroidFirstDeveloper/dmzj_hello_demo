package com.example.myapplication.matrixtest;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;

public class MatrixTestActivity extends AppCompatActivity {

    private ImageView imageView;
    private Matrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_test);
        findViews();
    }

    private void findViews() {
        matrix = new Matrix();
        imageView = (ImageView) findViewById(R.id.activity_matrix_test_imageview);

    }

    public void translate(View view) {
        matrix.reset();
        RectF rectF = getMatrixRectF();
//        matrix.postScale(1.1f,1.1f,(rectF.left+rectF.right)/2,(rectF.top+rectF.bottom)/2);
//        matrix.postTranslate(100, 100);
//        imageView.setImageMatrix(matrix);
//        matrix.postScale(0.5f,0.5f);
//        matrix.preScale(0.5f,0.5f);
//        matrix.postTranslate(imageView.getWidth()/2,rectF.height()/2);
//        matrix.preTranslate(-imageView.getWidth()/2,-rectF.height()/2);

        //M*T*S
//        matrix.preTranslate(100, 100);
//        matrix.preScale(0.5f, 0.5f);

        //S*(T*M)
//        matrix.postTranslate(100, 100);
//        matrix.postScale(0.5f, 0.5f);

        //M*T*R
//        matrix.preTranslate(100,100);
//        matrix.preRotate(30,100,100);

        float dx = imageView.getWidth() / 2 - (rectF.left + rectF.right) / 2;
        float dy = imageView.getHeight() / 2 - (rectF.top + rectF.bottom) / 2;
//        matrix.preTranslate(dx,dy);
//        matrix.preRotate(90,rectF.width()/2,rectF.height()/2);
        matrix.postTranslate(dx,dy);
//        matrix.postScale(0.5f,0.5f,imageView.getWidth()/2,imageView.getHeight()/2);
        matrix.postRotate(90,imageView.getWidth()/2,imageView.getHeight()/2);

        imageView.setImageMatrix(matrix);
    }

    private RectF getMatrixRectF() {
        RectF rectF = new RectF();
        if (imageView.getDrawable() != null) {
            int dh = imageView.getDrawable().getIntrinsicHeight();
            int dw = imageView.getDrawable().getIntrinsicWidth();
            rectF.set(0, 0, dw, dh);
        }
        matrix.mapRect(rectF);
        return rectF;
    }
}

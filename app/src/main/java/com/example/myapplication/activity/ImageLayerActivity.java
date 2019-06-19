package com.example.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ImageLayerActivity extends AppCompatActivity {

    private ImageView activity_image_layer_iv;
    private ImageView activity_image_compress_iv;
    private final String TAG = "ImageLayerActivity";
    final String title = "《我的英雄学院》将要上映了，敬请关注漫番漫画官方网站。动漫盛宴，暑期high番。活动期间下载漫番app还有机会获得最多88888漫币奖励！";
    private String querycodeContent = "漫番漫画";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_layer);
        findView();
//        testDrawableLayer();
//        testDrawableLayer2();
//        testDrawableLayer3();
//        testDrawableLayer4(title, querycodeContent, R.drawable.share_bg_icon, R.drawable.fg_icon3);
//
//        testGetImageByte();
        try {
            testPirtureCompress();
        } catch (IOException e) {
            Log.e(TAG, "onCreate1: ",e );
        }catch (Exception e){
            Log.e(TAG, "onCreate2: ",e );
        }
    }

    private void findView() {
        View activity_image_layer_toolbar = findViewById(R.id.activity_image_layer_toolbar);
        View title_bar_layout_status_view = activity_image_layer_toolbar.findViewById(R.id.title_bar_layout_status_view);
        ImmersionBar.with(this).statusBarView(title_bar_layout_status_view).init();
        activity_image_layer_iv = findViewById(R.id.activity_image_layer_iv);
        activity_image_compress_iv = findViewById(R.id.activity_image_compress_iv);
    }


    private void testDrawableLayer4(String title, String queryCodeContent, int bgResourceId, int fgResourceId) {
        final int BORDER_MARGIN = 20;//白色边框宽度
        final int TITLE_MARGIN = 33;//标题距离背景图片的高度
        final int QUERY_CODE_MARGIN = 40;//二维码距离背景右边的间距
        final int LOGO_RIGHT_MARGIN = 20;//漫番logo右边距离二维码的间距
        final int BOTTOM_MARGIN = 40;//二维码和logo距离背景底部的间距
        final String TITLE_TEXT_COLOR = "#333333";
        final int TITLE_TEXT_SIZE = 48;
        final int TITLE_MAXLINE = 2;
        final int ANGLE = -15;

        //获取背景图片对象
        Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), bgResourceId);
        final int bgWidth = bgBitmap.getWidth();
        final int bgHeight = bgBitmap.getHeight();

        //获取文字标题内容所占的高度
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(TITLE_TEXT_SIZE);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.parseColor(TITLE_TEXT_COLOR));
        StaticLayout sl = new StaticLayout(title, 0, title.length(), textPaint, bgWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//        StaticLayout sl = generateStaticLayout(title, 0, title.length(), textPaint, bgWidth, Layout.Alignment.ALIGN_NORMAL, TextDirectionHeuristics.FIRSTSTRONG_LTR, 1.0f, 0.0f, false, TextUtils.TruncateAt.END, 0,3);
        StaticLayout staticLayout = null;
        if (sl.getLineCount() > TITLE_MAXLINE) {
            final int endIndex = sl.getLineEnd(1);
            staticLayout = new StaticLayout(title, 0, endIndex, textPaint, bgWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        } else {
            staticLayout = sl;
        }

        final int titleHeight = staticLayout.getHeight();

        //获取二维码
        Bitmap querycodeBmp = getQueryCode(queryCodeContent);
        final int qcWidth = querycodeBmp.getWidth();
        final int qcHeight = querycodeBmp.getHeight();
        //初始化整个背景的宽度、高度
        final int TOTAL_WIDTH = bgWidth + BORDER_MARGIN * 2;
        final int TOTAL_HEIGHT = bgHeight + BORDER_MARGIN * 2 + TITLE_MARGIN + titleHeight + QUERY_CODE_MARGIN + qcHeight + BOTTOM_MARGIN;
        //绘制白色背景
        Bitmap newBitmap = Bitmap.createBitmap(TOTAL_WIDTH, TOTAL_HEIGHT, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);

        //绘制背景图片
        canvas.drawBitmap(bgBitmap, BORDER_MARGIN, BORDER_MARGIN, null);
        //获得前景图片的大小
        Bitmap fgBitmap = BitmapFactory.decodeResource(getResources(), fgResourceId);
//        final int fgWidth = fgBitmap.getWidth();
//        final int fgHeight = fgBitmap.getHeight();
        final int fgWidth = bgWidth - BORDER_MARGIN * 2 - BORDER_MARGIN;
        final int fgHeight = (int) (fgWidth * fgBitmap.getHeight() * 1.0f / fgBitmap.getWidth());
        final float fgScale = fgWidth * 1.0f / fgBitmap.getWidth();


        //绘制前景图片白色背景
        canvas.save();
        final int fgtranslateX = (bgWidth - fgWidth) / 2;
        final int fgtranslateY = (bgHeight - fgHeight) / 2;
        final int rotateX = (fgWidth + BORDER_MARGIN * 2) / 2;
        final int rotateY = (fgHeight + BORDER_MARGIN * 2) / 2;
        final int rotateAngle = ANGLE;
        Bitmap borderBitmap = Bitmap.createBitmap(fgWidth + BORDER_MARGIN * 2, fgHeight + BORDER_MARGIN * 2, Bitmap.Config.RGB_565);
        Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
        canvas.translate(fgtranslateX, fgtranslateY);
        canvas.rotate(rotateAngle, rotateX, rotateY);
        canvas.drawBitmap(borderBitmap, 0, 0, borderPaint);
        canvas.restore();
        //绘制前景图
        final int rotateX2 = (fgWidth) / 2;
        final int rotateY2 = (fgHeight) / 2;
        canvas.save();
        Paint fgPaint = new Paint();
        fgPaint.setAntiAlias(false);
        canvas.translate(fgtranslateX + BORDER_MARGIN, fgtranslateY + BORDER_MARGIN);
        canvas.rotate(rotateAngle, rotateX2, rotateY2);
        Matrix matrixScale = new Matrix();
        matrixScale.setScale(fgScale, fgScale);
        canvas.drawBitmap(fgBitmap, matrixScale, null);
//        canvas.drawBitmap(fgBitmap, 0, 0, null);
        canvas.restore();

        //绘制文字
        canvas.save();
        final int translateX3 = BORDER_MARGIN;
        final int translateY3 = bgHeight + BORDER_MARGIN * 2 + TITLE_MARGIN;
        canvas.translate(translateX3, translateY3);
        staticLayout.draw(canvas);
        canvas.restore();
        //绘制二维码
        final int translateX4 = bgWidth + BORDER_MARGIN * 2 - qcWidth - QUERY_CODE_MARGIN;
        final int translateY4 = TOTAL_HEIGHT - BOTTOM_MARGIN - qcHeight;
        canvas.save();
        canvas.translate(translateX4, translateY4);
        canvas.drawBitmap(querycodeBmp, 0, 0, null);
        canvas.restore();
        //绘制漫番logo
        Bitmap logoBmp = BitmapFactory.decodeResource(getResources(), R.drawable.mf_logo_icon);
        final int logoWidth = logoBmp.getWidth();
        final int logoHeight = logoBmp.getHeight();
        Log.e(TAG, "logoWidth=" + logoWidth + "\t\tlogoHeight=" + logoHeight);
        final int translateX5 = bgWidth + BORDER_MARGIN * 2 - qcWidth - QUERY_CODE_MARGIN - LOGO_RIGHT_MARGIN - logoWidth;
        final int translateY5 = TOTAL_HEIGHT - BOTTOM_MARGIN - logoHeight;
        canvas.save();
        canvas.translate(translateX5, translateY5);
        canvas.drawBitmap(logoBmp, 0, 0, null);
        canvas.restore();
        activity_image_layer_iv.setImageBitmap(newBitmap);
//        showCompressBitmap(newBitmap);
    }

    private void testDrawableLayer3() {
        final int margin = 20;

        Bitmap bg = Bitmap.createBitmap(600, 900, Bitmap.Config.RGB_565);
        Bitmap fg = Bitmap.createBitmap(600, 500, Bitmap.Config.RGB_565);

        Bitmap newBmp = Bitmap.createBitmap(640, 1380, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBmp);
        canvas.drawColor(Color.WHITE);

        Paint redPaint = new Paint();
        redPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bg, margin, margin, redPaint);

        canvas.save();
        Paint greenPaint = new Paint();
        greenPaint.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
        canvas.translate(margin, 200);
        canvas.rotate(-10, 300, 250);
        canvas.drawBitmap(fg, 0, 0, greenPaint);
        canvas.restore();

        canvas.save();
        final String title = "《我的英雄学院》将要上映了，敬请关注漫番漫画官方网站。暑期high番，动漫盛宴。";
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.parseColor("#333333"));
        StaticLayout sl = new StaticLayout(title, textPaint, 600, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        canvas.translate(margin, 900 + margin * 2);
        sl.draw(canvas);
        canvas.restore();

        activity_image_layer_iv.setImageBitmap(newBmp);
    }

    private void testDrawableLayer2() {

        //未知数据：背景图片的宽度、高度；文字距离背景图片底部的距离；分享图片未旋转时的位置、分享图片旋转角度；
        Bitmap bg = ((BitmapDrawable) getResources().getDrawable(R.drawable.layer_bg2)).getBitmap();
//        Bitmap fg = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();

        // 创建一张大小和背景图一致的位图
        final int bgWidth = bg.getWidth();
        final int bgHeight = bg.getHeight();
        Bitmap fg = Bitmap.createBitmap(bgWidth, bgHeight / 2, Bitmap.Config.RGB_565);

        final int fgWidth = fg.getWidth();
        final int fgHeight = fg.getHeight();
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.RGB_565);
        // 新建画布，并画出背景图和前景图
        Canvas canvas = new Canvas(newbmp);
        canvas.drawColor(Color.WHITE);
        //drawbginto
        //在0，0坐标开始画入bg
        canvas.drawBitmap(bg, 0, 0, null);
        //drawfginto
        //开始画入fg，可以从任意位置画入，具体位置自己计算
//        Matrix matrix = new Matrix();
//        matrix.preTranslate((bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2);
//        matrix.postRotate(-90);
////        canvas.drawBitmap(fg, 0, 0, null);
//        canvas.drawBitmap(fg,matrix,new Paint());
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.translate((bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2);
        canvas.rotate(-30, fgWidth / 2, fgHeight / 2);
        canvas.drawBitmap(fg, 0, 0, paint);
        canvas.restore();


        canvas.save();
        final String content = "《我的英雄学院》人间精品，不可多得啊，大家快来一起看，欢乐过暑期";
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
//        textPaint.setTypeface(Typeface.BOLD);
        textPaint.setTextSize(32);
        StaticLayout sl = new StaticLayout(content, textPaint, bgWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        canvas.translate(20, bgHeight - 220);
        sl.draw(canvas);
        canvas.restore();
//        canvas.drawText("",20,bgHeight-180,paint);
        activity_image_layer_iv.setImageBitmap(newbmp);
    }

    private void testDrawableLayer() {
        Bitmap bitmap1 = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.layer_bg1)).getBitmap();
        final int width = bitmap1.getWidth();
        final int height = bitmap1.getHeight();

        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.ic_launcher)).getBitmap();
//        Bitmap rotateBmp=rotaingImageView(20,bitmap2);
        Drawable[] array = new Drawable[2];
        array[0] = new BitmapDrawable(bitmap1);
        array[1] = new BitmapDrawable(bitmap2);
        LayerDrawable la = new LayerDrawable(array);
        // 其中第一个参数为层的索引号，后面的四个参数分别为left、top、right和bottom
        la.setLayerInset(0, 0, 0, 0, 0);
        la.setLayerInset(1, 20, 20, 20, 20);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) activity_image_layer_iv.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        activity_image_layer_iv.setLayoutParams(layoutParams);
        activity_image_layer_iv.setImageDrawable(la);
    }

    private void testGetImageByte() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap originBmp = BitmapFactory.decodeResource(getResources(), R.drawable.fg_icon3, options);
        Log.e(TAG, "图片宽=" + originBmp.getWidth() + "\t\t图片高=" + originBmp.getHeight() + "\t\tBitmap占用内存大小=" + (originBmp.getByteCount()) / 1024 + "KB");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        Log.e(TAG, "图片的大小=" + bytes.length / 1024 + "KB");
    }

    private void testPirtureCompress() throws IOException {
        final String path = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20190418-113516.jpg";
        File file = new File(path);
        FileInputStream inputStream = null;
        byte[] bytes = new byte[1024];
        inputStream = new FileInputStream(file);
        int read = inputStream.read(bytes);
        while (read > 0) {
            read = inputStream.read(bytes);
        }
        inputStream.close();

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.e(TAG, "testPirtureCompress:图片宽度= " + bitmap.getWidth() + "\t\t图片高度=" + bitmap.getHeight());
        Log.e(TAG, "testPirtureCompress: 图片大小=" + bitmap.getByteCount() / 1024 + "KB");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        bitmap.recycle();


        Bitmap compressBmp = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
        Log.e(TAG, "testPirtureCompress: 压缩后图片大小=" + bos.toByteArray().length / 1024 + "KB");
        Log.e(TAG, "testPirtureCompress: 压缩后图片宽度=" + compressBmp.getWidth() + "\t\t压缩后图片高度=" + compressBmp.getHeight());
    }


    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 要处理的Bitmap
     * @return 处理后的Bitmap
     */
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }

    private final int QUERY_CODE_WIDTH = 80;
    private final int QUERY_CODE_HEIGHT = 80;

    //生成二维码
    private Bitmap getQueryCode(String content) {
        if (TextUtils.isEmpty(content)) {
            throw new NullPointerException("二维码内容为空");
        }
        return CodeUtils.createImage(content, QUERY_CODE_WIDTH, QUERY_CODE_HEIGHT, null);
    }

    //生成StaticLayout
    private StaticLayout generateStaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerwidth, Layout.Alignment align, TextDirectionHeuristic textDir,
                                              float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth, int maxLines) {
        StaticLayout sl = null;
        Class clazz = null;
        try {
            clazz = Class.forName("android.text.StaticLayout");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        Constructor<?> cons[] = null;
        cons = clazz.getConstructors(); //第一步，取得全部构造方法并赋给数组
        Constructor con = null;
        StaticLayout tmp = null;
        try {
            //**13个参数 ，注意int.class 不是interge.class**
            con = clazz.getConstructor(CharSequence.class, int.class, int.class, TextPaint.class, int.class, Layout.Alignment.class, TextDirectionHeuristic.class, float.class, float.class, boolean.class,
                    TextUtils.TruncateAt.class, int.class, int.class);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        try {
//            tmp = (StaticLayout) con.newInstance("" + e.label, 0, end, mTextPain, (int) sdif - 1, Layout.Alignment.ALIGN_NORMAL, TextDirectionHeuristics.FIRSTSTRONG_LTR, 1.0f, 0.0f, true, TextUtils.TruncateAt.MIDDLE, (int) (sdif - 3 * sradius), 2);
            tmp = (StaticLayout) con.newInstance(source, bufstart, bufend, paint, outerwidth, align, textDir, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth, maxLines);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        sl = tmp;
        return sl;
    }


    private void showCompressBitmap(Bitmap newBitmap) {
        long imageWidth = newBitmap.getWidth();
        long imageHeight = newBitmap.getHeight();
        float size = imageWidth * imageHeight * (5 + 6 + 5) / 8.0f / 1024.0f;
        Log.e(TAG, "showCompressBitmap: 压缩前图片大小" + size + "K");
        Bitmap compressBmp = null;
        float ratio = 1.0f;
        while (size > 32 && ratio > 0.2f) {
            ratio = ratio - 0.2f;
            Matrix matrix = new Matrix();
            matrix.setScale(0.25f, 0.25f);
            compressBmp = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
            imageWidth = compressBmp.getWidth();
            imageHeight = compressBmp.getHeight();
            size = imageWidth * imageHeight * (5 + 6 + 5) / 8.0f / 1024.0f;
        }
        Log.e(TAG, "showCompressBitmap: 压缩后图片大小" + size + "K");
        activity_image_compress_iv.setImageBitmap(compressBmp);
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}

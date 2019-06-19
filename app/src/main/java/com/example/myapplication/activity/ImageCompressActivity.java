package com.example.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ImageCompressActivity extends AppCompatActivity {

    //    private final String path = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20190418-113516.jpg";
//    private final String path = "/storage/emulated/0/Tencent/QQ_Images/-2ed5e6ee5a1b3815.jpg";
    private final String path = "/storage/emulated/0/DCIM/Camera/IMG_20190603_203119.jpg";
    private final String TAG = "ImageCompressActivity";
    private ImageView activity_image_compress_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compress);
        findView();

//        byte[] bytes = getFileBytes();
//        compressPic1(bytes);
//        compressPic2(bytes);
//        compressPic3(bytes);
//        asyncCompressPic4();

        lubanCompressPic5();
    }

    private void findView() {
        View activity_image_compress_toolbar = findViewById(R.id.activity_image_compress_toolbar);
        View title_bar_layout_status_view = activity_image_compress_toolbar.findViewById(R.id.title_bar_layout_status_view);
        ImmersionBar.with(this).statusBarView(title_bar_layout_status_view).init();
        activity_image_compress_iv = findViewById(R.id.activity_image_compress_iv);
    }


    /**
     * 采样率压缩图片
     */
    private void compressPic1(byte[] bytes) {
        if (bytes == null) {
            Log.e(TAG, "compressPic: 压缩数据为空1");
            return;
        }
        int fileSize = bytes.length / 1024;
        Log.e(TAG, "compressPic: 图片压缩前\t\t1文件大小=" + fileSize + " KB");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        float compressFileSize = bitmap.getWidth() * bitmap.getHeight() * 2 / 1024 * 1.0f;
        Log.e(TAG, "compressPic: 图片压缩后\t\t占用内存大小=" + bitmap.getByteCount() / 1024 + " KB");
        activity_image_compress_iv.setImageBitmap(bitmap);
    }

    /**
     * 质量压缩图片
     */
    private void compressPic2(byte[] bytes) {
        if (bytes == null) {
            Log.e(TAG, "compressPic: 压缩数据为空2");
            return;
        }
        int fileSize = bytes.length / 1024;
        Log.e(TAG, "compressPic: 图片文件大小=" + fileSize + " KB");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        final int fileSize4 = bitmap.getWidth() * bitmap.getHeight() * 4 / 1024;
        Log.e(TAG, "compressPic2: 图片未压缩前占用内存大小=" + fileSize4);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
        byte[] bytes1 = bos.toByteArray();
        final int fileSize2 = bytes1.length / 1024;
        Log.e(TAG, "compressPic: 图片压缩后字节文件大小=" + fileSize2 + " KB");
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
        final int fileSize3 = bitmap1.getWidth() * bitmap1.getHeight() * 4 / 1024;
        Log.e(TAG, "compressPic2: 图片压缩后占用内存大小=" + fileSize3 + " KB");
        activity_image_compress_iv.setImageBitmap(bitmap1);
    }


    /**
     * 编码格式压缩图片
     */
    private void compressPic3(byte[] bytes) {
        if (bytes == null) {
            Log.e(TAG, "compressPic: 压缩数据为空3");
            return;
        }
        int fileSize = bytes.length / 1024;
        Log.e(TAG, "compressPic: 图片文件大小=" + fileSize + " KB");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        final int fileSize1 = bitmap.getWidth() * bitmap.getHeight() * 2 / 1024;
        Log.e(TAG, "compressPic3: 图片压缩后占用内存大小=" + fileSize1 + " KB");
        activity_image_compress_iv.setImageBitmap(bitmap);
    }

    /**
     * 异步压缩图片
     */

    private void asyncCompressPic4() {
        BitmapAsyncTask asyncTask = new BitmapAsyncTask(activity_image_compress_iv, 2100, 3500);
        asyncTask.execute(path);
    }

    /**
     * 使用第三方库LUban压缩图片
     */
    private void lubanCompressPic5() {
        String savePath = Environment.getExternalStorageDirectory().getPath() + "/compress/pic/";
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file1 = new File(savePath + fileName);
//        if (!file1.exists()) {
//            try {
//                file1.createNewFile();
//            } catch (IOException e) {
//                Log.e(TAG, "lubanCompressPic5: ", e);
//            } finally {
//                Log.e(TAG, "lubanCompressPic5: 压缩后图片路径=" + file1.getPath());
//            }
//        }
        Luban.with(this)
                .load(path)
                .ignoreBy(32)
                .setTargetDir(savePath)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        final String path = file.getPath();
                        Log.e(TAG, "onSuccess: 压缩成功:" + path);
                        byte[] bytes = getFileBytes(file.getPath());
                        Log.e(TAG, "onSuccess: 压缩后图片文件大小=" + bytes.length / 1024 + " KB");
//                        Bitmap bitmap = BitmapFactory.decodeFile(path);
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                        Log.e(TAG, "onSuccess: 压缩后图片文件大小=" + bos.toByteArray().length / 1024 + " KB");
//                        activity_image_compress_iv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: 压缩出错", e);
                    }
                }).launch();
    }


    /**
     * 读取文件中的数据到字节数组
     */
    private byte[] getFileBytes(String path) {
        final String MY_TAG = "Bytes";
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            final long inSize = fis.getChannel().size();//判断FileInputStream中是否有内容
            if (inSize == 0) {
                Log.e(MY_TAG, "文件里面没有内容");
                return null;
            }
            byte[] bytes = new byte[fis.available()];//表示要读取的文件中的数据长度
            fis.read(bytes);//将文件中的数据读到buffer中
            return bytes;
        } catch (FileNotFoundException e) {
            Log.e(MY_TAG, "文件不存在");
            return null;
        } catch (IOException e) {
            Log.e(MY_TAG, "2:" + e.getMessage());
            return null;
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                Log.e(MY_TAG, "1:" + e.getMessage());
            }
        }
    }


    /**
     * 异步加载图片的内部类
     */

    private static class BitmapAsyncTask extends AsyncTask<String, Object, Bitmap> {
        private WeakReference<ImageView> weakReference;
        private int requestWidth;
        private int requestHeight;

        public BitmapAsyncTask(ImageView imageView, int requestWidth, int requestHeight) {
            super();
            weakReference = new WeakReference<>(imageView);
            this.requestHeight = requestHeight;
            this.requestWidth = requestWidth;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (weakReference.get() != null) {
                weakReference.get().setImageBitmap(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return decodeBitmapInSample(strings[0], requestWidth, requestHeight);
        }


        /**
         * 解码图片，并压缩
         */
        private Bitmap decodeBitmapInSample(String path, int requestWidth, int requestHeight) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            final int sampleSize = caculateBitmapInSample(options, requestWidth, requestHeight);
            options.inJustDecodeBounds = false;
            options.inSampleSize = sampleSize;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeFile(path, options);
        }

        /**
         * 根据需求值计算缩放倍数
         */
        private int caculateBitmapInSample(BitmapFactory.Options options, int requestWidth, int requestHeigh) {
            int insampleSize = 1;
            while (options.outWidth / requestWidth > insampleSize && options.outHeight / requestHeigh > insampleSize) {
                insampleSize *= 2;
            }
            return insampleSize;
        }
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

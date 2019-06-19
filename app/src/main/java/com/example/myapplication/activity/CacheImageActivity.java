package com.example.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebStorage;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.BaseModel;
import com.example.myapplication.CacheSingleInstance;
import com.example.myapplication.ClearCacheSingleInstance;
import com.example.myapplication.FileCallback;
import com.example.myapplication.R;
import com.example.myapplication.model.ChapterImageList;
import com.example.myapplication.model.ImageBean;
import com.example.myapplication.okhttp.OkHttpSingletonHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.longtuchecksdk.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;

public class CacheImageActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener, FileCallback {

    private final String URL = "https://codetest.myfcomic.com/api/getchapterinfo";
    private final String TAG = "CacheImageActivity";
    private ViewPager activity_cache_image_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_image);
        findView();
        requestChapter();
    }

    private TextView title_bar_layout_operation_tv;

    private void findView() {
        View activity_cache_image_toolbar = findViewById(R.id.activity_cache_image_toolbar);
        View status_view = activity_cache_image_toolbar.findViewById(R.id.title_bar_layout_status_view);
        title_bar_layout_operation_tv = activity_cache_image_toolbar.findViewById(R.id.title_bar_layout_operation_tv);
        ImmersionBar.with(this).statusBarView(status_view).navigationBarColor(android.R.color.white).init();
        activity_cache_image_vp = findViewById(R.id.activity_cache_image_vp);
        title_bar_layout_operation_tv.setText("缓存");
        title_bar_layout_operation_tv.setOnClickListener(this);
    }

    private MyThread myThread;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_layout_operation_tv:
//                String result = ClearCacheSingleInstance.getInstance().getTotalCacheSize(this);
//                showClearCacheDialog(result);
                myThread = new MyThread(this);
                myThread.start();
                break;
            case R.id.clear_cache_dialog_layout_confirm_tv:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.clear_cache_dialog_layout_cancel_tv:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void call(boolean success, final String result) {
        if (!TextUtils.isEmpty(result)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showClearCacheDialog(result);
                }
            });
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (dialog instanceof Dialog)
            ImmersionBar.with(this, (Dialog) dialog, CACHE_DIALOG).destroy();
    }

    private final String CACHE_DIALOG = "CacheDialog";
    private Dialog dialog;

    private void showClearCacheDialog(String size) {
        dialog = new AlertDialog.Builder(this, R.style.dialog_bottom_full).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams layoutParams=window.getAttributes();
//        layoutParams.width=ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(layoutParams);
        dialog.setContentView(R.layout.clear_cache_dialog_layout);
        dialog.setCancelable(true);
        dialog.setOnDismissListener(this);
        TextView clear_cache_dialog_layout_title_tv = window.findViewById(R.id.clear_cache_dialog_layout_title_tv);
        TextView clear_cache_dialog_layout_confirm_tv = window.findViewById(R.id.clear_cache_dialog_layout_confirm_tv);
        TextView clear_cache_dialog_layout_cancel_tv = window.findViewById(R.id.clear_cache_dialog_layout_cancel_tv);
        clear_cache_dialog_layout_confirm_tv.setOnClickListener(this);
        clear_cache_dialog_layout_cancel_tv.setOnClickListener(this);
        clear_cache_dialog_layout_title_tv.setText("是否删除缓存数据(" + size + ")？");
        ImmersionBar.with(this, dialog, CACHE_DIALOG).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    private void requestChapter() {
        Map<String, String> map = new HashMap<>();
        map.put("netstatus", "1");//1
        map.put("comic_id", "155");
        map.put("uid", "0");
        map.put("chapter_id", "3477");//3475、3477、3479、3481
        map.put("remember_token", "0");
        OkHttpSingletonHelper.getInstance().requestGetBySync(getApplicationContext(), URL, map, new OkHttpSingletonHelper.GetCallback() {
            @Override
            public void success(Call call, final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BaseModel<ChapterImageList> baseModel = new Gson().fromJson(result, new TypeToken<BaseModel<ChapterImageList>>() {
                        }.getType());
                        CacheSingleInstance.getInstance().cacheImage("155", "3477", baseModel.getData().getImages(), CacheImageActivity.this.getApplicationContext());
                        MyAdapter myAdapter = new MyAdapter("155", "3477", baseModel.getData().getImages(), CacheImageActivity.this);
                        activity_cache_image_vp.setAdapter(myAdapter);
                    }
                });
            }

            @Override
            public void failed(Call call, IOException e) {
                //DebugUtil.e(TAG, "failed: ");
            }

            @Override
            public void netError(String note) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CacheImageActivity.this, "net error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private static class MyAdapter extends PagerAdapter {
        private List<ImageBean> list;
        private WeakReference<CacheImageActivity> weakReference;
        private final String TAG = "MyAdapter";

        private String comicId;
        private String chapterId;

        MyAdapter(String comicId, String chapterId, List<ImageBean> list, CacheImageActivity activity) {
            this.comicId = comicId;
            this.chapterId = chapterId;
            this.list = list;
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view == object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View convertView = LayoutInflater.from(weakReference.get()).inflate(R.layout.cache_image_item_layout, container, false);
            final ImageView imageView = convertView.findViewById(R.id.cache_image_item_layout_iv);
            final ProgressBar cache_image_item_layout_pb = convertView.findViewById(R.id.cache_image_item_layout_pb);
            cache_image_item_layout_pb.setTag(position);
            cache_image_item_layout_pb.setVisibility(View.VISIBLE);
            container.addView(convertView);

            Glide.with(weakReference.get())
                    .load(CacheSingleInstance.getInstance().getCachePath(weakReference.get().getApplicationContext(), comicId, chapterId, list.get(position).getName()))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            if ((int) (cache_image_item_layout_pb.getTag()) == position) {
                                cache_image_item_layout_pb.setVisibility(View.GONE);
                            }
                            imageView.setImageDrawable(resource);
                        }
                    });
            return convertView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        ImmersionBar.with(this).destroy();
        if (myThread.isAlive()) {
            Log.e(TAG, "onDestroy: 页面退出，终止线程");
            myThread.finishFlag = true;
            myThread.interrupt();//中止线程的阻塞状态
        }
        super.onDestroy();
    }

    private static class DelThread extends Thread {
        private final String TAG = "DelThread";
        private WeakReference<CacheImageActivity> weakReference;
        public boolean finishFlag = false;

        public DelThread(CacheImageActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            if (weakReference != null&&!finishFlag) {
                CacheSingleInstance.getInstance().clearAllCache(weakReference.get());
            }
        }
    }

    private static class MyThread extends Thread {
        private final String TAG = "MyThread";

        private WeakReference<CacheImageActivity> weakReference;
        public boolean finishFlag = false;

        public MyThread(CacheImageActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            Log.e(TAG, "开始读取文件大小...");
            String result = "";
            float totalProgress = 5;
            try {
                for (int i = 1; i <= totalProgress; i++) {
                    sleep(1000);
                    Log.e(TAG, "run: 读取了 " + (10.0f * i / totalProgress) + "%");
                }
                result = ClearCacheSingleInstance.getInstance().getTotalCacheSize(weakReference.get());
                Log.e(TAG, "读取文件大小正常结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, "run: 读取文件提前终止");
            }
            if (weakReference != null && weakReference.get() != null) {
                if (!finishFlag) {
                    Log.e(TAG, "run: 弹出对话框");
                    weakReference.get().call(true, result);
                }
            }
        }
    }
}

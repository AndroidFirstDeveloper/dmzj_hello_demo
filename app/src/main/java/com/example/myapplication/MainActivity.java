package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.myapplication.activity.BannerActivity;
import com.example.myapplication.activity.BannerActivity2;
import com.example.myapplication.activity.BannerTestActivity;
import com.example.myapplication.activity.CacheImageActivity;
import com.example.myapplication.activity.ConstraintActivity;
import com.example.myapplication.activity.DefineToastActivity;
import com.example.myapplication.activity.DotActivity;
import com.example.myapplication.activity.FragmentLifeCycleActivity;
import com.example.myapplication.activity.GitUsingActivity;
import com.example.myapplication.activity.GroupActivity;
import com.example.myapplication.activity.GuideActivity;
import com.example.myapplication.activity.ImageCompressActivity;
import com.example.myapplication.activity.ImageLayerActivity;
import com.example.myapplication.activity.LaunchModelActivity;
import com.example.myapplication.activity.LifeCycleActivity1;
import com.example.myapplication.activity.ListActivity;
import com.example.myapplication.activity.PictureActivity;
import com.example.myapplication.activity.SaveTestActivity;
import com.example.myapplication.activity.ScrollViewActivity;
import com.example.myapplication.activity.ServiceTestActivity;
import com.example.myapplication.activity.WebActivity;
import com.example.myapplication.browse.BrowseActivity1;
import com.example.myapplication.coordinator.CoordinatorActivity1;
import com.example.myapplication.dispatch.DispatchActivity;
import com.example.myapplication.fragment2.FragmentTestActivity;
import com.example.myapplication.fragment3.ConfigChangeActivity;
import com.example.myapplication.greendaotest.GreenDaoActivity;
import com.example.myapplication.javatest.JavaTestActivity;
import com.example.myapplication.matrixtest.MatrixTestActivity;
import com.example.myapplication.multiclick.MultiClickActivity;
import com.example.myapplication.multistate.FirstActivity;
import com.example.myapplication.multistate.TestActivity;
import com.example.myapplication.okhttp.OkHttpSingletonHelper;
import com.example.myapplication.permission.WelcomeActivity;
import com.example.myapplication.recyclerview.RecyclerViewActivity;
import com.example.myapplication.refresh.RefreshLoadActivity;
import com.example.myapplication.screenchange.ScreenActivity;
import com.example.myapplication.zoomtest.EffectImageViewActivity;
import com.example.myapplication.zoomtest.ZoomImageActivity;
import com.google.gson.Gson;
import com.tencent.tinker.android.dx.util.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    private int number;
    private static final String TAG = "MainActivity";
    private static boolean looperFlag = true;
    private List<ActivityBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
//        testBreakPoint();
//        testPropertyBPoint();
        initData();
        findViews();
//        initPage();
//        new JavaTest();
//        testStringReplace();
//        netRequest();
//        testMap();
//        testThread();
//        testThreadNotifyWait();
//        testThreadLocalUse();
    }


    /**
     * 学习ThreadLocal的使用(ThreadLocal的作用主要是，变量在线程间隔离，在一个线程的多个方法间共享)
     * treadLocal不是为了解决并发同步用的，是为了隔离变量用的。同步是为了让多个线程共同操作一个对象而不乱掉
     */
    static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder();
        }
    };

    private void testThreadLocalUse() {
        new TestLocalThread("0").start();
        new TestLocalThread("1").start();
        new TestLocalThread("2").start();
    }

    private static class TestLocalThread extends Thread {

        public TestLocalThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 3; i++) {
                add(String.valueOf(i));
                print();
            }
            threadLocal.remove();
        }

        private void add(String value) {
            StringBuilder sb = threadLocal.get();
            sb.append(value);
            threadLocal.set(sb);
        }

        private void set(String value) {
            StringBuilder sb = new StringBuilder(value);
            threadLocal.set(sb);
        }

        private void print() {
            Log.e(TAG, "thread " + Thread.currentThread().getName() + "\thashCode1：" + threadLocal.hashCode() + "\thashCode2：" + threadLocal.get().hashCode() + "\tcontent：" + threadLocal.get().toString());
        }
    }

    private static String lock = "zhangsan";
    static boolean downloadFinish = false;

    /**
     * 测试多线程通知和等待
     */
    private void testThreadNotifyWait() {
        StorageThread storageThread = new StorageThread("storage", lock);
        storageThread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        DownloadThread downloadThread = new DownloadThread("download", lock);
        downloadThread.start();
    }

    /**
     * 下载线程，模拟下载
     */
    private static class DownloadThread extends Thread {

        private String lock;

        public DownloadThread(String name, String lock) {
            this.lock = lock;
            setName(name);
        }

        public void run() {
            synchronized (lock) {
                Log.e(TAG, "开始下载文件...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "下载完成");
                downloadFinish = true;
                try {
                    lock.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 存储线程，模拟存储
     */
    private static class StorageThread extends Thread {
        private String lock;

        public StorageThread(String name, String lock) {
            this.lock = lock;
            setName(name);
        }

        public void run() {
            synchronized (lock) {
                if (Thread.holdsLock(lock)) {//检测一个线程是否持有对象监视器
                    Log.e(TAG, Thread.currentThread().getName() + " Thread hold the monitor");
                }
                Log.e(TAG, "准备存储文件");
                while (!downloadFinish) {
                    try {
                        Log.e(TAG, "没有下载完毕，进行等待...");
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "开始存储文件...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "存储完成");
            }
        }
    }


    /**
     * 测试多线程的同步问题
     */
    private void testThread() {

        String lock = new String("thread");
        //启动等待线程
        WaitThread waitThread = new WaitThread(lock, "WaitThread");
        waitThread.start();
        //主线程休眠1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e(TAG, "testThread: ", e);
        }
        //启动通知线程
        NotifyThread notifyThread = new NotifyThread(lock, "NotifyThread");
        notifyThread.start();

    }


    private static class WaitThread extends Thread {

        private String lock;

        public WaitThread(String lock, String name) {
            setName(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();
            synchronized (lock) {
                try {
                    while (looperFlag) {
                        final long startTime = SystemClock.elapsedRealtime();
                        Log.e(TAG, "run: Thread Name is " + Thread.currentThread().getName() + "\t\tCurrentTime=" + startTime);
                        lock.wait();
                        final long endTime = SystemClock.elapsedRealtime();
                        Log.e(TAG, "run: Thread Name is " + Thread.currentThread().getName() + "\t\tCurrentTime=" + endTime + "\t线程执行耗时=" + (endTime - startTime) + "ms");
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }
    }

    private static class NotifyThread extends Thread {

        private String lock;

        public NotifyThread(String lock, String name) {
            setName(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();
            synchronized (lock) {
                try {
                    Log.e(TAG, "run: Thread Name is " + Thread.currentThread().getName() + "\t\tCurrentTime=" + SystemClock.elapsedRealtime());
                    lock.notify();
                    Thread.sleep(5000);
                    looperFlag = false;
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        }
    }


    private void testStringReplace() {
        String url = "https://user.myfcomic.com/api/getuserinfo";
        String url2 = "https://user.myfcomic.com/api/getuserinfo";

        url = url.replace("user", "reuser");
        url2 = url2.replaceFirst("user", "reuser");

        Log.e(TAG, "testStringReplace:url= " + url);
        Log.e(TAG, "testStringReplace:url2= " + url2);
    }

    private void initPage() {
        Intent intent = getIntent();
        if (intent != null) {
            if (TextUtils.equals("1", intent.getStringExtra("page"))) {
                Intent intent2 = new Intent(this, BrowseActivity1.class);
                startActivity(intent2);
            }
        }
    }

    private void initData() {
        ActivityBean bean1 = new ActivityBean();
        bean1.setName("EffectImageActivity");
        bean1.setActivity(EffectImageViewActivity.class);
        list.add(bean1);

        ActivityBean bean2 = new ActivityBean();
        bean2.setName("ZoomImageActivity");
        bean2.setActivity(ZoomImageActivity.class);
        list.add(bean2);

        ActivityBean bean3 = new ActivityBean();
        bean3.setName("MatrixTestActivity");
        bean3.setActivity(MatrixTestActivity.class);
        list.add(bean3);

        ActivityBean bean4 = new ActivityBean();
        bean4.setName("FirstActivity");
        bean4.setActivity(FirstActivity.class);
        list.add(bean4);

        ActivityBean bean5 = new ActivityBean();
        bean5.setName("TestActivity");
        bean5.setActivity(TestActivity.class);
        list.add(bean5);

        ActivityBean bean6 = new ActivityBean();
        bean6.setName("可添加header/footer 的recyclerview");
        bean6.setActivity(RecyclerViewActivity.class);
        list.add(bean6);

        ActivityBean bean10 = new ActivityBean();
        bean10.setName("测试recyclerview的insert、remove、局部刷新");
        bean10.setActivity(com.example.myapplication.recyclerview.RvTestActivity.class);
        list.add(bean10);

        ActivityBean bean7 = new ActivityBean();
        bean7.setName("协调布局CoordinatorLayout");
        bean7.setActivity(CoordinatorActivity1.class);
        list.add(bean7);

        ActivityBean bean8 = new ActivityBean();
        bean8.setName("下拉刷新");
        bean8.setActivity(RefreshLoadActivity.class);
        list.add(bean8);

        ActivityBean bean9 = new ActivityBean();
        bean9.setName("Fragment数据传递");
        bean9.setActivity(com.example.myapplication.fragment.FirstActivity.class);
        list.add(bean9);

        ActivityBean bean11 = new ActivityBean();
        bean11.setName("单例模式展示");
        bean11.setActivity(com.example.myapplication.singleton.SingletonActivity.class);
        list.add(bean11);

        ActivityBean bean12 = new ActivityBean();
        bean12.setName("监听网络状态");
        bean12.setActivity(com.example.myapplication.network.NetworkActivity.class);
        list.add(bean12);

        ActivityBean bean13 = new ActivityBean();
        bean13.setName("Bugly 测试");
        bean13.setActivity(com.example.myapplication.BuglyTestActivity.class);
        list.add(bean13);

        ActivityBean bean14 = new ActivityBean();
        bean14.setName("布局 测试");
        bean14.setActivity(com.example.myapplication.layouts.MergeTestActivity.class);
        list.add(bean14);

        ActivityBean bean15 = new ActivityBean();
        bean15.setName("约束布局(ConstraintLayout)");
        bean15.setActivity(com.example.myapplication.constraints.ConstraintActivity.class);
        list.add(bean15);

        ActivityBean bean16 = new ActivityBean();
        bean16.setName("Java （string、integer、参数传递）");
        bean16.setActivity(JavaTestActivity.class);
        list.add(bean16);

        ActivityBean bean17 = new ActivityBean();
        bean17.setName("事件分发");
        bean17.setActivity(DispatchActivity.class);
        list.add(bean17);

        ActivityBean bean18 = new ActivityBean();
        bean18.setName("activity 基础掌握");
        bean18.setActivity(SaveTestActivity.class);
        list.add(bean18);

        ActivityBean bean19 = new ActivityBean();
        bean19.setName("fragment 生命周期+回退栈");
        bean19.setActivity(FragmentTestActivity.class);
        list.add(bean19);

        ActivityBean bean20 = new ActivityBean();
        bean20.setName("fragment 配置发生变化，防止重新构建");
        bean20.setActivity(ConfigChangeActivity.class);
        list.add(bean20);

        ActivityBean bean21 = new ActivityBean();
        bean21.setName("Banner图展示");
        bean21.setActivity(BannerActivity.class);
        list.add(bean21);

        ActivityBean bean22 = new ActivityBean();
        bean22.setName("Banner图展示2");
        bean22.setActivity(BannerActivity2.class);
        list.add(bean22);

        ActivityBean bean23 = new ActivityBean();
        bean23.setName("打点测试");
        bean23.setActivity(DotActivity.class);
        list.add(bean23);

        ActivityBean bean24 = new ActivityBean();
        bean24.setName("三方唤起程序跳转");
        bean24.setActivity(BrowseActivity1.class);
        list.add(bean24);

        ActivityBean bean25 = new ActivityBean();
        bean25.setName("greenDao 测试");
        bean25.setActivity(GreenDaoActivity.class);
        list.add(bean25);

        ActivityBean bean26 = new ActivityBean();
        bean26.setName("横竖屏切换测试");
        bean26.setActivity(ScreenActivity.class);
        list.add(bean26);

        ActivityBean bean27 = new ActivityBean();
        bean27.setName("防止View重复点击  测试");
        bean27.setActivity(MultiClickActivity.class);
        list.add(bean27);

        ActivityBean bean28 = new ActivityBean();
        bean28.setName("自定义Toast");
        bean28.setActivity(DefineToastActivity.class);
        list.add(bean28);

        ActivityBean bean29 = new ActivityBean();
        bean29.setName("启动模式");
        bean29.setActivity(LaunchModelActivity.class);
        list.add(bean29);

        ActivityBean bean30 = new ActivityBean();
        bean30.setName("约束布局 ratio使用");
        bean30.setActivity(ConstraintActivity.class);
        list.add(bean30);

        ActivityBean bean31 = new ActivityBean();
        bean31.setName("Banner轮播图");
        bean31.setActivity(BannerTestActivity.class);
        list.add(bean31);

        ActivityBean bean32 = new ActivityBean();
        bean32.setName("WebView 测试");
        bean32.setActivity(WebActivity.class);
        list.add(bean32);

        ActivityBean bean33 = new ActivityBean();
        bean33.setName("ScrollView 嵌套测试");
        bean33.setActivity(ScrollViewActivity.class);
        list.add(bean33);
        ActivityBean bean34 = new ActivityBean();
        bean34.setName("git 命令集合");
        bean34.setActivity(GitUsingActivity.class);
        list.add(bean34);
        ActivityBean bean35 = new ActivityBean();
        bean35.setName("集合的相关操作");
        bean35.setActivity(ListActivity.class);
        list.add(bean35);

        ActivityBean bean36 = new ActivityBean();
        bean36.setName("Fragment 生命周期");
        bean36.setActivity(FragmentLifeCycleActivity.class);
        list.add(bean36);

        ActivityBean bean37 = new ActivityBean();
        bean37.setName("事件分发测试");
        bean37.setActivity(com.example.myapplication.activity.DispatchActivity.class);
        list.add(bean37);

        ActivityBean bean38 = new ActivityBean();
        bean38.setName("引导页");
        bean38.setActivity(GuideActivity.class);
        list.add(bean38);

        ActivityBean bean39 = new ActivityBean();
        bean39.setName("选择相片");
        bean39.setActivity(PictureActivity.class);
        list.add(bean39);

        ActivityBean bean40 = new ActivityBean();
        bean40.setName("分组列表");
        bean40.setActivity(GroupActivity.class);
        list.add(bean40);

        ActivityBean bean41 = new ActivityBean();
        bean41.setName("动态权限请求");
        bean41.setActivity(WelcomeActivity.class);
        list.add(bean41);

        ActivityBean bean42 = new ActivityBean();
        bean42.setName("图片叠加");
        bean42.setActivity(ImageLayerActivity.class);
        list.add(bean42);

        ActivityBean bean43 = new ActivityBean();
        bean43.setName("图片压缩");
        bean43.setActivity(ImageCompressActivity.class);
        list.add(bean43);
        ActivityBean bean44 = new ActivityBean();
        bean44.setName("图片缓存");
        bean44.setActivity(CacheImageActivity.class);
        list.add(bean44);

        ActivityBean bean45 = new ActivityBean();
        bean45.setName("Android组件生命周期测试");
        bean45.setActivity(LifeCycleActivity1.class);
        list.add(bean45);

        ActivityBean bean46 = new ActivityBean();
        bean46.setName("Service测试");
        bean46.setActivity(ServiceTestActivity.class);
        list.add(bean46);
    }

    private void findViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ActivityAdapter adapter = new ActivityAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void testBreakPoint() {
        int i = 0;
        i = i + 10;
        int a = -10;
        int b = 0;
        for (int k = 0; k < i; k++) {
            a = k;
            b = Math.abs(a);
//            Log.e("Main", b + "");
        }
    }

    private void testPropertyBPoint() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                number = 10;
            }
        }).start();
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    public void anotherPage(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void zoomImagePage(View view) {
        Intent intent = new Intent(this, ZoomImageActivity.class);
        startActivity(intent);
    }

    public void matrixTestPage(View view) {
        Intent intent = new Intent(this, MatrixTestActivity.class);
        startActivity(intent);
    }

    /**
     * 测试字符串转换为16进制
     */
    private void netRequest() {
        final String url = "https://myfcomicadmin.oss-cn-hangzhou.aliyuncs.com/test/3.txt";
        OkHttpSingletonHelper.getInstance().requestGetBySync(getApplicationContext(), url, null, new OkHttpSingletonHelper.GetCallback() {
            @Override
            public void success(Call call, String result) {
                String hexStr = HexUtil.str2HexStr(result);
                Log.e(TAG, "success: " + hexStr);
                String originStr = HexUtil.hexStr2Str(hexStr);
                Log.e(TAG, "success: " + originStr);
            }

            @Override
            public void failed(Call call, IOException e) {

            }

            @Override
            public void netError(String note) {

            }
        });
    }


    private void testMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(null, "first");

        for (int i = 0; i < 50; i++) {
            map.put(i, "item" + i);
        }
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            Log.e(TAG, "testMap: key=" + entry.getKey() + "\t\tvalue=" + entry.getValue());
        }

        Log.e(TAG, "testMap: map contain key==null " + map.containsKey(null));
//        Log.e(TAG, "testMap: " + new Gson().toJson(map));
    }
}

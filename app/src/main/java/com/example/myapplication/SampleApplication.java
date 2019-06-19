package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;

import com.example.myapplication.gen.DaoMaster;
import com.example.myapplication.gen.DaoSession;
import com.example.myapplication.greendaotest.helper.MyOpenHelper;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


public class SampleApplication extends Application {

    private final String TAG = "SampleApplication";

    public static int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
//        MFDotUtil.setupDataBase(this);
        ZXingLibrary.initDisplayOpinion(this);
        listenLifeRecycle();
        setupDatabase();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.d(TAG, "onTrimMemory: " + level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }

    private void listenLifeRecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityCount++;
                Log.d(TAG, "onActivityCreated: activityCount=" + activityCount);
                if (activityCount == 1) {
                    // 返回系统启动到现在的时间，包含设备深度休眠的时间, 单位是毫秒
                    long localTime = SystemClock.elapsedRealtime();
                    Log.d(TAG, "onActivityCreated: 本地时间=" + localTime);
                    saveTime("localTime", localTime);
                    Log.d(TAG, "onActivityCreated: localTime=" + getTime("localTime"));

                    getNetTime();
                    Intent intent = activity.getIntent();
                    if (intent != null) {
                        String action = intent.getAction();
                        Log.d(TAG, "onActivityCreated: action=" + action);
                    }

//                    Log.d(TAG, "onActivityCreated: deviceId=" + com.example.myapplication.Utils.getDeviceUniqueId());

//                    com.example.myapplication.Utils.getDeviceUniqueId(getApplicationContext());
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed: ");
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityCount--;
                Log.d(TAG, "onActivityDestroyed: activityCount=" + activityCount);
            }
        });
    }


    private void getNetTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long standardTime;
                try {
                    //取得资源对象，网址可替换
                    URL url = new URL("https://www.baidu.com/");
                    //生成连接对象
                    URLConnection uc = url.openConnection();
                    //发出连接
                    uc.connect();
                    //取得网站日期时间
                    standardTime = uc.getDate();
                    Log.d(TAG, "getNetTime: 百度时间=" + standardTime);
                    saveTime("netTime", standardTime);
                    Log.d(TAG, "run: netTime=" + getTime("netTime"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: 未获取到百度时间");
                }
            }
        }).start();
    }

    private void saveTime(String key, long time) {
        SharedPreferences sharedPreferences = getSharedPreferences("time_table", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, time);
        editor.apply();
    }

    private long getTime(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("time_table", MODE_PRIVATE);
        long time = sharedPreferences.getLong(key, 0);
        return time;
    }


    private static DaoSession daoSession;

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
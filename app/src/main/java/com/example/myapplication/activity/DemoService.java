package com.example.myapplication.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.myapplication.R;

import java.lang.ref.WeakReference;

public class DemoService extends Service {

    private static final String TAG = "DemoService";
    public static final int SERVICE_WHAT_SEND = 2;
    public static final int SERVICE_WHAT_LOOP = 3;

    private int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 1;
    private boolean haveRemove = true;

    /**
     * Notification
     */
    public void createNotification() {
        /*Log.e(TAG, "createNotification: 启动前台服务");
        //使用兼容版本
        NotificationCompat.Builder builder = new NotificationCompat.Action(R.drawable.jg, "这是一个前台服务", new PendingIntent()).Builder(this);
        //设置状态栏的通知图标
        //builder.setSmallIcon(R.drawable.jg);
        //设置通知栏横条的图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间显示
        builder.setShowWhen(true);
        //设置通知栏的标题内容
        builder.setContentTitle("I am Foreground Service!!!");
        //创建通知
        Notification notification = builder.build();
        //设置为前台服务
        startForegroundService(NOTIFICATION_DOWNLOAD_PROGRESS_ID, notification);*/

        final String CHANNEL_ID = "TEST_SERVICE_ID";
        final String CHANNEL_NAME = "渠道一";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            chan.enableLights(true);
            chan.setLightColor(Color.RED);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
            Notification notification = builder.setContentText("内容")
                    .setContentTitle("标题")
                    .setSmallIcon(R.drawable.jg)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                    .build();
            startForeground(1, notification);
        } else {
            Intent i = new Intent();
            PendingIntent p = PendingIntent.getActivity(this, 1, i, 0);
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("标题")
                    .setContentText("内容")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.jg)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                    .setContentIntent(p)
                    .build();
            startForeground(1, notification);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        if (haveRemove) {
            createNotification();
            haveRemove = false;
        } else {
            stopForeground(true);
            haveRemove = true;
            Log.e(TAG, "createNotification: 停止前台服务");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (myThread != null && myThread.isAlive()) {
            myThread.interrupt();
            myThread = null;
            Log.e(TAG, "结束工作线程: ");
        }

        if (!haveRemove) {
            Log.e(TAG, "createNotification: 停止前台服务");
            stopForeground(true);
        }
        haveRemove = false;
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
//        loopSendMsg();
        return messenger.getBinder();
    }


    private Messenger messenger = new Messenger(new MyHandler());

    private static class MyHandler extends Handler {
        private int counter = 1;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ServiceTestActivity.CLIENT_WHAT_SEND: {
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String content = bundle.getString("msg");
                        Log.e(TAG, "accept message：" + content);
                    }
                    Messenger messenger = msg.replyTo;
                    if (messenger != null) {
                        Message message = Message.obtain();
                        message.what = SERVICE_WHAT_SEND;
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("msg", "reply a message from service " + counter);
                        message.setData(bundle1);
                        try {
                            messenger.send(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        counter++;
                    }
                }
                break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }


    private static class MyRunnable implements Runnable {

        private WeakReference<DemoService> weakReference;

        public MyRunnable(DemoService service) {
            weakReference = new WeakReference<>(service);
        }

        @Override
        public void run() {
            int counter = 1;
            while (counter <= 100) {
                try {
                    Thread.sleep(1000);
                    Message message = Message.obtain();
                    message.what = SERVICE_WHAT_LOOP;
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("msg", "come from service an loop message..." + counter);
                    message.setData(bundle1);
                    try {
                        if (weakReference != null && weakReference.get() != null)
                            weakReference.get().messenger.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    counter++;
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: " + e.getMessage());
                }
            }
        }
    }

    private Thread myThread;

    private void loopSendMsg() {
        Log.e(TAG, "loopSendMsg: 启动工作线程");
        myThread = new Thread(new MyRunnable(this));
        myThread.start();
    }
}

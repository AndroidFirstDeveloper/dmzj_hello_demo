package com.example.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.server.Book;
import com.example.server.BookController;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.lang.ref.WeakReference;
import java.util.List;

public class ServiceTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ServiceActiivty";
    private ServiceConnection serviceConnection;
    public static final int CLIENT_WHAT_SEND = 1;


    private int startModel = 0;
    private int bindModel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        initView();
        initData();
    }


    private void initView() {
        View activity_service_test_toolbar = findViewById(R.id.activity_service_test_toolbar);
        View status_view = activity_service_test_toolbar.findViewById(R.id.title_bar_layout_status_view);
        ImmersionBar.with(this).statusBarView(status_view).navigationBarColor(android.R.color.white).init();

        Button activity_service_test_btn1 = findViewById(R.id.activity_service_test_btn1);
        Button activity_service_test_btn2 = findViewById(R.id.activity_service_test_btn2);
        Button activity_service_test_btn3 = findViewById(R.id.activity_service_test_btn3);
        Button activity_service_test_btn4 = findViewById(R.id.activity_service_test_btn4);
        Button activity_service_test_btn5 = findViewById(R.id.activity_service_test_btn5);
        Button activity_service_test_btn6 = findViewById(R.id.activity_service_test_btn6);
        Button activity_service_test_btn7 = findViewById(R.id.activity_service_test_btn7);
        Button activity_service_test_btn8 = findViewById(R.id.activity_service_test_btn8);

        activity_service_test_btn2.setOnClickListener(this);
        activity_service_test_btn1.setOnClickListener(this);
        activity_service_test_btn3.setOnClickListener(this);
        activity_service_test_btn4.setOnClickListener(this);
        activity_service_test_btn5.setOnClickListener(this);
        activity_service_test_btn6.setOnClickListener(this);
        activity_service_test_btn7.setOnClickListener(this);
        activity_service_test_btn8.setOnClickListener(this);
    }

    private void initData() {
        serviceConnection = new MyServiceConnection(this);
    }


    private Messenger serviceMger;

    private void initServiceMessenger(Messenger messenger) {
        serviceMger = messenger;
    }

    private int counter = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_service_test_btn1: {
                Log.e(TAG, "onClick: 启动服务");
                Intent intent = new Intent(ServiceTestActivity.this, DemoService.class);
                startService(intent);
                startModel = 1;
            }
            break;
            case R.id.activity_service_test_btn2: {
                Intent intent = new Intent(ServiceTestActivity.this, DemoService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                bindModel = 1;
                Log.e(TAG, "onClick: 绑定服务");
            }
            break;
            case R.id.activity_service_test_btn3: {
                if (serviceMger == null) {
                    throw new NullPointerException("请先绑定服务");
                } else {
                    Message message = Message.obtain(null, CLIENT_WHAT_SEND, 0, 0);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "a message from client... " + counter);
                    message.setData(bundle);
                    message.replyTo = localMger;
                    try {
                        serviceMger.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
            }
            break;
            case R.id.activity_service_test_btn4: {
                //绑定另一个进程中的Service,需要指定包名、action
                Intent intent = new Intent();
                intent.setAction("com.example.aidlservie");
                intent.setPackage("com.example.server");
                bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE);
            }
            break;
            case R.id.activity_service_test_btn5: {
                if (bookController != null) {
                    try {
                        List<Book> list = bookController.getBookList();
                        Log.e(TAG, "从服务器获得数据：" + new Gson().toJson(list));
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                }
            }
            break;
            case R.id.activity_service_test_btn6: {
                if (bookController != null) {
                    Book book = new Book();
                    book.setName("添加一本新书，添加时间：" + SystemClock.elapsedRealtime());
                    try {
                        bookController.addBookInOut(book);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                }
            }
            break;
            case R.id.activity_service_test_btn7: {
                if (bookController != null) {
                    Book book = new Book();
                    book.setName("一品芝麻狐:" + SystemClock.elapsedRealtime());
                    try {
                        bookController.addBookIn(book);
                        Log.e(TAG, "in tag 添加完后，书名= " + book.getName());
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                }
            }
            break;
            case R.id.activity_service_test_btn8: {
                if (bookController != null) {
                    Book book = new Book();
                    book.setName("一品芝麻狐:" + SystemClock.elapsedRealtime());
                    try {
                        bookController.addBookOut(book);
                        Log.e(TAG, "out tag 添加完后，书名= " + book.getName());
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                }
            }
            break;
        }
    }

    private final AidlConnection aidlConnection = new AidlConnection(this);

    private boolean aidlConnect = false;

    private void updateBindState(boolean connect) {
        aidlConnect = connect;
    }

    private BookController bookController;

    private void initBookController(IBinder iBinder) {
        bookController = BookController.Stub.asInterface(iBinder);
    }

    private static class AidlConnection implements ServiceConnection {

        private WeakReference<ServiceTestActivity> weakReference;

        public AidlConnection(ServiceTestActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceDisconnected: 进程服务链接");
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().updateBindState(true);
                weakReference.get().initBookController(service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: 进程服务断开链接");
        }
    }

    private Messenger localMger = new Messenger(new MyHandler());

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DemoService.SERVICE_WHAT_SEND:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String content = bundle.getString("msg");
                        Log.e(TAG, "accept message：" + content);
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }


    private static class MyServiceConnection implements ServiceConnection {
        private final String TAG = "ServiceConn";
        WeakReference<ServiceTestActivity> weakReference;

        public MyServiceConnection(ServiceTestActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: ");
            if (weakReference != null && weakReference.get() != null)
                weakReference.get().initServiceMessenger(new Messenger(service));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当服务意外结束的时候回调用该方法(系统崩溃、系统内存不足服务被杀死)，服务正常结束不会调用该方法
            Log.e(TAG, "onServiceDisconnected: ");
        }
    }

    @Override
    protected void onDestroy() {
        if (bindModel == 1) {
            Log.e(TAG, "解除绑定服务");
            unbindService(serviceConnection);
        }
        if (startModel == 1) {
            Log.e(TAG, "停止服务");
            Intent intent = new Intent(ServiceTestActivity.this, DemoService.class);
            stopService(intent);
        }

        if (aidlConnect) {
            Log.e(TAG, "onDestroy: 解除进程服务绑定");
            unbindService(aidlConnection);
        }
        aidlConnect = false;
        super.onDestroy();
    }
}

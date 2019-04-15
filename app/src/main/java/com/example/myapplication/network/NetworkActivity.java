package com.example.myapplication.network;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragment.OnItemClickedListener;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkActivity extends AppCompatActivity implements OnItemClickedListener {
    private static class MyHandler extends Handler {
        private WeakReference<NetworkActivity> weakReference;

        public MyHandler(NetworkActivity networkActivity) {
            weakReference = new WeakReference<NetworkActivity>(networkActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!NetworkSingleton.getInstance().isConnected()) {
                        if (weakReference != null && weakReference.get() != null) {
                            DialogSingleton.getInstance().showWifiDlg(weakReference.get());
                        }
                    }
                    break;
                case 2:
                    //测试handler执行顺序
                    Log.e("Handler","conduct");
                    break;
            }
        }
    }

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netwok);

        checkNetwork();
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_network_container);

        if (NetworkSingleton.getInstance().isConnected()) {//有网络
            if (fragment == null) {
                fragment = new NetworkContentFragment();
                fragmentManager.beginTransaction().add(R.id.activity_network_container, fragment).commit();
            } else {
                if (fragment instanceof NetworkErrorFragment) {
                    Fragment contentFragment = new NetworkContentFragment();
                    fragmentManager.beginTransaction().replace(R.id.activity_network_container, contentFragment).commit();
                }
            }
        } else {//没有网络
            if (fragment == null) {
                fragment = new NetworkErrorFragment();
                fragmentManager.beginTransaction().add(R.id.activity_network_container, fragment).commit();
            } else {
                if (fragment instanceof NetworkContentFragment) {
                    Fragment errorFragment = new NetworkErrorFragment();
                    fragmentManager.beginTransaction().replace(R.id.activity_network_container, errorFragment).commit();
                }
            }
        }
    }

    private void checkNetwork() {
        if (myHandler == null)
            myHandler = new MyHandler(this);
//        myHandler.sendEmptyMessageDelayed(1, 100);
        myHandler.sendEmptyMessage(2);
    }

    @Override
    public void onItemClicked(View view, int position, String content) {
        initFragment();
    }

    @Override
    protected void onDestroy() {
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}

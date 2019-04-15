package com.example.myapplication.network;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class DialogSingleton {

    private final String TAG = "DialogSingleton";
    private Dialog mWifiDialog;

    private DialogSingleton() {
    }

    private static class DialogState {
        public static final DialogSingleton dialogSingleton = new DialogSingleton();
    }

    public static DialogSingleton getInstance() {
        return DialogState.dialogSingleton;
    }

    public void showWifiDlg(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        if (mWifiDialog == null) {
            mWifiDialog = builder.setIcon(R.drawable.vector_drawable_ic_wifi_off)         //
                    .setTitle("wifi设置")            //
                    .setMessage("当前无网络").setPositiveButton("设置", new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(android.provider.Settings
                                        .ACTION_WIFI_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings",
                                        Settings.ACTION_WIFI_SETTINGS);
                            }
                            if ((context instanceof Application)) {
                                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            }
                            context.startActivity(intent);

                        }
                    }).setNegativeButton("知道了", null).create();
            // 设置为系统的Dialog，这样使用Application的时候不会 报错
            mWifiDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mWifiDialog.show();
    }
}
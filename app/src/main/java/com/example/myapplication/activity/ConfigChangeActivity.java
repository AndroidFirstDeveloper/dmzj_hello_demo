package com.example.myapplication.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class ConfigChangeActivity extends AppCompatActivity {

    private EditText activity_config_change_et;
    private TextView activity_config_change_tv;
    private String TAG="ConfigChangeAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_config_change);
        findView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }


    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_config_change_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("避免配置改变时Activity重建");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_config_change_et = findViewById(R.id.activity_config_change_et);
        activity_config_change_tv = findViewById(R.id.activity_config_change_tv);
        activity_config_change_tv.setText("为了避免由于配置改变导致Activity重建，可在AndroidManifest.xml中对应的Activity中设置android:configChanges=\"orientation|screenSize\"。此时再次旋转屏幕时，该Activity不会被系统杀死和重建，只会调用onConfigurationChanged。因此，当配置程序需要响应配置改变，指定configChanges属性，重写onConfigurationChanged方法即可。\n" +
                "    1、不设置Activity的android:configChanges时，切屏会重新调用各个生命周期方法（包括onCreate和onDestroy)，切横屏时会执行一次，切竖屏时会执行一次 \n" +
                "    2、设置Activity的android:configChanges=\"orientation\"时，切屏还是会重新调用各个生命周期方法，切横屏时会执行一次，切竖屏时会执行一次 \n" +
                "    3、设置Activity的android:configChanges=\"orientation|screenSize\"时，切屏不会重新调用各个生命周期方法，只会执行onConfigurationChanged方法" +
                "4、另外经测试，keyboardHidden对配置发生变化避免activity重建没有作用。");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
        String content = activity_config_change_et.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            Log.e(TAG, "onSaveInstanceState: content=" + content);
            outState.putString("content", content);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
        if (savedInstanceState != null) {
            String content = savedInstanceState.getString("content");
            Log.e(TAG, "onRestoreInstanceState: content=" + content);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextmenu_next:
//                Intent intent = new Intent(DispatchActivity2.this, DispatchActivity3.class);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

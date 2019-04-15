package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dispatch.BadFragmentAdapter;
import com.example.myapplication.dispatch.DispatchActivity2;
import com.example.myapplication.dispatch.DispatchActivity3;

public class SaveTestActivity extends AppCompatActivity {

    private final String TAG = "SaveTestActivity";
    private EditText activity_save_test_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_test);
        findView();
    }

    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_save_test_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("onSaveInstanceState 调用场景");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_save_test_et = findViewById(R.id.activity_save_test_et);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
        String content = activity_save_test_et.getText().toString().trim();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextmenu_next:
                Intent intent = new Intent(SaveTestActivity.this, ConfigChangeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}




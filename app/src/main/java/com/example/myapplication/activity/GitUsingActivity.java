package com.example.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class GitUsingActivity extends AppCompatActivity {

    private final String TAG = "GitUsingActivity";
    private TextView activity_git_using_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_using);
        findView();
        initData();
    }

    private void findView() {
        View activity_git_using_toolbar = findViewById(R.id.activity_git_using_toolbar);
        View status_view = activity_git_using_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_bar_layout_title_tv = activity_git_using_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        activity_git_using_tv = findViewById(R.id.activity_git_using_tv);

        title_bar_layout_title_tv.setText("git 命令集合");
        ImmersionBar.with(this).statusBarView(status_view).init();
    }

    private void initData() {
        initAssets();
    }

    public void initAssets() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("git_command.txt");
            String str = getString(inputStream);
            activity_git_using_tv.setText(str);
        } catch (IOException e) {
            Log.e(TAG, "initAssets1: ", e);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "initAssets2: ", e);
            }
        }
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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

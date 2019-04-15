package com.example.myapplication;

import android.app.usage.ExternalStorageStats;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.example.myapplication.fragment.OnItemClickedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.FileInputStream;
import java.io.IOException;

public class BuglyTestActivity extends AppCompatActivity implements View.OnClickListener {

    Button activity_bugly_test_btn1, activity_bugly_test_btn6, activity_bugly_test_btn2, activity_bugly_test_btn3, activity_bugly_test_btn4, activity_bugly_test_btn5;

    Button activity_bugly_test_btn7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugly_test);

        findView();
        setListener();
//        testNullPointException();
    }


    private void findView() {
        activity_bugly_test_btn1 = findViewById(R.id.activity_bugly_test_btn1);
        activity_bugly_test_btn2 = findViewById(R.id.activity_bugly_test_btn2);
        activity_bugly_test_btn3 = findViewById(R.id.activity_bugly_test_btn3);
        activity_bugly_test_btn4 = findViewById(R.id.activity_bugly_test_btn4);
        activity_bugly_test_btn5 = findViewById(R.id.activity_bugly_test_btn5);
        activity_bugly_test_btn6 = findViewById(R.id.activity_bugly_test_btn6);
        activity_bugly_test_btn7 = findViewById(R.id.activity_bugly_test_btn7);
    }


    private void setListener() {
        activity_bugly_test_btn6.setOnClickListener(this);
        activity_bugly_test_btn5.setOnClickListener(this);
        activity_bugly_test_btn4.setOnClickListener(this);
        activity_bugly_test_btn3.setOnClickListener(this);
        activity_bugly_test_btn2.setOnClickListener(this);
        activity_bugly_test_btn1.setOnClickListener(this);
        activity_bugly_test_btn7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_bugly_test_btn1:
                CrashReport.testJavaCrash();
                break;

            case R.id.activity_bugly_test_btn2:
                testNullPointException();
                break;
            case R.id.activity_bugly_test_btn3:
                testIoException();
                break;
            case R.id.activity_bugly_test_btn4:
                testUIConductException();
                break;
            case R.id.activity_bugly_test_btn5:
                testClassCastException();
                break;
            case R.id.activity_bugly_test_btn6:
                Beta.checkUpgrade();
                break;
            case R.id.activity_bugly_test_btn7:
                BaseModel<ChapterModel> baseModel = new BaseModel<>();
                baseModel.setCode(1);
                baseModel.setMsg("成功");
                ChapterModel chapterModel = new ChapterModel();
                chapterModel.setName("张三");
                chapterModel.setData("你呗临时禁言");
                baseModel.setData(chapterModel);
                String sss = new Gson().toJson(baseModel);
                Log.e("Bugly", sss);
                BaseModel<ChapterModel> modelBaseModel = new Gson().fromJson(sss, new TypeToken<BaseModel<ChapterModel>>() {
                }.getType());
                Log.e("Bugly", modelBaseModel.toString());
                break;
        }
    }


    private void testNullPointException() {
        String ss = null;
        int length = ss.length();
    }

    private void testIoException() {
        byte[] resource = new byte[1000];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("czl.txt");
            fileInputStream.read(resource);
            if (fileInputStream != null)
                fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void testUIConductException() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity_bugly_test_btn1.setText("hell world");
            }
        }).start();
    }

    private void testClassCastException() {
        OnItemClickedListener onItemClickedListener = (OnItemClickedListener) this;
    }

}

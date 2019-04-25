package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button activity_list_first_btn = findViewById(R.id.activity_list_first_btn);
        Button activity_list_second_btn = findViewById(R.id.activity_list_second_btn);
        activity_list_first_btn.setOnClickListener(this);
        activity_list_second_btn.setOnClickListener(this);
        registBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_list_first_btn:
                testList();
                break;
            case R.id.activity_list_second_btn:
                testList2();
                break;
        }
    }

    private void testList() {
        /**利用HashSet元素不重复的特性， 去除集合中的重复元素，如果集合元素是对象的话，需要实现对象equals、hashCode方法*/
        List<PersonModel> list = new ArrayList<>();
        list.add(new PersonModel("张三", "小张", 23));
        list.add(new PersonModel("李四", "小李", 25));
        list.add(new PersonModel("王五", "老王", 53));
        list.add(new PersonModel("张三", "张队", 33));
        List<PersonModel> newList = new ArrayList<>(new HashSet<>(list));
        Log.e(TAG, "去除重复后的集合：" + new Gson().toJson(newList));
    }


    private void testList2() {
        /**获取集合中不同的元素、相同的元素*/
        List<PersonModel> list1 = new ArrayList<>();
        list1.add(new PersonModel("小明", "明明", 11));
        list1.add(new PersonModel("小丽", "丽丽", 11));
        list1.add(new PersonModel("小红", "红红", 11));

        List<PersonModel> list2 = new ArrayList<>();
        list2.add(new PersonModel("小刚", "刚刚", 11));
        list2.add(new PersonModel("小强", "强强", 11));
        list2.add(new PersonModel("小红", "红红", 11));
        list1.removeAll(list2);
        Log.e(TAG, "list1中和list2不同的元素：" + new Gson().toJson(list1));

        List<PersonModel> list3 = new ArrayList<>();
        list3.add(new PersonModel("小明", "明明", 11));
        list3.add(new PersonModel("小丽", "丽丽", 11));
        list3.add(new PersonModel("小红", "红红", 11));

        List<PersonModel> list4 = new ArrayList<>();
        list4.add(new PersonModel("小刚", "刚刚", 11));
        list4.add(new PersonModel("小强", "强强", 11));
        list4.add(new PersonModel("小红", "红红", 11));
        List<PersonModel> list5 = new ArrayList<>(list4);
        list4.removeAll(list3);
        Log.e(TAG, "list4中和list3不同的元素：" + new Gson().toJson(list4));

        list5.removeAll(list4);
        Log.e(TAG, "list4中和list3相同的元素：" + new Gson().toJson(list5));
    }


    static class PersonModel {

        private String name;
        private String title;
        private int age;


        public PersonModel(String name, String title, int age) {
            this.name = name;
            this.title = title;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            return obj instanceof PersonModel && TextUtils.equals(this.name, ((PersonModel) obj).name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private void unregistBroadcast() {
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);
            Log.e(TAG, "unregistBroadcast: 解除注册广播");
        }
    }

    private void registBroadcast() {
        if (myBroadcastReceiver == null) {
            Log.e(TAG, "registBroadcast: 注册广播");
            myBroadcastReceiver = new MyBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("test_broad_cast_unregist");
            registerReceiver(myBroadcastReceiver, intentFilter);
        }
    }

    private MyBroadcastReceiver myBroadcastReceiver;


    static class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    protected void onDestroy() {
        unregistBroadcast();
        super.onDestroy();
    }
}

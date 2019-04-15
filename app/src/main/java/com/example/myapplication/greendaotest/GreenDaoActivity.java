package com.example.myapplication.greendaotest;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.SampleApplication;
import com.example.myapplication.gen.StudentModelDao;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GreenDaoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        findView();
    }


    private void findView() {
        Button activity_green_dao_insert_btn = findViewById(R.id.activity_green_dao_insert_btn);
        Button activity_green_dao_delete_btn = findViewById(R.id.activity_green_dao_delete_btn);
        Button activity_green_dao_query_btn = findViewById(R.id.activity_green_dao_query_btn);
        Button activity_green_dao_thread_query_btn = findViewById(R.id.activity_green_dao_thread_query_btn);
        Button activity_green_dao_thread_insert_btn = findViewById(R.id.activity_green_dao_thread_insert_btn);

        activity_green_dao_insert_btn.setOnClickListener(this);
        activity_green_dao_delete_btn.setOnClickListener(this);
        activity_green_dao_query_btn.setOnClickListener(this);
        activity_green_dao_thread_query_btn.setOnClickListener(this);
        activity_green_dao_thread_insert_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_green_dao_insert_btn:
                insertData();
                break;
            case R.id.activity_green_dao_delete_btn:
                deleteData();
                break;
            case R.id.activity_green_dao_query_btn:
                queryData();
                break;
            case R.id.activity_green_dao_thread_query_btn:
                threadQueryData();
                break;
            case R.id.activity_green_dao_thread_insert_btn:
                threadInsertData();
                break;
        }
    }

    private void insertData() {
        double seed = Math.random();
        int name = (int) (seed * 25);
        int age = (int) (seed * 100);
        StudentModel studentModel = new StudentModel();
        studentModel.setName("" + ((char) ((int) ('a') + name)));
        studentModel.setAge(age);
        studentModel.setBirth("1995-5-23");
        studentModel.setAddress("北京市 朝阳区 望京科技园");
        SampleApplication.getDaoInstant().getStudentModelDao().insert(studentModel);

        List<StudentModel> list = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().list();
        if (list != null && list.size() > 15) {
//                Log.e(TAG, "insertData: 表里数据=" + new Gson().toJson(list));
        }
    }

    /**
     * 测试条件查询
     */
    private void queryData() {
        /**
         * 说明：where中的条件语句是与的关系;
         * gt的意思是大于,不包括等于;
         * lt的意思是小于,不包括等于;
         * ge的意思是大于等于;
         * le的意思是小于等于;
         * 当希望只查询一条结果，但是有多条查询结果时，只使用unique()会报错，使用limit(1)+unique()当有多条查询结果，或者查询结果为null都不会报错;
         * */
        List<StudentModel> list = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("l"), StudentModelDao.Properties.Age.gt(35)).list();
        if (list != null) {
            Log.e(TAG, "queryData: 表里数据=" + new Gson().toJson(list));
        }

        List<StudentModel> list2 = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("r"), StudentModelDao.Properties.Age.lt(36)).list();
        if (list2 != null) {
            Log.e(TAG, "queryData: 表里数据=" + new Gson().toJson(list2));
        }

        List<StudentModel> list3 = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("l"), StudentModelDao.Properties.Age.ge(35)).list();
        if (list3 != null) {
            Log.e(TAG, "queryData: 表里数据=" + new Gson().toJson(list3));
        }

        List<StudentModel> list4 = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("r"), StudentModelDao.Properties.Age.le(36)).list();
        if (list4 != null) {
            Log.e(TAG, "queryData: 表里数据=" + new Gson().toJson(list4));
        }

        StudentModel studentModel = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("r")).limit(1).unique();
        if (studentModel != null) {
            Log.e(TAG, "queryData: 表里数据=" + new Gson().toJson(studentModel));
        }
    }

    /**
     * 测试批量删除数据
     */
    private void deleteData() {
        List<StudentModel> list = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("m"), StudentModelDao.Properties.Age.lt(45)).list();
        if (list != null && list.size() > 0) {
            Log.e(TAG, "deleteData: 表里数据=" + new Gson().toJson(list));
            /*for (StudentModel studentModel : list) {
                SampleApplication.getDaoInstant().getStudentModelDao().delete(studentModel);
            }*/
            SampleApplication.getDaoInstant().getStudentModelDao().deleteInTx(list);
        }
    }


    /**
     * 多线程插入数据
     */
    private void threadInsertData() {
        final String divider = "-";
        final int maxCount = 20000;
        final int addCount = 3000;
        final Query query = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = SystemClock.elapsedRealtime();
                StringBuilder stringBuilder = new StringBuilder();
//                StringBuilder stringBuilder2 = new StringBuilder();
                List<StudentModel> list = query.forCurrentThread().list();
                if (list == null || list.size() < maxCount) {
                    int itemCount = (maxCount - list.size()) > addCount ? addCount : (maxCount - list.size());
                    for (int i = 0; i < itemCount; i++) {
                        double seed = Math.random();
                        int name = (int) (seed * 25);
                        int age = (int) (seed * 100);
                        int month = (int) (seed * 11) + 1;
                        int day = (int) (seed * 27) + 1;
                        stringBuilder.append(2018 - age).append(divider).append(month).append(divider).append(day);

                        StudentModel studentModel = new StudentModel();
                        studentModel.setName("" + ((char) ((int) ('a') + name)));
//            stringBuilder2.append(((char) ((int) ('a') + name)));
//            studentModel.setName(stringBuilder2.toString());
                        studentModel.setAge(age);
                        studentModel.setBirth(stringBuilder.toString());
                        studentModel.setAddress("北京市 朝阳区 望京科技园");
                        SampleApplication.getDaoInstant().getStudentModelDao().insert(studentModel);
                        stringBuilder.delete(0, stringBuilder.toString().length());
//            stringBuilder2.delete(0,stringBuilder2.toString().length());
                    }
                    long endTime = SystemClock.elapsedRealtime();
                    Log.e(TAG, "多线程添加 " + itemCount + " 条数据耗时 " + (endTime - startTime) + " 毫秒");
                }
            }
        }).start();
    }

    /**
     * 测试多线程查询
     */
    private void threadQueryData() {
        final Query query = SampleApplication.getDaoInstant().getStudentModelDao().queryBuilder().where(StudentModelDao.Properties.Name.eq("m"), StudentModelDao.Properties.Age.gt(22)).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = SystemClock.elapsedRealtime();
                List<StudentModel> list = query.forCurrentThread().list();
                if (list != null && list.size() > 0) {
                    long endTime = SystemClock.elapsedRealtime();
                    Log.e(TAG, "多线程查询 " + list.size() + " 条数据耗时 " + (endTime - startTime) + " 毫秒");
                }
            }
        }).start();
    }
}

package com.example.myapplication.fragment3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ConfigChangeActivity extends AppCompatActivity implements FragmentFour.TaskCallback, View.OnClickListener {

    private Toolbar activity_config_change2_toolbar;
    private ProgressBar activity_config_change2_progress_bar;
    private Button activity_config_change2_button;
    private TextView activity_config_change2_tv1, activity_config_change2_tv2;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change2);

        findView();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("FragmentFour");
        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(new FragmentFour(), "FragmentFour");
            transaction.commit();
        }

        if (savedInstanceState != null) {
            activity_config_change2_tv1.setText((savedInstanceState.getInt("progress") + "%"));
        }
    }

    private void findView() {
        activity_config_change2_toolbar = findViewById(R.id.activity_config_change2_toolbar);
        activity_config_change2_toolbar.setTitleTextColor(Color.WHITE);
        activity_config_change2_toolbar.setTitle("配置变化防止fragment重构");
        setSupportActionBar(activity_config_change2_toolbar);

        activity_config_change2_progress_bar = findViewById(R.id.activity_config_change2_progress_bar);
        activity_config_change2_button = findViewById(R.id.activity_config_change2_button);
        activity_config_change2_tv1 = findViewById(R.id.activity_config_change2_tv1);
        activity_config_change2_tv2 = findViewById(R.id.activity_config_change2_tv2);
        activity_config_change2_button.setOnClickListener(this);

        activity_config_change2_tv2.setText("当配置发生改变的时候，系统会重新创建Activity，调用其onCreate、onDestroy方法，依附在Activity上的Fragment也会进行重建，重建会调用其onDestroyView、onDestroy、onDetach方法。" +
                "视图重建会导致很多问题，试想在一个Activity中启动了一个AsyncTask然后用户很快就旋转了屏幕,当这个AsyncTask最后完成它的工作并返回时，它将会错误地报告它的结果给旧的Activity实例，" +
                "完全不知道有一个新的Activity实例被创建了。似乎这并不完全是个问题，但是这个新的Activity实例可能会重新启动一个后台任务（因为它不知道旧的AsyncTask尚在运行），从而浪费宝贵的资源。\n" +
                "我们该如何处理配置变化导致的问题呢，网上说的比较多的处理配置变化的方法是在Android manifest中的设置android:configChanges=keyboardHidden|orientation属性，这种方法确实可以防止手机在屏幕旋转时销毁重建，" +
                "但配置变化不仅仅局限于屏幕旋转，还包括：语言变化、SIM卡掉卡、网络状态变化等。比较适合的方法是调用Fragment#setRetainInstance(true)允许我们绕开销毁-重建循环，发信号给系统让其在宿主Activity被重建时保留现在的Fragment实例。" +
                "经测试设置setRetainInstance后，配置变化时activity会继续销毁:onDestroy，fragment也会销毁视图：onDetach、onDestroyView，但是fragment不会销毁实例，不会调用onDestroy，视图重建不会调用onCreate。");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (progress > 0)
            outState.putInt("progress", progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_config_change2_button) {
            Fragment fragment = getFragmentManager().findFragmentByTag("FragmentFour");
            if (fragment != null) {
                ((FragmentFour) fragment).cancelTask();
            }
        }
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, "Task Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, "Task Finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(Integer progress) {
        activity_config_change2_progress_bar.setProgress(progress);
        activity_config_change2_tv1.setText(progress + "%");
        this.progress = progress;
    }

    @Override
    public void onCancelled() {
        Toast.makeText(this, "Task Canceled", Toast.LENGTH_SHORT).show();
    }
}

package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.Fragment1;
import com.example.myapplication.fragment.Fragment2;
import com.example.myapplication.fragment.OnItemClickedListener;
import com.gyf.barlibrary.ImmersionBar;

public class FragmentLifeCycleActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life_cycle);
        findView();
    }

    private void findView() {

        View activity_fragment_life_cycle_toolbar = findViewById(R.id.activity_fragment_life_cycle_toolbar);
        View status_view = activity_fragment_life_cycle_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_bar_layout_operation_tv = activity_fragment_life_cycle_toolbar.findViewById(R.id.title_bar_layout_operation_tv);
        TextView title_bar_layout_title_tv = activity_fragment_life_cycle_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        title_bar_layout_operation_tv.setVisibility(View.VISIBLE);
        title_bar_layout_operation_tv.setText("跳转");
        title_bar_layout_title_tv.setText("Activity中Fragment生命周期");
        Button activity_fragment_life_cycle_btn1 = findViewById(R.id.activity_fragment_life_cycle_btn1);
        Button activity_fragment_life_cycle_btn2 = findViewById(R.id.activity_fragment_life_cycle_btn2);

        activity_fragment_life_cycle_btn2.setOnClickListener(this);
        activity_fragment_life_cycle_btn1.setOnClickListener(this);
        title_bar_layout_operation_tv.setOnClickListener(this);
        ImmersionBar.with(this).statusBarView(status_view).init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_fragment_life_cycle_btn1:
                showFragment1();
                break;
            case R.id.activity_fragment_life_cycle_btn2:
                showFragment2();
                break;
            case R.id.title_bar_layout_operation_tv:
                Intent intent = new Intent(FragmentLifeCycleActivity.this, FragmentLifeCycleActivity2.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClicked(View view, int position, String content) {

    }

    public void onBack(View view) {
        finish();
    }

    private void showFragment1() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment2 = fragmentManager.findFragmentByTag("Fragment2");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment2 != null) {
            transaction.remove(fragment2);
        }
        Fragment fragment1 = fragmentManager.findFragmentByTag("Fragment1");
        if (fragment1 == null) {
            fragment1 = new Fragment1();
            transaction.add(R.id.activity_fragment_life_cycle_container, fragment1, "Fragment1");
        } else {
            transaction.show(fragment1);
        }
        transaction.commit();
    }

    private void showFragment2() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentByTag("Fragment1");
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragment1 != null) {
            transaction.remove(fragment1);
        }

        Fragment fragment2 = fragmentManager.findFragmentByTag("Fragment2");
        if (fragment2 == null) {
            fragment2 = new Fragment2();
            transaction.add(R.id.activity_fragment_life_cycle_container, fragment2, "Fragment2");
        } else {
            transaction.show(fragment2);
        }
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        Log.e("FragLifeCycle1", "onDestroy: ");
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

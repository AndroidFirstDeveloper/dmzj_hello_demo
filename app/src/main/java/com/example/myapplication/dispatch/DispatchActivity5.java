package com.example.myapplication.dispatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class DispatchActivity5 extends AppCompatActivity {

    private HorizontalEx2 activity_dispatch5_horizontalex2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch5);
        findView();
        initData();
    }

    private void findView() {
        Toolbar activity_dispatch_toolbar = findViewById(R.id.activity_dispatch5_toolbar);
        activity_dispatch_toolbar.setTitleTextColor(Color.WHITE);
        activity_dispatch_toolbar.setTitle("事件分发5");
        setSupportActionBar(activity_dispatch_toolbar);
        activity_dispatch_toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        activity_dispatch_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_dispatch5_horizontalex2 = findViewById(R.id.activity_dispatch5_horizontalex2);
    }

    private void initData() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list1.add("First List ITEM" + i);
            list2.add("Second List ITEM" + i);
            list3.add("Third List ITEM" + i);
        }
        showOutHVData(list1, list2, list3);
    }


    public void showOutHVData(List<String> data1, List<String> data2, List<String> data3) {
        ListViewEx listView1 = new ListViewEx(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        listView1.setAdapter(adapter1);
        listView1.setmHorizontalEx2(activity_dispatch5_horizontalex2);

        ListViewEx listView2 = new ListViewEx(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);
        listView2.setAdapter(adapter2);
        listView2.setmHorizontalEx2(activity_dispatch5_horizontalex2);

        ListViewEx listView3 = new ListViewEx(this);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data3);
        listView3.setAdapter(adapter3);
        listView3.setmHorizontalEx2(activity_dispatch5_horizontalex2);

        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        activity_dispatch5_horizontalex2.addView(listView1, params);
        activity_dispatch5_horizontalex2.addView(listView2, params);
        activity_dispatch5_horizontalex2.addView(listView3, params);
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
                Intent intent = new Intent(DispatchActivity5.this, DispatchActivity6.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

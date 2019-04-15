package com.example.myapplication.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.recyclerview.define.PowerfulRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<MyItemBean> list;
    private int transform_style = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
    }

    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_recycler_view_toolbar);
        toolbar.setTitle("仿ListView");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewActivity.this.finish();
            }
        });
        PowerfulRecyclerView powerfulRecyclerView = (PowerfulRecyclerView) findViewById(R.id.activity_recycler_view_prv);
        powerfulRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        /*powerfulRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));*/
        /*powerfulRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));*/
        /*powerfulRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));*/
        View headerview = LayoutInflater.from(this).inflate(R.layout.recyclerview_headerview_01, powerfulRecyclerView, false);
        View headerview2 = LayoutInflater.from(this).inflate(R.layout.recyclerview_headerview_02, powerfulRecyclerView, false);
        View footerview = LayoutInflater.from(this).inflate(R.layout.recyclerview_footerview_01, powerfulRecyclerView, false);
        powerfulRecyclerView.addHeaderView(headerview);
        powerfulRecyclerView.addHeaderView(headerview2);
        powerfulRecyclerView.addFooterView(footerview);

        if (list == null)
            list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < 16; i++) {
            MyItemBean myItemBean = new MyItemBean();
            myItemBean.setPhone("15222633502");
            myItemBean.setName("Mr cui");
//            https://www.tpctax.com/wp-content/uploads/2018/05/Wa-Tax-May-email-header-450x450.jpg
//            http://mysmcac.org/wp-content/uploads/2017/07/pexels-photo-27633-450x450.jpg
//            https://p.nanrenwo.net/uploads/allimg/180703/8473-1PF30TR6.png                //white house
//            https://www.hnw.org/wp-content/uploads/2017/09/WORDS-TO-LIVE-BY_SQUARE-01-450x450.png
//            https://www.xtracycle.com/wp-content/uploads/2018/06/Screenshot-2018-06-23-at-8.38.21-AM-450x450.png
            myItemBean.setImage("https://www.hnw.org/wp-content/uploads/2017/09/WORDS-TO-LIVE-BY_SQUARE-01-450x450.png");
            list.add(myItemBean);
        }
        if (myRecyclerViewAdapter == null)
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(list, this);
        powerfulRecyclerView.setAdapter(myRecyclerViewAdapter);

        View footerview2 = LayoutInflater.from(this).inflate(R.layout.recyclerview_footerview_02, powerfulRecyclerView, false);
        powerfulRecyclerView.addFooterView(footerview2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addmenu_add:
                MyItemBean myItemBean = new MyItemBean();
                myItemBean.setPhone("18332656190");
                myItemBean.setName("Mr zhang");
//                myItemBean.setImage("https://p.nanrenwo.net/uploads/allimg/180703/8473-1PF30TR6.png");
                myItemBean.setImage("https://www.glide.org/image/congregational-life/Church.jpg");
                list.add(myItemBean);
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.addmenu_del:
                if (list.size() > 0) {
                    list.remove(list.size() - 1);
                }
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.addmenu_pre:
                myRecyclerViewAdapter.setTransform_style(((transform_style--) < 0 ? (transform_style = 0) : transform_style) % 15);
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.addmenu_next:
                myRecyclerViewAdapter.setTransform_style(++transform_style % 15);
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.next_page:
                Intent intent = new Intent(this, NestActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

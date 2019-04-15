package com.example.myapplication.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.dispatch.DispatchActivity4;
import com.example.myapplication.dispatch.DispatchActivity5;

import java.util.ArrayList;
import java.util.List;

public class NestActivity extends AppCompatActivity {

    private RecyclerView activity_nest_recycler_view;
    private final List<NestModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);
        findView();
        initData();
    }

    private void findView() {
        Toolbar toolbar = findViewById(R.id.activity_nest_toolbar);
        toolbar.setTitle("RecyclerView嵌套横向RecyclerView");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NestActivity.this.finish();
            }
        });
        activity_nest_recycler_view = findViewById(R.id.activity_nest_recycler_view);
        activity_nest_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        for (int i = 0; i < 10000; i++) {
            NestModel nestModel = new NestModel();
            nestModel.setOffset(0);
            nestModel.setPosition(0);
            nestModel.setIndex(i);
            List<NestModel.ItemModel> itemModels = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                NestModel.ItemModel itemModel = new NestModel.ItemModel();
                itemModel.setTitle("Jade Rabbit Two-"+j);
                switch ((j + i) % 15) {
                    case 0:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584215648&di=aab8d1a3b54a18221a549469817c10a8&imgtype=0&src=http%3A%2F%2Fimgbdb2.bendibao.com%2Fnnbdb%2F201312%2F2%2F20131202092556_76319.jpg");
                        break;
                    case 1:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503718&di=49f5d2f8bacb29659a94cdcd2cb4b197&imgtype=0&src=http%3A%2F%2Fepaper.scdaily.cn%2Fscrb%2F20181226%2Fm_d852c6de0e6b67f7687acf82b1555171.jpg");
                        break;
                    case 2:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503718&di=7e6a18fd6f825449e74a09cb8a9e1fc1&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fsh%2Fpics%2Fhv1%2F242%2F35%2F1481%2F96311192.jpg");
                        break;
                    case 3:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503735&di=5068db90385a92b846a5970758ec504e&imgtype=0&src=http%3A%2F%2Fres.cjyun.org%2Fa%2F10008%2F201609%2F6561b3dfe687eea5c166b0fdf7016368.jpeg");
                        break;
                    case 4:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503734&di=9d54c4a16db35b0ab13ac7b4f36464d4&imgtype=0&src=http%3A%2F%2Fmmbiz.qpic.cn%2Fmmbiz_jpg%2FwepemY4v07Nc8ImfJCLoicN1QibVgksBMQ6c8N7nrDrn7zEfNTkSHdnqbicofsrPqhW8qH3KYpZWOOSFpYrcx14JQ%2F0%3Fwx_fmt%3Djpeg");
                        break;
                    case 5:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503733&di=fb62b5aed65dcd18b8364e290be306cc&imgtype=0&src=http%3A%2F%2Fp0.so.qhmsg.com%2Ft018bf7fc869adce78f.jpg");
                        break;
                    default:
                        itemModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546584503730&di=a31906572a17ac640d0d3d8126cc7ae9&imgtype=0&src=http%3A%2F%2Fimage.bitautoimg.com%2Fappimage%2Fmedia%2F20170224%2Fw547_h409_4bff60e81f8a452087f94089d39647cb.jpeg");
                        break;
                }
                itemModels.add(itemModel);
            }
            nestModel.setImages(itemModels);
            list.add(nestModel);
        }
        NestAdapter nestAdapter = new NestAdapter(this, list);
        activity_nest_recycler_view.setAdapter(nestAdapter);
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
//                Intent intent = new Intent(DispatchActivity4.this, DispatchActivity5.class);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

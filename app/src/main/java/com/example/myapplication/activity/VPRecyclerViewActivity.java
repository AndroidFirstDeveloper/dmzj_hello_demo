package com.example.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.VPAdapter;
import com.example.myapplication.view.VPRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VPRecyclerViewActivity extends AppCompatActivity {

    private VPRecyclerView activity_vprecyclerview_layout_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprecyclerview_layout);
        findView();
    }


    private void findView() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("item " + i);
        }
        activity_vprecyclerview_layout_rv = findViewById(R.id.activity_vprecyclerview_layout_rv);
        activity_vprecyclerview_layout_rv.setAdapter(new VPAdapter(this, list));

        activity_vprecyclerview_layout_rv.scrollToPosition(100);
    }


}

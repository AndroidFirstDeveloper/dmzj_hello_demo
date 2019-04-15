package com.example.myapplication.refresh;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.myapplication.R;


import java.util.ArrayList;
import java.util.List;

public class RefreshLoadActivity extends AppCompatActivity implements OnRefreshListener ,OnLoadMoreListener{

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_load);
        findViews();
    }

    private void findViews() {
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        for(int i=0;i<10;i++){
            list.add("item\t "+i+"\trefresh");
        }
        TestAdatper adatper=new TestAdatper(list,this);
        recyclerView.setAdapter(adatper);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add("item\t\t"+list.size()+"\trefresh");
                list.add("item\t "+list.size()+"\trefresh");
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add("item\t\t"+list.size()+"\tloadmore");
                list.add("item\t "+list.size()+"\tloadmore");
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);
    }

    public class TestAdatper extends RecyclerView.Adapter<TestAdatper.TestViewHolder> {
        private List<String> list;
        private Context context;

        public TestAdatper(List<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(context);
            return new TestViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public class TestViewHolder extends RecyclerView.ViewHolder {
            public TestViewHolder(View itemView) {
                super(itemView);
                RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemView.setLayoutParams(layoutParams);
                itemView.setPadding(48,48,48,48);
                ((TextView)itemView).setTextSize(20);
                ((TextView)itemView).setTextColor(Color.BLACK);
            }
        }
    }
}

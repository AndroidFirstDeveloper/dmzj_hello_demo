package com.example.myapplication.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.widget.StickyHeaderLayout;
import com.example.myapplication.R;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements OnLoadMoreListener, OnRefreshListener {

    /**
     * 可以实现分组，也可以实现吸顶，但是无法同时实现上拉加载和吸顶
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        findView();
        addListener();
    }

    private SwipeToLoadLayout activity_group_swipe_layout;
    private List<GroupModel> list;
    private MyAdapter myAdapter;
    private StickyHeaderLayout activity_group_sticky;

    private void findView() {
        View activity_group_toolbar = findViewById(R.id.activity_group_toolbar);
        View title_bar_layout_status_view = activity_group_toolbar.findViewById(R.id.title_bar_layout_status_view);
        TextView title_bar_layout_title_tv = activity_group_toolbar.findViewById(R.id.title_bar_layout_title_tv);
        title_bar_layout_title_tv.setText("分组列表");
        ImmersionBar.with(this).statusBarView(title_bar_layout_status_view).init();

        RecyclerView recyclerView = findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list = getData();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        activity_group_swipe_layout = findViewById(R.id.activity_group_swipe_layout);
//        activity_group_sticky = findViewById(R.id.activity_group_sticky);
//        activity_group_sticky.setSticky(true);

    }

    private void addListener() {
        activity_group_swipe_layout.setOnLoadMoreListener(this);
        activity_group_swipe_layout.setOnRefreshListener(this);
    }

    private static class MyAdapter extends GroupedRecyclerViewAdapter {

        private List<GroupModel> list;

        public MyAdapter(Context context, List<GroupModel> list) {
            super(context);
            this.list = list;
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list.get(groupPosition).getList().size();
        }

        @Override
        public boolean hasHeader(int groupPosition) {
            return true;
        }

        @Override
        public boolean hasFooter(int groupPosition) {
            return false;
        }

        @Override
        public int getHeaderLayout(int viewType) {
            return R.layout.group_header_layout;
        }

        @Override
        public int getFooterLayout(int viewType) {
            return 0;
        }

        @Override
        public int getChildLayout(int viewType) {
            return R.layout.group_item_layout;
        }

        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
            holder.setText(R.id.group_header_layout_tv, list.get(groupPosition).getGroupName());
        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
            holder.setText(R.id.group_item_layout_tv, list.get(groupPosition).getList().get(childPosition));
        }
    }

    private static class GroupModel {
        private String groupName;
        private List<String> list;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }


    private List<GroupModel> getData() {
        GroupModel model1 = new GroupModel();
        model1.setList(new ArrayList<String>());
        GroupModel model2 = new GroupModel();
        model2.setList(new ArrayList<String>());
        GroupModel model3 = new GroupModel();
        model3.setList(new ArrayList<String>());
        GroupModel model4 = new GroupModel();
        model4.setList(new ArrayList<String>());

        for (int i = 0; i < 55; i++) {
            if (i < 12) {
                model1.setGroupName("5月20号");
                model1.getList().add("item " + i);
            } else if (i < 25) {
                model2.setGroupName("5月19号");
                model2.getList().add("item " + i);
            } else if (i < 35) {
                model3.setGroupName("5月18号");
                model3.getList().add("item " + i);
            } else {
                model4.setGroupName("5月17号");
                model4.getList().add("item " + i);
            }
        }

        List<GroupModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        return list;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity_group_swipe_layout.setLoadingMore(false);
                if (list != null) {
                    final int position = list.get(list.size() - 1).getList().size();
                    list.get(list.size() - 1).getList().add("item add");
                    myAdapter.notifyChildRangeInserted(list.size() - 1, position, 1);
                }
            }
        }, 2000);
    }
}

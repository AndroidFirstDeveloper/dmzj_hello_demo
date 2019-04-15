package com.example.myapplication.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragment.OnItemClickedListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.recyclerview.RvTestAdapter.PRAISE_CHANGE;

public class RvTestActivity extends AppCompatActivity implements OnItemClickedListener {

    private RecyclerView activity_rv_test_recycler_view;
    private Toolbar activity_rv_test_toolbar;
    private RvTestAdapter adapter;
    private List<RvTestModel> list = new ArrayList<>();

    private final String HTTPS_IMAGE_PATH = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private final String HTTP_IMAGE_PATH = "http://mafangvvo.com/cxkindeditor/attached/image/20181122/2018112213050664664.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_test);
        findView();
    }

    private void findView() {
        activity_rv_test_toolbar = findViewById(R.id.activity_rv_test_toolbar);
        activity_rv_test_toolbar.setTitle("RecyclerView");
        setSupportActionBar(activity_rv_test_toolbar);
        activity_rv_test_recycler_view = findViewById(R.id.activity_rv_test_recycler_view);
        activity_rv_test_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        for (int i = 0; i < 6; i++) {
            RvTestModel model = new RvTestModel();
            model.setImagePath(HTTPS_IMAGE_PATH);
            model.setTitle("item" + i);
            model.setContent("雪乡-中国最北，中国最美，中国最好客的乡村欢迎你！");
            list.add(model);
        }
        adapter = new RvTestAdapter(this, list);
        adapter.setOnItemClickedListener(this);
        activity_rv_test_recycler_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_remove_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu_item:
                RvTestModel model = new RvTestModel();
                model.setImagePath("http://mafangvvo.com/cxkindeditor/attached/image/20181122/2018112213050664664.jpg");
                model.setTitle("item" + list.size());
                model.setContent("雪乡-中国最北，中国最美，中国最好客的乡村欢迎你！");
                int position = (list == null ? 0 : (list.size() > 4 ? 4 : list.size()));
                list.add(position, model);
                adapter.notifyItemInserted(position);
                adapter.notifyItemRangeChanged(position, list.size() - position);
                break;
            case R.id.add_menu_items:
                List<RvTestModel> set = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    RvTestModel model2 = new RvTestModel();
                    model2.setImagePath("http://mafangvvo.com/cxkindeditor/attached/image/20181122/2018112213050664664.jpg");
                    model2.setTitle("item" + (list.size() + i));
                    model2.setContent("雪乡-中国最北，中国最美，中国最好客的乡村欢迎你！");
                    set.add(model2);
                }
                int position2 = (list == null ? 0 : (list.size() > 4 ? 4 : list.size()));
                list.addAll(position2, set);
                adapter.notifyItemRangeInserted(position2, set.size());
                adapter.notifyItemRangeChanged(position2, list.size() - position2);
                break;
            case R.id.remove_menu_item:
                int position3 = (list == null ? -1 : (list.size() > 4 ? 4 : list.size() - 1));
                if (position3 > 0) {
                    list.remove(position3);
                    adapter.notifyItemRemoved(position3);
                    adapter.notifyItemRangeChanged(position3, list.size() - position3);
                } else {
                    Toast.makeText(this, "剩余item无法删除", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.remove_menu_items:
                int position4 = (list == null ? 0 : (list.size() > 4 ? 4 : list.size()));
                List<RvTestModel> set2 = new ArrayList<>();
                if (list.size() - position4 >= 2) {
                    set2.add(list.get(position4));
                    set2.add(list.get(position4 + 1));
                    list.removeAll(set2);
                    adapter.notifyItemRangeRemoved(position4, set2.size());
                    adapter.notifyItemRangeChanged(position4, list.size() - set2.size());
                } else {
                    Toast.makeText(this, "剩余item无法删除", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }


    @Override
    public void onItemClicked(View view, int position, String content) {
        adapter.notifyItemChanged(position, PRAISE_CHANGE);
    }
}

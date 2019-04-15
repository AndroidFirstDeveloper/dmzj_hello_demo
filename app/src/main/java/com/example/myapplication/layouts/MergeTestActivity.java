package com.example.myapplication.layouts;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MergeTestActivity extends AppCompatActivity {

    private String TAG = "MergeTestActivity";
    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_test);
        findViews();
        setListener();
        initData();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.activity_merge_test_toolbar);
        toolbar.setTitle("merge");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button1 = findViewById(R.id.merge_layout_01_button1);
        button2 = findViewById(R.id.merge_layout_01_button2);

    }

    private void setListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "merge first button1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "merge second button1");
            }
        });
    }

    private void initData() {

      /*  Fragment fragment = getSupportFragmentManager().findFragmentByTag("merge_fragment");
        if (fragment == null) {
            fragment = new MergeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_merge_test_container, fragment,"merge_fragment").commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextmenu_next:
                Intent intent = new Intent(this, ViewStubActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}

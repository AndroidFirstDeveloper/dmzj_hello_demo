package com.example.myapplication.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";
    private Toolbar activity_fragment_content_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate()");
        setContentView(R.layout.activity_fragment_content);
        findView();
    }

    private void findView() {
        activity_fragment_content_toolbar = findViewById(R.id.activity_fragment_content_toolbar);
        activity_fragment_content_toolbar.setTitle("SecondFragment");
        setSupportActionBar(activity_fragment_content_toolbar);
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
                Log.e(TAG, "add fragment");
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
                if (fragment == null) {
                    if (getIntent() != null) {
                        fragment = SecondFragment.getInstant(getIntent().getStringExtra("chapter"));
                    }
                }
                fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
                break;
            case R.id.remove_menu_item:
                Log.e(TAG, "remove fragment");
                FragmentManager fragmentManager2 = getFragmentManager();
                Fragment fragment2 = fragmentManager2.findFragmentById(R.id.fragment_container);
                if (fragment2 != null) {
                    fragmentManager2.beginTransaction().remove(fragment2).commit();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState()");
    }
}

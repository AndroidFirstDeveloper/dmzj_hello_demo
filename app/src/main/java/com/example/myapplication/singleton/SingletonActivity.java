package com.example.myapplication.singleton;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;

public class SingletonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);
        init();
    }

    private void init() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.activity_singleton_container);
        if (fragment == null) {
            fragment = new MyFragment();
        }
        getFragmentManager().beginTransaction().add(R.id.activity_singleton_container, fragment).commit();
    }
}

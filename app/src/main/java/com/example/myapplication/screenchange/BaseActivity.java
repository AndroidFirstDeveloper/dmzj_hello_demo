package com.example.myapplication.screenchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;

public class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base2);
        Log.e(TAG, "onCreate: ");
    }
}

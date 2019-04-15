package com.example.myapplication.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_content);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new FirstFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("FirstActivity","onActivityResult"+"\t\trequestCode="+requestCode+"\t\tresultCode="+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}

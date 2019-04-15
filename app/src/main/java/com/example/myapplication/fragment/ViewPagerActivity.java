package com.example.myapplication.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements OnItemClickedListener {

    private List<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        findViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存数据
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        //恢复数据
    }

    private void findViews() {
        ViewPager viewPager = findViewById(R.id.activity_view_pager_vp);
//        viewPager.setAdapter(new VPAdapter(getSupportFragmentManager()));
        viewPager.setAdapter(new VPAdapter2(getSupportFragmentManager()));
    }

    @Override
    public void onItemClicked(View view, int position, String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public class VPAdapter extends FragmentPagerAdapter {
        private final int ITEM_COUNT = 6;

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new Fragment1();
                    break;
                case 1:
                    fragment = new Fragment2();
                    break;
                case 2:
                    fragment = new Fragment3();
                    break;
                case 3:
                    fragment = new Fragment4();
                    break;
                case 4:
                    fragment = new Fragment5();
                    break;
                case 5:
                    fragment = new Fragment6();
                    break;
                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return ITEM_COUNT;
        }

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }
    }

    public class VPAdapter2 extends FragmentStatePagerAdapter {
        private final int ITEM_COUNT = 6;

        public VPAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new Fragment1();
                    break;
                case 1:
                    fragment = new Fragment2();
                    break;
                case 2:
                    fragment = new Fragment3();
                    break;
                case 3:
                    fragment = new Fragment4();
                    break;
                case 4:
                    fragment = new Fragment5();
                    break;
                case 5:
                    fragment = new Fragment6();
                    break;
                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return ITEM_COUNT;
        }
    }
}

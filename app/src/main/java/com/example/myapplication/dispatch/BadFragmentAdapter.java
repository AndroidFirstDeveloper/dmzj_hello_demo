package com.example.myapplication.dispatch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BadFragmentAdapter extends FragmentPagerAdapter {

    private final int item_count = 4;

    public BadFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BadFragment();
                break;
            case 1:
                fragment = new BadFragment();
                break;
            case 2:
                fragment = new BadFragment();
                break;
            case 3:
                fragment = new BadFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return item_count;
    }
}

package com.example.myapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.Fragment1;
import com.example.myapplication.fragment.Fragment2;
import com.example.myapplication.fragment.Fragment3;
import com.example.myapplication.fragment.OnItemClickedListener;
import com.gyf.barlibrary.ImmersionBar;

public class FragmentLifeCycleActivity2 extends AppCompatActivity implements OnItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life_cycle2);
        findView();
    }

    private void findView() {
        View activity_fragment_life_cycle_toolbar2 = findViewById(R.id.activity_fragment_life_cycle_toolbar2);
        View status_view = activity_fragment_life_cycle_toolbar2.findViewById(R.id.title_bar_layout_status_view);
//        TextView title_bar_layout_operation_tv = activity_fragment_life_cycle_toolbar2.findViewById(R.id.title_bar_layout_operation_tv);
        TextView title_bar_layout_title_tv = activity_fragment_life_cycle_toolbar2.findViewById(R.id.title_bar_layout_title_tv);
//        title_bar_layout_operation_tv.setVisibility(View.VISIBLE);
//        title_bar_layout_operation_tv.setText("跳转");
        title_bar_layout_title_tv.setText("FragmentStatePagerAdapter中Fragment生命周期");

        ViewPager activity_fragment_life_cycle2_vp = findViewById(R.id.activity_fragment_life_cycle2_vp);
        activity_fragment_life_cycle2_vp.setAdapter(new MyAdapter(getSupportFragmentManager()));

        ImmersionBar.with(this).statusBarView(status_view).init();
    }

    @Override
    public void onItemClicked(View view, int position, String content) {

    }

    public void onBack(View view) {
        finish();
    }

    /**总结:(网上摘抄)
     FragmentPagerAdapter和FragmentStatePagerAdapter,首先从大部分概念的英文命名上就能获取很多信息(中文命名不做讨论,很多翻译的质量太差,根本不能作为参考)
     ,前者将fragment对象缓存在FragmentManager当中,一旦将fragment添加到了FragmentTransaction中,这个fragment就可以认为不会被销毁了,
     如果你想在中间添加新的fragment,是不会生效的,这也是导致增删之后发生错位的原因.而后者加了一个state,代表这个adapter只缓存fragment的状态,
     而fragment的对象是被销毁的*/

    /**
     * 结论： FragmentPagerAdapter和FragmentStatePagerAdapter不同点是，FragmentPagerAdapter中Fragment不可见时，只是销毁了Fragment的视图，Fragment对象没有被销毁，销毁过程调用方法  onPause--onStop--onDestroyView；
     * FragmentStatePagerAdapter中Fragment不可见时，销毁了Fragment对象，销毁过程调用方法 onPause--onStop--onDestoryView--onDestroy--onDetache；
     * 二者的相同点是，Fragment可见时，都调用了onCreateView--onActivityCreated--onStart--onResume，但FragmentStatePagerAdapter是重新创建Fragment，所以它的Fragment还会调用 onAttach--onCreate
     */
    private static class MyAdapter extends FragmentStatePagerAdapter {
        final int itemcount = 3;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
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
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return itemcount;
        }
    }


    @Override
    protected void onDestroy() {
        Log.e("FragLifeCycle1", "onDestroy: ");
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

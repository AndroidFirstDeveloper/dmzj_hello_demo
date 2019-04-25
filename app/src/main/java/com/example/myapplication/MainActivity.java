package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.myapplication.activity.BannerActivity;
import com.example.myapplication.activity.BannerActivity2;
import com.example.myapplication.activity.BannerTestActivity;
import com.example.myapplication.activity.ConstraintActivity;
import com.example.myapplication.activity.DefineToastActivity;
import com.example.myapplication.activity.DotActivity;
import com.example.myapplication.activity.FragmentLifeCycleActivity;
import com.example.myapplication.activity.GitUsingActivity;
import com.example.myapplication.activity.GuideActivity;
import com.example.myapplication.activity.LaunchModelActivity;
import com.example.myapplication.activity.ListActivity;
import com.example.myapplication.activity.SaveTestActivity;
import com.example.myapplication.activity.ScrollViewActivity;
import com.example.myapplication.activity.WebActivity;
import com.example.myapplication.browse.BrowseActivity1;
import com.example.myapplication.coordinator.CoordinatorActivity1;
import com.example.myapplication.dispatch.DispatchActivity;
import com.example.myapplication.fragment2.FragmentTestActivity;
import com.example.myapplication.fragment3.ConfigChangeActivity;
import com.example.myapplication.greendaotest.GreenDaoActivity;
import com.example.myapplication.javatest.JavaTestActivity;
import com.example.myapplication.matrixtest.MatrixTestActivity;
import com.example.myapplication.multiclick.MultiClickActivity;
import com.example.myapplication.multistate.FirstActivity;
import com.example.myapplication.multistate.TestActivity;
import com.example.myapplication.recyclerview.RecyclerViewActivity;
import com.example.myapplication.refresh.RefreshLoadActivity;
import com.example.myapplication.screenchange.ScreenActivity;
import com.example.myapplication.zoomtest.EffectImageViewActivity;
import com.example.myapplication.zoomtest.ZoomImageActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    private int number;

    private List<ActivityBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        testBreakPoint();
        testPropertyBPoint();
        initData();
        findViews();
        initPage();
        new JavaTest();
    }

    private void initPage() {
        Intent intent = getIntent();
        if (intent != null) {
            if (TextUtils.equals("1", intent.getStringExtra("page"))) {
                Intent intent2 = new Intent(this, BrowseActivity1.class);
                startActivity(intent2);
            }
        }
    }

    private void initData() {
        ActivityBean bean1 = new ActivityBean();
        bean1.setName("EffectImageActivity");
        bean1.setActivity(EffectImageViewActivity.class);
        list.add(bean1);

        ActivityBean bean2 = new ActivityBean();
        bean2.setName("ZoomImageActivity");
        bean2.setActivity(ZoomImageActivity.class);
        list.add(bean2);

        ActivityBean bean3 = new ActivityBean();
        bean3.setName("MatrixTestActivity");
        bean3.setActivity(MatrixTestActivity.class);
        list.add(bean3);

        ActivityBean bean4 = new ActivityBean();
        bean4.setName("FirstActivity");
        bean4.setActivity(FirstActivity.class);
        list.add(bean4);

        ActivityBean bean5 = new ActivityBean();
        bean5.setName("TestActivity");
        bean5.setActivity(TestActivity.class);
        list.add(bean5);

        ActivityBean bean6 = new ActivityBean();
        bean6.setName("可添加header/footer 的recyclerview");
        bean6.setActivity(RecyclerViewActivity.class);
        list.add(bean6);

        ActivityBean bean10 = new ActivityBean();
        bean10.setName("测试recyclerview的insert、remove、局部刷新");
        bean10.setActivity(com.example.myapplication.recyclerview.RvTestActivity.class);
        list.add(bean10);

        ActivityBean bean7 = new ActivityBean();
        bean7.setName("协调布局CoordinatorLayout");
        bean7.setActivity(CoordinatorActivity1.class);
        list.add(bean7);

        ActivityBean bean8 = new ActivityBean();
        bean8.setName("下拉刷新");
        bean8.setActivity(RefreshLoadActivity.class);
        list.add(bean8);

        ActivityBean bean9 = new ActivityBean();
        bean9.setName("Fragment数据传递");
        bean9.setActivity(com.example.myapplication.fragment.FirstActivity.class);
        list.add(bean9);

        ActivityBean bean11 = new ActivityBean();
        bean11.setName("单例模式展示");
        bean11.setActivity(com.example.myapplication.singleton.SingletonActivity.class);
        list.add(bean11);

        ActivityBean bean12 = new ActivityBean();
        bean12.setName("监听网络状态");
        bean12.setActivity(com.example.myapplication.network.NetworkActivity.class);
        list.add(bean12);

        ActivityBean bean13 = new ActivityBean();
        bean13.setName("Bugly 测试");
        bean13.setActivity(com.example.myapplication.BuglyTestActivity.class);
        list.add(bean13);

        ActivityBean bean14 = new ActivityBean();
        bean14.setName("布局 测试");
        bean14.setActivity(com.example.myapplication.layouts.MergeTestActivity.class);
        list.add(bean14);

        ActivityBean bean15 = new ActivityBean();
        bean15.setName("约束布局(ConstraintLayout)");
        bean15.setActivity(com.example.myapplication.constraints.ConstraintActivity.class);
        list.add(bean15);

        ActivityBean bean16 = new ActivityBean();
        bean16.setName("Java （string、integer、参数传递）");
        bean16.setActivity(JavaTestActivity.class);
        list.add(bean16);

        ActivityBean bean17 = new ActivityBean();
        bean17.setName("事件分发");
        bean17.setActivity(DispatchActivity.class);
        list.add(bean17);

        ActivityBean bean18 = new ActivityBean();
        bean18.setName("activity 基础掌握");
        bean18.setActivity(SaveTestActivity.class);
        list.add(bean18);

        ActivityBean bean19 = new ActivityBean();
        bean19.setName("fragment 生命周期+回退栈");
        bean19.setActivity(FragmentTestActivity.class);
        list.add(bean19);

        ActivityBean bean20 = new ActivityBean();
        bean20.setName("fragment 配置发生变化，防止重新构建");
        bean20.setActivity(ConfigChangeActivity.class);
        list.add(bean20);

        ActivityBean bean21 = new ActivityBean();
        bean21.setName("Banner图展示");
        bean21.setActivity(BannerActivity.class);
        list.add(bean21);

        ActivityBean bean22 = new ActivityBean();
        bean22.setName("Banner图展示2");
        bean22.setActivity(BannerActivity2.class);
        list.add(bean22);

        ActivityBean bean23 = new ActivityBean();
        bean23.setName("打点测试");
        bean23.setActivity(DotActivity.class);
        list.add(bean23);

        ActivityBean bean24 = new ActivityBean();
        bean24.setName("三方唤起程序跳转");
        bean24.setActivity(BrowseActivity1.class);
        list.add(bean24);

        ActivityBean bean25 = new ActivityBean();
        bean25.setName("greenDao 测试");
        bean25.setActivity(GreenDaoActivity.class);
        list.add(bean25);

        ActivityBean bean26 = new ActivityBean();
        bean26.setName("横竖屏切换测试");
        bean26.setActivity(ScreenActivity.class);
        list.add(bean26);

        ActivityBean bean27 = new ActivityBean();
        bean27.setName("防止View重复点击  测试");
        bean27.setActivity(MultiClickActivity.class);
        list.add(bean27);

        ActivityBean bean28 = new ActivityBean();
        bean28.setName("自定义Toast");
        bean28.setActivity(DefineToastActivity.class);
        list.add(bean28);

        ActivityBean bean29 = new ActivityBean();
        bean29.setName("启动模式");
        bean29.setActivity(LaunchModelActivity.class);
        list.add(bean29);

        ActivityBean bean30 = new ActivityBean();
        bean30.setName("约束布局 ratio使用");
        bean30.setActivity(ConstraintActivity.class);
        list.add(bean30);

        ActivityBean bean31 = new ActivityBean();
        bean31.setName("Banner轮播图");
        bean31.setActivity(BannerTestActivity.class);
        list.add(bean31);

        ActivityBean bean32 = new ActivityBean();
        bean32.setName("WebView 测试");
        bean32.setActivity(WebActivity.class);
        list.add(bean32);

        ActivityBean bean33 = new ActivityBean();
        bean33.setName("ScrollView 嵌套测试");
        bean33.setActivity(ScrollViewActivity.class);
        list.add(bean33);
        ActivityBean bean34 = new ActivityBean();
        bean34.setName("git 命令集合");
        bean34.setActivity(GitUsingActivity.class);
        list.add(bean34);
        ActivityBean bean35 = new ActivityBean();
        bean35.setName("集合的相关操作");
        bean35.setActivity(ListActivity.class);
        list.add(bean35);

        ActivityBean bean36 = new ActivityBean();
        bean36.setName("Fragment 生命周期");
        bean36.setActivity(FragmentLifeCycleActivity.class);
        list.add(bean36);

        ActivityBean bean37 = new ActivityBean();
        bean37.setName("事件分发测试");
        bean37.setActivity(com.example.myapplication.activity.DispatchActivity.class);
        list.add(bean37);

        ActivityBean bean38 = new ActivityBean();
        bean38.setName("引导页");
        bean38.setActivity(GuideActivity.class);
        list.add(bean38);
    }

    private void findViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ActivityAdapter adapter = new ActivityAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void testBreakPoint() {
        int i = 0;
        i = i + 10;
        int a = -10;
        int b = 0;
        for (int k = 0; k < i; k++) {
            a = k;
            b = Math.abs(a);
//            Log.e("Main", b + "");
        }
    }

    private void testPropertyBPoint() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                number = 10;
            }
        }).start();
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    public void anotherPage(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void zoomImagePage(View view) {
        Intent intent = new Intent(this, ZoomImageActivity.class);
        startActivity(intent);
    }

    public void matrixTestPage(View view) {
        Intent intent = new Intent(this, MatrixTestActivity.class);
        startActivity(intent);
    }
}

package com.example.myapplication.multistate;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.DensityUtil;
import com.example.myapplication.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView titleTv;
    private Button rightBtn;
    private Button leftBtn;
    private Button activity_test_button1, activity_test_button2;
    private TextView activity_test_display_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_test);

        findViews();
        setTitle(getString(R.string.dmzj));
//        setRightBtn(null, R.drawable.vector_drawable_add, this);
        setRightBtn(getString(R.string.tijiao), 0, this);
        setLeftBtn(null, R.drawable.vector_drawable_add, this);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.activity_test_toolbar);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        titleTv = (TextView) toolbar.findViewById(R.id.activity_test_title);
        rightBtn = (Button) toolbar.findViewById(R.id.activity_test_right_button);
        leftBtn = (Button) toolbar.findViewById(R.id.activity_test_left_button);

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestActivity.this.finish();
            }
        });*/

        activity_test_button1 = findViewById(R.id.activity_test_button1);
        activity_test_button2 = findViewById(R.id.activity_test_button2);

        activity_test_button1.setOnClickListener(this);
        activity_test_button2.setOnClickListener(this);

        activity_test_display_tv=findViewById(R.id.activity_test_display_tv);
        activity_test_display_tv.setText(" 3.android:layout_weight\n" +
                "\n" +
                "android:layout_weight的真实含义是:一旦View设置了该属性(假设有效的情况下)，那么该 View的宽度等于原有宽度(android:layout_width)加上剩余空间的占比！\n" +
                "\n" +
                "设屏幕宽度为L，在两个view的宽度都为match_parent的情况下，原有宽度为L，两个的View的宽度都为L，那么剩余宽度为L-（L+L） = -L, 左边的View占比三分之一，所以总宽度是L+(-L)*1/3 = (2/3)L.事实上默认的View的weight这个值为0，一旦设置了这个值，那么所在view在绘制的时候执行onMeasure两次的原因就在这。\n" +
                "\n" +
                "Google官方推荐，当使用weight属性时，将width设为0dip即可，效果跟设成wrap_content是一样的。这样weight就可以理解为占比了！");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.testmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.test_item0:
                System.out.println("test_item0");
                break;
            case R.id.test_item1:
                System.out.println("test_item1");
                break;
            case R.id.test_item2:
                System.out.println("test_item2");
                break;
            case R.id.test_item3:
                System.out.println("test_item3");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle(String title) {
        if (titleTv != null)
            titleTv.setText(title);
    }

    private void setRightBtn(String content, int resourceId, View.OnClickListener listener) {
        if (rightBtn != null) {
            if (content != null) {
                rightBtn.setText(content);
            }

            if (resourceId != 0) {
                rightBtn.setBackgroundResource(resourceId);
                ViewGroup.LayoutParams linearParams = rightBtn.getLayoutParams();
                linearParams.height = DensityUtil.dp2px(this, 26);
                linearParams.width = DensityUtil.dp2px(this, 26);
                rightBtn.setLayoutParams(linearParams);
            }
            if (listener != null) {
                rightBtn.setOnClickListener(listener);
            }
        }
    }


    private void setLeftBtn(String content, int resourceId, View.OnClickListener listener) {
        if (leftBtn != null) {
            if (content != null) {
                leftBtn.setText(content);
            }
            if (getResources().getDrawable(resourceId) != null) {
                leftBtn.setBackgroundResource(resourceId);
                ViewGroup.LayoutParams linearParams = leftBtn.getLayoutParams();
                linearParams.height = DensityUtil.dp2px(this, 26);
                linearParams.width = DensityUtil.dp2px(this, 26);
                leftBtn.setLayoutParams(linearParams);
            }
            if (listener != null) {
                leftBtn.setOnClickListener(listener);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_test_right_button:
                Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_test_left_button:
                Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.activity_test_button1:
                Toast.makeText(this, "width=" + activity_test_button1.getMeasuredWidth(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_test_button2:
                Toast.makeText(this, "width=" + activity_test_button2.getMeasuredWidth(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

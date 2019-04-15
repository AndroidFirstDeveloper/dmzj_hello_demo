package com.example.myapplication.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.just.agentweb.AgentWeb;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends AppCompatActivity {

    private final String TAG = "ScrollViewActivity";
    private String[] urls = new String[]{
            "https://rem.myfcomic.com/projectDetails/88?platform=app",
            "https://m.myfcomic.com/projectDetails/88?platform=app",
            "https://m.myfcomic.com/projectDetails/87",
            "http://m.myfcomic.com/projectDetails/85",
            "https://cdnmyfcomicadmin.myfcomic.com/activity/activity_1/index.html",
            "http://www.jd.com"
    };

    private LinearLayout activity_scroll_view_ll;
    private NestedScrollView activity_scroll_view_sv;

    private LinearLayout activity_scroll_view_wv;

    private final int TOUCH_SLOP = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        activity_scroll_view_ll = findViewById(R.id.activity_scroll_view_ll);
        activity_scroll_view_sv = findViewById(R.id.activity_scroll_view_sv);
        activity_scroll_view_wv = findViewById(R.id.activity_scroll_view_wv);

//        activity_scroll_view_sv.setNestedScrollingEnabled(false);
        initWebView();
        initRecyclerView();
    }


    private void initWebView() {
        init();
        initScrollTouchListener();
    }

    private AgentWeb mAgentWeb;

    private void init() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(activity_scroll_view_wv, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(urls[0]);
    }

    /**
     * 解决scrollview嵌套webview中HTML中banner横向滑动冲突的bug
     */
    private void initScrollTouchListener() {
        mAgentWeb.getWebCreator().getWebView().setOnTouchListener(new View.OnTouchListener() {
            private float startx;
            private float starty;
            private float offsetx;
            private float offsety;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        startx = event.getX();
                        starty = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetx = Math.abs(event.getX() - startx);
                        offsety = Math.abs(event.getY() - starty);
                        /*if (offsetx > offsety) {
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }*/
                        if (offsetx >= TOUCH_SLOP || offsety >= TOUCH_SLOP) {
                            float ratio = offsety / offsetx;
                            double angle = Math.atan(ratio);
                            Log.e(TAG, "onTouch: angle=" + (angle * 180 / Math.PI));
                            if (angle > Math.PI / 6) {
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                            } else {
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                            }
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
                            startx = event.getX();
                            starty = event.getY();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.evaluateJavascript("getScrollHeight()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e(TAG, "onReceiveValue: 初始高度：" + value);
//                        Toast.makeText(SubjectDetailActivity.this, "返回值"+value, Toast.LENGTH_SHORT).show();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    Display display = getWindowManager().getDefaultDisplay();
                    display.getMetrics(displayMetrics);
                    Log.e(TAG, "onReceiveValue: density=" + displayMetrics.density + "\t\tdensityDpi=" + displayMetrics.densityDpi);

                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mAgentWeb.getWebCreator().getWebView().getLayoutParams();
                    try {
                        layoutParams.height = (int) (Integer.valueOf(value) * displayMetrics.density);
                    } catch (NumberFormatException e) {
                        layoutParams.height = 0;
                    }
                    mAgentWeb.getWebCreator().getWebView().setLayoutParams(layoutParams);
                }
            });
        }
    };


    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };


    private void initRecyclerView() {
        List<ScrollModel> list=new ArrayList<>();
        for(int i=0;i<1000;i++){
            ScrollModel scrollModel=new ScrollModel();
            scrollModel.setPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554991047467&di=b6199251ab7ff566bec7e3f6456d2b91&imgtype=0&src=http%3A%2F%2Fphotos.tuchong.com%2F294639%2Ff%2F28649737.jpg");
            scrollModel.setText("item"+i+"或者windows 自带的画图。用旋择工具，拉一个矩形。比如照片的原尺寸是1600x1200，准备把他裁剪成宽500，那再拉矩形的时候，同时看下面状态栏上的数字，当变成500x1000的时候释放鼠标，然后再点击“剪裁”命令。这样剪裁出来的照片是原来照片的一部分，但是不变性。");
            list.add(scrollModel);
        }
        RecyclerView activity_scroll_view_rv = findViewById(R.id.activity_scroll_view_rv);
        activity_scroll_view_rv.setNestedScrollingEnabled(false);
        activity_scroll_view_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activity_scroll_view_rv.setAdapter(new ScrollAdapter(list,this));
    }

    private static class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ScrollHolder> {

        private List<ScrollModel> list;
        private Context context;

        public ScrollAdapter(List<ScrollModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public ScrollHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ScrollHolder(LayoutInflater.from(context).inflate(R.layout.scroll_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ScrollHolder holder, int position) {
            Glide.with(context)
                    .load(list.get(position).getPath())
                    .into(holder.scroll_item_layout_iv);
            holder.scroll_item_layout_tv.setText(list.get(position).getText());
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public static class ScrollHolder extends RecyclerView.ViewHolder {
            private ImageView scroll_item_layout_iv;
            private TextView scroll_item_layout_tv;

            public ScrollHolder(View itemView) {
                super(itemView);
                scroll_item_layout_tv = itemView.findViewById(R.id.scroll_item_layout_tv);
                scroll_item_layout_iv = itemView.findViewById(R.id.scroll_item_layout_iv);

            }
        }
    }


    private static class ScrollModel {
        private String path;
        private String text;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}

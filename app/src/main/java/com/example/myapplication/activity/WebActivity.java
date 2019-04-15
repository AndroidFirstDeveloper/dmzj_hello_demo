package com.example.myapplication.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.PermissionInterceptor;

public class WebActivity extends AppCompatActivity {
    private String[] urls=new String []{
            "https://m.myfcomic.com/projectDetails/88?platform=app",
            "https://m.myfcomic.com/projectDetails/87",
            "http://m.myfcomic.com/projectDetails/85",
            "https://cdnmyfcomicadmin.myfcomic.com/activity/activity_1/index.html",
            "http://www.jd.com"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        findView();
        init();
    }

    private LinearLayout web_activity_web_parent;

    private void findView() {
        View status_view = findViewById(R.id.web_activity_status_view);
        ImmersionBar.with(this).statusBarView(status_view).init();
        web_activity_web_parent = findViewById(R.id.web_activity_web_parent);
    }

    private AgentWeb mAgentWeb;

    private void init() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web_activity_web_parent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(urls[0]);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i("PermissionInt", "url:" + url + "  permission:" + permissions + " action:" + action);
            return false;
        }
    };

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

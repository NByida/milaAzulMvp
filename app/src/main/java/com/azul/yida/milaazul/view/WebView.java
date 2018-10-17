package com.azul.yida.milaazul.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.event.RefreshEvent;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.weidget.SmartToolbar;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class WebView extends MvpView {
    public android.webkit.WebView getWebView() {
        return webView;
    }

    @BindView(R.id.web_view)
    android.webkit.WebView webView;
    @BindView(R.id.tv_custom_title)
    TextView tvCustomTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvTitle;
    float y;
    int lastX,lastY,detY;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }


    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
       toolbar.post(()-> toolbar.setNavigationOnClickListener(v->getActivity().onBackPressed()));
        init();
        swipeRefreshLayout.setOnRefreshListener(() ->
                EventBus.getDefault().post(new RefreshEvent(this, swipeRefreshLayout)));
    }



    public void setContent(String title, String url) {
        y=0;
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
//                if(url == null) return false;
//                try {
//                    if(url.startsWith("weixin://") || url.startsWith("alipays://") ||
//                            url.startsWith("mailto://") || url.startsWith("tel://")
//                        //其他自定义的scheme
//                            ) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        getActivity().startActivity(intent);
//                        return true;
//                    }
//                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
//                    return false;
//                }
//                //处理http和https开头的url
//                webView.loadUrl(url);
//                return true;
//            }
        });

        new Handler().post(() -> {
            toolbar.setTitle(title);
            tvCustomTitle.setSingleLine(true);

        });
    }

    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }




    @Override
    public void unRegist() {
        webView.destroy();
        super.unRegist();
    }

    private void init(){
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
//支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }
}

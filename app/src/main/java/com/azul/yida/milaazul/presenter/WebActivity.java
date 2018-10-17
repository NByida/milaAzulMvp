package com.azul.yida.milaazul.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.event.RefreshEvent;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.WebView;
import com.azul.yida.milaazul.view.base.MvpView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WebActivity extends BasePresentActivity<WebView> {
    private String title;
    private String url;

    @Override
    public Class<WebView> getPresentClass() {
        return WebView.class;
    }

    @Override
    public void onNetWorkErorRetry() {
        mvpView.setContent(title, url);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (null == bundle) {
            bundle = savedInstanceState;
        }

        if (null != bundle) {
            title = bundle.getString("title");
            url = bundle.getString("url");
            Mlog.t(url+"");
        }

        if (null != title || null != url) {
            mvpView.setContent(title, url);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
        outState.putString("url", url);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(RefreshEvent event) {
        if(event.getMvpView() == mvpView){
            mvpView.setContent(title, url);
        }
    }

    @Override
    public void onBackPressed() {
        if(mvpView.getWebView().canGoBack()){
            mvpView.getWebView().goBack();
        }else {
            super.onBackPressed();
        }
    }

    public static void startWebActivity(Context context,String title,String Url){
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",Url);

        context.startActivity(intent);
    }


}

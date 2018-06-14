package com.azul.yida.milaazul.view;

import android.widget.Button;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.view.myView.CircleProgressBar;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivityView extends MvpView {

    @OnClick(R.id.bt_show)
    public void showDialog(){
        showLoading();
        //setProgress();
        Mlog.t("showDialog");
    }

    @OnClick(R.id.bt_dissmiss)
    public void dissMiss(){
       dissmissLoading();
    }
    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }



}

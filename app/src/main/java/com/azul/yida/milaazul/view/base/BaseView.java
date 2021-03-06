package com.azul.yida.milaazul.view.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.azul.yida.milaazul.presenter.base.BasePresentActivity;

public interface BaseView {
    public void regist(LayoutInflater inflater);
    public void unRegist();
    public void showLoading();
    public void dissmissLoading();
    public void showErrorMessage(String msg);
    public void showLongMessage(String msg);
    public <T extends Activity> T getActivity();

    }

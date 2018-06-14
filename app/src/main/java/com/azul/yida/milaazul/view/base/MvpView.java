package com.azul.yida.milaazul.view.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.azul.yida.milaazul.presenter.base.BaseLoadingFragment;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MvpView implements BaseView {
//    private BasePresentActivity activity;
    private View rootView;
    private BaseLoadingFragment loadingFragment;
    private Unbinder unbinder;



    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        rootView=inflater.inflate(getLayoutId(),null);
        unbinder= ButterKnife.bind(this,rootView);
        }


    public View getRootView() {
        return rootView;
    }

    @Override
    public void unRegist() {
        if(unbinder==null)return;
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        this.loadingFragment=new BaseLoadingFragment();
        loadingFragment.show(getActivity().getSupportFragmentManager(),"");
    }

    @Override
    public void dissmissLoading() {
        if(loadingFragment==null)return;
        loadingFragment.dismiss();
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showLongMessage() {

    }

    @Override
    public <T extends AppCompatActivity> T getActivity() {
        return null != rootView ? (T) rootView.getContext() : null;
    }

    public abstract int getLayoutId();
}

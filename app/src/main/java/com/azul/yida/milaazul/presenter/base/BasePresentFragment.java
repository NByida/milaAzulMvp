package com.azul.yida.milaazul.presenter.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azul.yida.milaazul.presenter.LifeCycleBasePresenter.BaseFragment;
import com.azul.yida.milaazul.view.base.MvpView;
import com.trello.rxlifecycle2.components.support.RxFragment;

public abstract class BasePresentFragment<T extends MvpView> extends BaseFragment{
    private MvpView mvpView;

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       initView();
       mvpView.regist(inflater);
       return mvpView.getRootView();
       }

    public abstract Class<T> getPresentClass();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mvpView.unRegist();
    }
}

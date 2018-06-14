package com.azul.yida.milaazul.presenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.azul.yida.milaazul.view.base.MvpView;

public abstract class BasePresentActivity<T extends MvpView> extends AppCompatActivity {
    public MvpView mvpView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        }

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mvpView.regist(LayoutInflater.from(this));
        if(mvpView.getRootView()!=null){
            setContentView(mvpView.getRootView());
        }

    }

    public abstract Class<T> getPresentClass();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpView.unRegist();
    }
}

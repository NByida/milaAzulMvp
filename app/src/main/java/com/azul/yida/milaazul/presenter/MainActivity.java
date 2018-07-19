package com.azul.yida.milaazul.presenter;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.model.Azul;
import com.azul.yida.milaazul.net.GankService;
import com.azul.yida.milaazul.net.RetrofitInstance;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.MainActivityView;
import com.azul.yida.milaazul.view.base.MvpView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BasePresentActivity<MainActivityView> {

    private RecyclerView recyclerView;
    GankService service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service= RetrofitInstance.getInstance().getGankService();
        recyclerView=(RecyclerView) findViewById(R.id.recycle_view);
        pullData();
    }




    private void pullData(){
        service.getData(GankService.福利,10,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(m->Observable.fromIterable(m.getResults()))
                .subscribe(gank->{
                    gank.getUrl();
                    Mlog.t("url"+gank.getUrl());
                },consumer::accept);
    }

    @Override
    public Class<MainActivityView> getPresentClass() {
        return MainActivityView.class;
    }
}

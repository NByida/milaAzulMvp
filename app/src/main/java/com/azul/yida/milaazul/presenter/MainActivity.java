package com.azul.yida.milaazul.presenter;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.model.Azul;
import com.azul.yida.milaazul.net.GankService;
import com.azul.yida.milaazul.net.RetrofitInstance;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.MainActivityView;
import com.azul.yida.milaazul.view.viewHelper.BGArefrashViewHolder;


import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BasePresentActivity<MainActivityView> implements BGARefreshLayout.BGARefreshLayoutDelegate  {

    private BGARefreshLayout mRefreshLayout;
    private int page=1;
    GankService service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefreshLayout=rootView.findViewById(R.id.rl_modulename_refresh);
        initRefreshLayout(mRefreshLayout);
        service= RetrofitInstance.getInstance().getGankService(this);
        pullData(1);
    }




    private void pullData(int p){
        mvpView.showLoading();
        service.getData(GankService.福利,10,p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model->{
                    mRefreshLayout.postDelayed(()->mvpView.dissmissLoading(),1000);
                    if(p==1) mRefreshLayout.postDelayed(()->mRefreshLayout.endRefreshing(),1000);
                    else  mRefreshLayout.postDelayed(()->mRefreshLayout.endLoadingMore(),1000);
                   mvpView.setData(model.getResults(),p);
                },e->{consumer.accept(e);

                    if(p==1) mRefreshLayout.postDelayed(()->mRefreshLayout.endRefreshing(),1000);
                    else  mRefreshLayout.postDelayed(()->mRefreshLayout.endLoadingMore(),1000);
                });
    }

    @Override
    public Class<MainActivityView> getPresentClass() {
        return MainActivityView.class;
    }

    @SuppressLint("ResourceType")
    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        // 为BGARefreshLayout 设置代理
        BGArefrashViewHolder refreshViewHolder = new BGArefrashViewHolder(this, true) {
        };
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setIsShowLoadingMoreView(true);
        refreshViewHolder.setLoadingMoreText("疯狂加载中...");
        refreshViewHolder.setStickinessColor(R.color.colorAccent);
        refreshViewHolder.setRotateImage(R.mipmap.bga_refresh_loading12);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page=1;
        pullData(1);
       // mvpView.showToast("刷新成功");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pullData(++page);
       // mvpView.showToast("10张小姐姐已经添加");
        return true;
    }


}

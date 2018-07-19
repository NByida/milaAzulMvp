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
import android.view.View;

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
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
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
        mRefreshLayout=mvpView.getRootView().findViewById(R.id.rl_modulename_refresh);
        initRefreshLayout(mRefreshLayout);
        service= RetrofitInstance.getInstance().getGankService();
        pullData(1);
    }




    private void pullData(int p){
        service.getData(GankService.福利,10,p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model->{
                   mvpView.setData(model.getResults(),p);
                    //Mlog.t("url"+gank.getUrl());
                },consumer::accept);
    }

    @Override
    public Class<MainActivityView> getPresentClass() {
        return MainActivityView.class;
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        // 为BGARefreshLayout 设置代理
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true) {
        };
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setIsShowLoadingMoreView(true);
        refreshViewHolder.setLoadingMoreText("疯狂加载中...");
        //refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.yellow);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page=1;
        pullData(1);
        mRefreshLayout.endRefreshing();
        mvpView.showToast("刷新成功");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pullData(++page);
        mvpView.showToast("10张小姐姐已经" +
                "添加");
        return false;
    }
}

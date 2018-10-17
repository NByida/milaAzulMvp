package com.azul.yida.milaazul.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.presenter.DailyActivity;
import com.azul.yida.milaazul.presenter.MilaActivity;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.weidget.SmartToolbar;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


public class MainActivityView extends MvpView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;
    private BaseQuickAdapter<Gank, myViewHolder> baseQuickAdapter;



    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        initRv();
        toolbar.setTitle("Gank");
        toolbar.setNavigationIcon(R.mipmap.pic_round);
        toolbar.post(()->{ toolbar.setNavigationOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),MilaActivity.class);
            getActivity().startActivity(intent);
        });});

    }


    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        baseQuickAdapter = new BaseQuickAdapter<Gank, myViewHolder>(R.layout.item) {
            @Override
            protected void convert(myViewHolder helper, Gank item) {
                helper.setIamgeUrl(getActivity(), R.id.im_beauty, item.getUrl());
                helper.setText(R.id.tv_time, item.getCreatedAt());
                helper.addOnClickListener(R.id.im_beauty);
            }
        };
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemChildClickListener((a, v, p) -> {
            Intent intent=new Intent(getActivity(),DailyActivity.class);
            intent.putExtra("gank",(Gank) a.getData().get(p));
            getActivity().startActivity(intent);
        });
    }

    public void setData(List<Gank> arrayList, int page) {
        if (page == 1) {
            baseQuickAdapter.replaceData(arrayList);
        } else {
            baseQuickAdapter.addData(arrayList);
        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }
}

package com.azul.yida.milaazul.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


public class MainActivityView extends MvpView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;

    private BaseQuickAdapter<Gank, myViewHolder> baseQuickAdapter;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)


    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        initRv();
        initToolbar(toolbar,"Gank MEIZI :-)");
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
                helper.setText(R.id.tv_title, item.getPicName());
                helper.addOnClickListener(R.id.im_beauty);
                // helper.setImageResource(R.id.im_beauty, Resources.getSystem().getIdentifier("ProgressBar_progressDrawable", "drawable", "android"));
            }
        };
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemChildClickListener((a, v, p) -> {
            String string = ((Gank) a.getData().get(p)).getWho();
            showToast(string);
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


}

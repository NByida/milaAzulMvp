package com.azul.yida.milaazul.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.view.adapter.myViewHolder;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.view.myView.CircleProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example2.lrudemo.toast.LoadingLayout;
import com.example2.lrudemo.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivityView extends MvpView {

    @BindView(R.id.lay_content)
    LoadingLayout layContent;
    @BindView(R.id.circle)
    CircleProgressBar circle;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.bt_show)
    Button btShow;
    @BindView(R.id.bt_dissmiss)
    Button btDissmiss;
    @BindView(R.id.circle1)
    CircleProgressBar circle1;
    private BaseQuickAdapter<Gank,myViewHolder> baseQuickAdapter;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.bt_show)
    public void showDialog() {
        layContent.startLoading();
        showLoading();
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        initRv();

    }

    @OnClick(R.id.bt_dissmiss)
    public void dissMiss() {
        layContent.stoptLoading();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    private void  initRv(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        baseQuickAdapter=new BaseQuickAdapter<Gank, myViewHolder>(R.layout.item) {
            @Override
            protected void convert(myViewHolder helper, Gank item) {
                helper.setIamgeUrl(getActivity(),R.id.im_beauty,item.getUrl());
                helper.setText(R.id.tv_time,item.getCreatedAt());
                helper.setText(R.id.tv_title,item.getPicName());
                helper.addOnClickListener(R.id.im_beauty);
            }
        };
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemChildClickListener((a,v,p)->{
            String string=((Gank)a.getData().get(p)).getWho();
            showToast(string);
        });
    }

    public void setData(List<Gank> arrayList,int page){
        if(page==1){
            baseQuickAdapter.replaceData(arrayList);
        }else {
            baseQuickAdapter.addData(arrayList);
        }
        baseQuickAdapter.notifyDataSetChanged();}





}

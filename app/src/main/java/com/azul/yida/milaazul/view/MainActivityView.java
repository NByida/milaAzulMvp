package com.azul.yida.milaazul.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.view.base.MvpView;
import com.azul.yida.milaazul.view.myView.CircleProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example2.lrudemo.toast.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivityView extends MvpView {

    @BindView(R.id.lay_content)
    LoadingLayout layContent;
    @BindView(R.id.circle)
    CircleProgressBar circle;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.bt_show)
    Button btShow;
    @BindView(R.id.bt_dissmiss)
    Button btDissmiss;
    @BindView(R.id.circle1)
    CircleProgressBar circle1;
    private BaseQuickAdapter<Gank,BaseViewHolder> baseQuickAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.bt_show)
    public void showDialog() {
        layContent.startLoading();
        showLoading();
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);

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
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        baseQuickAdapter=new BaseQuickAdapter<Gank, BaseViewHolder>(R.layout.item) {
            @Override
            protected void convert(BaseViewHolder helper, Gank item) {
                helper.
            }
        }

    }


}

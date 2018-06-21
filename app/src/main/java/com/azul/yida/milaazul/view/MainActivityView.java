package com.azul.yida.milaazul.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.view.base.MvpView;

import butterknife.BindView;
import butterknife.OnClick;
import com.azul.yida.milaazul.weidget.BaseLoadingLayout;

public class MainActivityView extends MvpView {

    @BindView(R.id.lay_content)
    BaseLoadingLayout layContent;

    @OnClick(R.id.bt_show)
    public void showDialog(){
        //showLoading();
        //setProgress();
        layContent.startLoading();
        //new BaseLoadingFragment().show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),"");
        Mlog.t("showDialog");
    }

    @OnClick(R.id.bt_dissmiss)
    public void dissMiss(){
        layContent.stoptLoading();
    }
    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    public void startLoading(){
        for(int a=0;a<layContent.getChildCount();a++){
            layContent.getChildAt(a).setVisibility(View.GONE);
        }
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View loadView=layoutInflater.inflate(R.layout.loading_dialog,null);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
       p.addRule(RelativeLayout.CENTER_IN_PARENT);
        layContent.addView(loadView,p);
    }

    public void stoptLoading(){
//        View loadView=layContent.findViewById(R.id.progress_bar);
//        if(loadView!=null){
            layContent.removeView(layContent.getChildAt(layContent.getChildCount()-1));
//        }
        for(int a=0;a<layContent.getChildCount();a++){
            layContent.getChildAt(a).setVisibility(View.VISIBLE);
        }
    }

}

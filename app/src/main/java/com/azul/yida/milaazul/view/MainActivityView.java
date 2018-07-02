package com.azul.yida.milaazul.view;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.presenter.base.BaseLoadingFragment;
import com.azul.yida.milaazul.view.base.MvpView;

import butterknife.BindView;
import butterknife.OnClick;

import com.azul.yida.milaazul.view.myView.CircleProgressBar;


import com.example2.lrudemo.toast.LoadingLayout;
import com.example2.lrudemo.toast.SnackBarUtils;
import com.example2.lrudemo.toast.ToastUtil;


public class MainActivityView extends MvpView {

    @BindView(R.id.lay_content)
    LoadingLayout layContent;
//
    @BindView(R.id.circle)
    CircleProgressBar circle;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.bt_show)
    public void showDialog(){
        //showLoading();
        //setProgress();
        layContent.startLoading();
      //  new BaseLoadingFragment().show(getActivity().getSupportFragmentManager(),"");
        showLoading();
        ToastUtil toastUtil=new ToastUtil();
       Toast toast= toastUtil.Indefinite(getRootView().getContext(),"111112345", Snackbar.LENGTH_INDEFINITE).setToastBackground(Color.WHITE,R.drawable.bg_round_white).getToast();

       View view=toast.getView();
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
       toast.setGravity(Gravity.RIGHT|Gravity.BOTTOM, 200, 200);
       layoutParams.gravity=Gravity.RIGHT|Gravity.BOTTOM;

       //layoutParams.x=-100;
      // layoutParams.y=-200;
       //view.setLayoutParams(layoutParams);
       toast.show();
       SnackBarUtils.IndefiniteSnackbar(getRootView(),"aa",R.color.colorAccent, R.color.colorPrimaryDark,R.mipmap.ic_launcher).show();
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        super.regist(inflater);
        circle.setProgress(30);
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

package com.azul.yida.milaazul.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.presenter.MainActivity;
import com.azul.yida.milaazul.presenter.base.BaseLoadingFragment;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.example2.lrudemo.toast.ToastUtil;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public abstract class MvpView implements BaseView {
//    private BasePresentActivity activity;
    protected View rootView;
    private BaseLoadingFragment loadingFragment;
    private Unbinder unbinder;




    @Override
    public void regist(@NonNull LayoutInflater inflater) {
        rootView=inflater.inflate(getLayoutId(),null);
        unbinder= ButterKnife.bind(this,rootView);
        }


    public View getRootView() {
        return rootView;
    }

    @Override
    public void unRegist() {
        if(unbinder==null)return;
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        this.loadingFragment=new BaseLoadingFragment();
        loadingFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),"");
    }

    @Override
    public void dissmissLoading() {
        if(loadingFragment==null)return;
        loadingFragment.dismiss();
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showLongMessage() {

    }

    protected   void  go2Activity(Class<?extends BasePresentActivity>  activity) {
        Intent intent=new Intent(getActivity(), activity);
        getActivity().startActivity(intent);

    }

    @Override
    public <T extends Activity> T getActivity() {
        return null != rootView ? (T) rootView.getContext() : null;
    }

    public void showToast(String s){
        new ToastUtil().Short(getActivity(),s).setToastBackground(getActivity().getResources().getColor(R.color.white),R.drawable.bg_round_white).show();
    }

    protected void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    public abstract int getLayoutId();

    public class errorConsumer implements Consumer<Throwable> {

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            Log.e("test",e.getClass().getName());
           dissmissLoading();
            if (e instanceof SSLHandshakeException) {
                showToast("请检关闭");
            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
                showToast("请检查网络");
            } else {
                //先注释掉，服务端总是报500，好烦。
                showToast("光纤被挖断");
                Log.e("test",""+getClass().getName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();
}

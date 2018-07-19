package com.azul.yida.milaazul.presenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.azul.yida.milaazul.view.base.MvpView;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.functions.Consumer;

public abstract class BasePresentActivity<T extends MvpView> extends AppCompatActivity {
    public T mvpView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        }

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mvpView.regist(LayoutInflater.from(this));
        if(mvpView.getRootView()!=null){
            setContentView(mvpView.getRootView());
        }

    }

    public abstract Class<T> getPresentClass();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpView.unRegist();
    }

    public class errorConsumer implements Consumer<Throwable>{

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            Log.e("test","exception");
            mvpView.dissmissLoading();
            if (e instanceof SSLHandshakeException) {

            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
                //mView.showErrorMsg(getString(R.string.check_your_network));
            } else {
                //先注释掉，服务端总是报500，好烦。
                //mView.showErrorMsg(getString(R.string.server_error));
                Log.e("test",""+getLocalClassName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();
}

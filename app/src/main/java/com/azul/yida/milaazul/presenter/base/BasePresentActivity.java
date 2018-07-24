package com.azul.yida.milaazul.presenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.azul.yida.milaazul.event.loadNetworkEvent;
import com.azul.yida.milaazul.presenter.LifeCycleBasePresenter.BaseActivity;
import com.azul.yida.milaazul.view.base.MvpView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.functions.Consumer;

public abstract class BasePresentActivity<T extends MvpView> extends BaseActivity {
    public T mvpView;
    protected View rootView;


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
            if (mvpView.getToolbar() instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) mvpView.getToolbar();
                setSupportActionBar(toolbar);
            }
        }
        rootView=mvpView.getRootView();

    }

    public abstract Class<T> getPresentClass();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpView.unRegist();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() != 0) getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    protected int getMenuRes() {
        return 0;
    }

    public class errorConsumer implements Consumer<Throwable>{

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            mvpView.showSnackbar();
            Log.e("test",e.getClass().getName());
            mvpView.dissmissLoading();
            if (e instanceof SSLHandshakeException) {
                //mvpView.showToast("请关闭");
            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
               // mvpView.showToast("请检查网络设置");
            } else {
                //先注释掉，服务端总是报500，好烦。
                //mvpView.showToast("请检查网络设置");
                Log.e("test",""+getLocalClassName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadNetworkEvent(loadNetworkEvent loadNetworkEvent){
        onNetWorkErorRetry();
    }

    public  abstract void  onNetWorkErorRetry();
}

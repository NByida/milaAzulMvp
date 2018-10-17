package com.azul.yida.milaazul.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.azul.yida.milaazul.R;
import com.azul.yida.milaazul.common.Mlog;
import com.azul.yida.milaazul.net.GankService;
import com.azul.yida.milaazul.net.MilaServices;
import com.azul.yida.milaazul.net.RetrofitInstance;
import com.azul.yida.milaazul.presenter.base.BaseloadingRvActivity;
import com.azul.yida.milaazul.view.MainActivityView;
import com.azul.yida.milaazul.view.milaView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MilaActivity extends BaseloadingRvActivity<milaView> {
    MilaServices milaServices;
    ArrayList<String> list;
    boolean isMila;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        milaServices= RetrofitInstance.getInstance().getMilaServices(this);
        super.onCreate(savedInstanceState);
        list=new ArrayList<String>();
        TextView tvTiyle=findViewById(R.id.tv_custom_title);
        tvTiyle.setOnClickListener((v)->{
            list=new ArrayList<String>();
            if(isMila){
                isMila=false;
                tvTiyle.setText("可爱");
            }else {
                isMila=true;
                tvTiyle.setText("米娜");
            }
            pullData(1);
        });
    }

    @Override
    protected void pullData(int p) {
        if(isMila){
            pullMilaData(p);
            return;
        }
         mvpView.showLoading();
         milaServices.getCuteDate("https://cutiejpn.tumblr.com/page/"+p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model->{
                    mvpView.dissmissLoading();
                    if(p==1) {mRefreshLayout.endRefreshing();}
                    else { mRefreshLayout.endLoadingMore();}
                    splitData(model.toString());
                     mvpView.setData(list,p);
                },e->{consumer.accept(e);
                    if(p==1)mRefreshLayout.endRefreshing();
                    else  mRefreshLayout.endLoadingMore();
                });

    }


    protected void pullMilaData(int p) {
        mvpView.showLoading();
        milaServices.getMilaDate(p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model->{
                    mvpView.dissmissLoading();
                    if(p==1) {mRefreshLayout.endRefreshing();}
                    else { mRefreshLayout.endLoadingMore();}
                    splitData(model.toString());
                    mvpView.setData(list,p);
                },e->{consumer.accept(e);
                    if(p==1)mRefreshLayout.endRefreshing();
                    else  mRefreshLayout.endLoadingMore();
                });

    }

    @Override
    public Class<milaView> getPresentClass() {
        return milaView.class;
    }

    public void splitData(String html){
        //  <img src="https://78.media.tumblr.com/7e257f0f47947ae1d736a0a55d72fd58/tumblr_p8na4tZzwQ1ucgluao1_1280.jpg" alt="" width="1280" height="853"></a>
        String pattern="(<img src=\")(https://.*?.media.tumblr.com/.*?.jpg)";
        Pattern split=Pattern.compile(pattern);
        Matcher m=split.matcher(html);
        int i=0;
        while (m.find()){
            i++;
            String url=m.group(2);
            Mlog.i("test"+""+i+":",url);
            list.add(url);
        }
        if(list.size()==0){
            mvpView.showErrorMessage("网页正则结果为0");
        }
    }
}

package com.azul.yida.milaazul.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.net.Entity.PostGankModel;
import com.azul.yida.milaazul.net.GankService;
import com.azul.yida.milaazul.net.RetrofitInstance;
import com.azul.yida.milaazul.presenter.base.BasePresentActivity;
import com.azul.yida.milaazul.view.DailyView;

public class DailyActivity extends BasePresentActivity<DailyView> {
    Gank gank;
    String year,month,day;
    private GankService gankService;
    @Override
    public Class<DailyView> getPresentClass() {
        return DailyView.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gankService= RetrofitInstance.getInstance().getGankService(this);
        gank=getIntent().getParcelableExtra("gank");
        String image=gank.getUrl();
        String desc=gank.getDesc();
        year=desc.substring(0,4);
        month=desc.substring(5,7);
        day=desc.substring(8,10);
        mvpView.setImage(image);
        getSomeDayDetail(year,month,day);
    }

    private void getSomeDayDetail(String year,String month,String day){
        mvpView.showLoading();
        gankService.getDataOnSomeday(year,month,day)
                .compose(applyIOSchedulersAndLifecycle())
                .subscribe(
                        model->{
                            mvpView.dissmissLoading();
                          if(model.isError())  mvpView.showToast("位置错误");
                          else{

                          }},consumer::accept);
    }



}

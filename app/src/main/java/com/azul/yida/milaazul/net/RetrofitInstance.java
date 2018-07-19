package com.azul.yida.milaazul.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuyimin on 2018/7/19.
 * E-mail codingyida@qq.com
 */

public class RetrofitInstance {
    private volatile Retrofit retrofit;
    private  GankService gankService;
    private RetrofitInstance(){

    }

    private static class holder{
        static final RetrofitInstance retrofitInstance=new RetrofitInstance();
    }

    public static RetrofitInstance getInstance(){
        return holder.retrofitInstance;
    }

    private Retrofit getRetrofit(){
        if (retrofit==null){
            synchronized (this) {
                if (retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .baseUrl(GankService.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public GankService getGankService(){
        if(gankService==null){
            gankService=getRetrofit().create(GankService.class);
        }
        return gankService;
    }


}

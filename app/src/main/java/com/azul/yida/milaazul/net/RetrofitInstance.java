package com.azul.yida.milaazul.net;

import android.app.Application;
import android.content.Context;

import com.azul.yida.milaazul.AzulApplication.AzulApp;
import com.azul.yida.milaazul.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuyimin on 2018/7/19.
 * E-mail codingyida@qq.com
 */

public class RetrofitInstance {
    private volatile Retrofit retrofit;
    private volatile Retrofit milaRetrofit;
    private  GankService gankService;
    private MilaServices milaServices;

    File cacheFile = new File(AzulApp.getInstance().getCacheDir().getAbsolutePath(), "HttpCache");
    Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB

    private RetrofitInstance(){

    }

    private static class holder{
        static final RetrofitInstance retrofitInstance=new RetrofitInstance();
    }

    public static RetrofitInstance getInstance(){
        return holder.retrofitInstance;
    }

    private Retrofit getRetrofit(Context context){
        if (retrofit==null){
            synchronized (this) {
                if (retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .client(getClient(context))
                            .baseUrl(GankService.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private Retrofit getMilaRetrofit(Context context){
        if (milaRetrofit==null){
            synchronized (this) {
                if (milaRetrofit==null){
                    milaRetrofit=new Retrofit.Builder()
                            .client(getClient(context))
                            .baseUrl(MilaServices.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(new ToStringConverterFactory())
                            .build();
                }
            }
        }
        return milaRetrofit;
    }

    public GankService getGankService(Context context){
        if(gankService==null){
            gankService=getRetrofit(context).create(GankService.class);
        }
        return gankService;
    }

    public MilaServices getMilaServices(Context context){
        if(milaServices==null){
            milaServices=getMilaRetrofit(context).create(MilaServices.class);
        }
        return milaServices;
    }



    public OkHttpClient getClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                     .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .cache(cache)
                    .build();
        return client;
    }
}

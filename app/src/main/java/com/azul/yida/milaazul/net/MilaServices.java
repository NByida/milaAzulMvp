package com.azul.yida.milaazul.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface MilaServices {
    String BASE_URL = "http://gorgeousyage.tumblr.com/";

    @GET("tagged/Mila+Azul/page/{page}")
//    @Headers("Cache-control:max-stale="+3600)
    Observable<String> getMilaDate(@Path("page") int page);



    @GET
//    @Headers("Cache-control:max-stale="+3600)
    Observable<String> getCuteDate(@Url String url);

}

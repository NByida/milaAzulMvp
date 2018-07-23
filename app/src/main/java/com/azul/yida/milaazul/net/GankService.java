package com.azul.yida.milaazul.net;

import com.azul.yida.milaazul.net.Entity.DateModel;
import com.azul.yida.milaazul.net.Entity.Gank;
import com.azul.yida.milaazul.net.Entity.GankModel;
import com.azul.yida.milaazul.net.Entity.PostGankModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by xuyimin on 2018/7/19.
 * E-mail codingyida@qq.com
 */

public interface GankService {
    String BASE_URL = "http://gank.io/api/";
    String ANDROID = "Android";
    String 福利 = "福利";
    String IOS = "iOS";
    String 休息视频 = "休息视频";
    String 拓展资源 = "拓展资源";
    String 前端 = "前端";
    String ALL = "All";
    String 瞎推荐 = "瞎推荐";
    String SApp = "App";

    /**
     * 获取不同类型的干货
     *
     * @param type 类型
     * @param size 数量
     * @param page 页数
     */
    @GET("data/{type}/{size}/{page}")
   // @Headers("Cache-control:max-stale="+3600)
    Observable<GankModel<List<Gank>>> getData(
            @Path("type") String type, @Path("size") int size, @Path("page") int page);

    /**
     * 获取发过干货日期接口
     */
    @GET("day/history") Observable<GankModel<List<String>>> getHistoryDate();

    /**
     * 获取某一天的数据
     */
    @GET("day/{year}/{month}/{day}") Observable<GankModel<DateModel>> getDataOnSomeday(
            @Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 投稿
     */
    @FormUrlEncoded
    @POST("add2gank") Observable<PostGankModel> postGank(@Field("url") String url,
                                                       @Field("desc") String desc, @Field("who") String who, @Field("type") String type,
                                                       @Field("debug") boolean debug);

}

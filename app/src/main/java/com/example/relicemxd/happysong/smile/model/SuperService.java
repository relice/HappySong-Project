package com.example.relicemxd.happysong.smile.model;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;
import rx.Observable;

/**
 * Created by relicemxd on 16/1/21.
 * retrofit网络请求,解析数据
 */
public interface SuperService {
    String baseUrl = "http://api.1-blog.com";//api接口
    String parmsUrl = "?maxXhid=10000&size=1&minXhid=6&page=1";

    /**
     * 注意:
     * 1. maxXhid=10000 是参数,可用 @Query 动态修改
     * 2. /xiaohua 是路径,可用 @Path 动态修改
     *
     * @param xiaohua 类别笑话
     * @param maxXhid 最大笑话id,当前数据库最高存10000条笑话
     * @param minXhid 最小笑话id,1
     * @param size    笑话数量,最大为5
     * @param page    页数
     * @return
     */
    @GET("/biz/bizserver/{xiaohua}/list.do")
    Observable<SmileInfo> publicRepositories(
            @Path("xiaohua") String xiaohua,
            @Query("maxXhid") int maxXhid,
            @Query("minXhid") int minXhid,
            @Query("size") int size,
            @Query("page") int page);

    @GET("/biz/bizserver/xiaohua/list.do")
    Observable<SmileInfo> publicRepositories(
            @Query("maxXhid") int maxXhid,
            @Query("minXhid") int minXhid,
            @Query("size") int size,
            @Query("page") int page);

    @GET
    Observable<SmileInfo> superInfoFromUrl(@Url String userUrl);


    class Factory {
        /**
         * 动态代理:
         * 我给Retrofit对象传了一个SmileService接口的Class对象，怎么又返回一个SmileService对象呢？
         * 进入create方法一看，没几行代码，但是我觉得这几行代码就是Retrofit的精妙的地方.
         *
         * @return
         */
        public static SuperService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(SuperService.class);
        }
    }
}

package com.example.relicemxd.happysong.net;

import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.bean.SmileInfo;

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
     * url path + url prams 用法
     * 注意:
     * 1. maxXhid=10000 是参数,可用 @Query 动态修改
     * 2. /xiaohua 是路径,可用 @Path 动态修改
     *
     * @param xiaohua 类别笑话/新闻
     * @param maxXhid 最大笑话id,当前数据库最高存10000条笑话
     * @param minXhid 最小笑话id,1
     * @param size 笑话数量,最大为5
     * @param page 页数
     * @return
     *///笑话
    @GET("/biz/bizserver/{xiaohua}/list.do")
    Observable<SmileInfo> publicSmileRepositories(
            @Path("xiaohua") String xiaohua,
            @Query("maxXhid") int maxXhid,
            @Query("minXhid") int minXhid,
            @Query("size") int size,
            @Query("page") int page);

    /**
     * url prams 用法
     *
     * @param max_behot_time:指定获取哪个时间点前的新闻，毫秒计数的整数值（以新闻收录时间为依据）
     * @param size: 获取新闻的条数
     *///新闻
    @GET("/biz/bizserver/news/list.do")
    Observable<NewInfo> publicNewsRepositories(
            @Query("size") int size,
            @Query("max_behot_time") long max_behot_time);

    /**
     * 全 url 用法
     */
    @GET
    Observable<SmileInfo> superInfoFromUrl(@Url String userUrl);


    class Factory {
        /**
         * 动态代理:
         * 我给Retrofit对象传了一个SmileService接口的Class对象，怎么又返回一个SmileService对象呢？
         * 进入create方法一看，没几行代码，但是我觉得这几行代码就是Retrofit的精妙的地方.
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

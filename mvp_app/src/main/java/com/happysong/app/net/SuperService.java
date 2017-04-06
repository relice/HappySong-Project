package com.happysong.app.net;

import android.graphics.Bitmap;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.bean.NewInfo;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.File;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.Url;
import rx.Observable;

/**
 * Created by relicemxd on 16/1/21.
 * retrofit网络请求,解析数据
 */
public interface SuperService {


    //base api接口
    String baseUrl = "http://route.showapi.com";
    //百思不得其姐接口
    String parmsUrl = "showapi_appid=31249&showapi_sign=137df2f5f9f046fa96703a0578fbe098";

    /**
     * url path + url prams 用法
     * 注意:
     * 1.参数: maxXhid=10000 可用 @Query 动态修改
     * 2.路径: /xiaohua      可用 @Path  动态修改
     *
     * @param type 查询的类型，默认全部返回。
     * type=10 图片
     * type=29 段子
     * type=31 声音
     * type=41 视频
     * @param page 第几页。每页最多返回20条记录
     * @param title 文本中包括的内容，模糊查询
     * @param showapi_appid 百思不得其姐api,必要
     * @param showapi_sign 个人数字签名信息,必要
     */
    @GET("/255-1")//注意填入参数的方法必须要在@get中有url 否则会报错
    Observable<BsbdqjInfo> getBSBDQJInfos(
            @Query("type") String type,
            @Query("page") int page,
            @Query("showapi_appid") String showapi_appid,
            @Query("showapi_sign") String showapi_sign
    );

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
     * http://www.pifayanjing.com/?m=app&do=index_json
     * 全 url 用法
     */
    @GET
    Observable<BsbdqjInfo> superInfoFromUrl(@Url String userUrl);

    @GET
    Observable<Bitmap> getVideoThumbnailo(@Url String userUrl);

    /**
     * 上传文件
     *
     * @param description 请求体描述
     * @param file 文件
     */
    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              //注意这里的参数 "aFile" 之前是在创建 MultipartBody.Part 的时候传入的
                              @Part("aFile") File file);


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

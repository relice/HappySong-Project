package com.happysong.app.mainContent.videoModule.model;

import android.content.Context;

import com.happysong.app.HappySongApp;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.net.SuperService;
import com.hwangjr.rxbus.RxBus;

import com.happysong.app.HappySongApp;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.net.SuperService;
import com.happysong.app.utils.Preconditions;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.happysong.app.utils.Preconditions.checkNotNull;

/**
 * @Package: com.example.relicemxd.happysong.mainContent.videoModule.model
 * @Author: Relice
 * @Date: 2017/2/3
 * @Des: TODO
 */

public class VideoModel {
    public static final String VIDEOINFO_TAG = "videoinfo_tag";
    private Subscription subscribe;

    public void getBsbdqjInfo(Context cont, int page) {
        if (subscribe != null)
            subscribe.unsubscribe();

        HappySongApp app = HappySongApp.get(cont);
        SuperService superService = app.getSmileService();
        subscribe = superService
                .getBSBDQJInfos("41", page, "31249", "137df2f5f9f046fa96703a0578fbe098")//1执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())//IO线程
                .subscribe(new Subscriber<BsbdqjInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BsbdqjInfo videoInfo) {
                        //TODO  虽然解耦了model层,但是数据只能通过 事件总线来传递数据,否则就需要通过接口回调了
                        BsbdqjInfo bsbdqjInfo = Preconditions.checkNotNull(videoInfo);
                        RxBus.get().post(VIDEOINFO_TAG, bsbdqjInfo);
                    }
                });
    }

    public void detachView() {
        if (subscribe != null)
            subscribe.unsubscribe();
    }
}

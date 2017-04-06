package com.happysong.app.mainContent.duanziModule.model;

import android.content.Context;

import com.happysong.app.HappySongApp;
import com.happysong.app.mainContent.duanziModule.ILoadingListner;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.net.SuperService;
import com.hwangjr.rxbus.RxBus;


import com.happysong.app.HappySongApp;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Package: com.example.relicemxd.happysong.new_mvp_googlebase.model
 * @Author: Relice
 * @Date: 16/4/22
 * @Des: TODO
 */
public class DZModelImpl implements IDZModel {

    public final static String NEWINFO_TAG = "newinfo_tag";
    private static DZModelImpl newModel;

    private DZModelImpl() {
    }

    public static DZModelImpl newInstance() {
        if (newModel == null) {
            newModel = new DZModelImpl();
        }
        return newModel;
    }

    Subscription subscribe;

    @Override
    public void loadNewDatas(Context cont, int page, final ILoadingListner<BsbdqjInfo> listner) {
        if (subscribe != null)
            subscribe.unsubscribe();
        HappySongApp app = HappySongApp.get(cont);
        SuperService superService = app.getSmileService();

        subscribe = superService
                .getBSBDQJInfos("29", page, "31249", "137df2f5f9f046fa96703a0578fbe098")//1执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())//IO线程
                .subscribe(new Subscriber<BsbdqjInfo>() {
                    @Override
                    public void onCompleted() {
                        listner.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listner.onError(e);
                    }

                    @Override
                    public void onNext(BsbdqjInfo info) {
                        listner.onNext(info);

                        //TODO 使用RxBus post发送数据,避免了这里使用回调
                        RxBus.get().post(NEWINFO_TAG, info);
                    }
                });
    }
}

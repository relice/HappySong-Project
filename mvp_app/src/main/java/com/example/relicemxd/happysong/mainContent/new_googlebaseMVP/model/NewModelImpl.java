package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.model;

import android.content.Context;

import com.example.relicemxd.happysong.ILoadingListner;
import com.example.relicemxd.happysong.InfoApplication;
import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.net.SuperService;
import com.hwangjr.rxbus.RxBus;

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
public class NewModelImpl implements INewModel {

    public final static String NEWINFO_TAG = "newinfo_tag";
    private static NewModelImpl newModel;

    private NewModelImpl() {
    }

    public static NewModelImpl newInstance() {
        if (newModel == null) {
            newModel = new NewModelImpl();
        }
        return newModel;
    }

    Subscription subscribe;

    @Override
    public void loadNewDatas(Context cont, int page, final ILoadingListner<NewInfo> listner) {
        if (subscribe != null)
            subscribe.unsubscribe();
//api.1-blog.com/biz/bizserver/news/list.do
        InfoApplication app = InfoApplication.get(cont);
        SuperService superService = app.getSmileService();
        subscribe = superService
                .publicNewsRepositories(1000, System.currentTimeMillis())//执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())//IO线程
                .subscribe(new Subscriber<NewInfo>() {
                    @Override
                    public void onCompleted() {
                        listner.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listner.onError(e);
                    }

                    @Override
                    public void onNext(NewInfo info) {
                        listner.onNext(info);

                        //TODO 使用RxBus post发送数据,避免了这里使用回调
                        RxBus.get().post(NEWINFO_TAG, info);
                    }
                });
    }
}

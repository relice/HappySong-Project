package com.happysong.app.mainContent.duanziModule.contract;


import android.content.Context;

import com.happysong.app.mainContent.duanziModule.ILoadingListner;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.bean.NewInfo;
import com.happysong.app.mainContent.duanziModule.model.DZModelImpl;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.bean.NewInfo;
import com.happysong.app.mainContent.duanziModule.model.DZModelImpl;

import static com.happysong.app.mainContent.duanziModule.model.DZModelImpl.NEWINFO_TAG;
import static com.happysong.app.utils.Preconditions.checkNotNull;

/**
 * VideoMainActivity 与Model之间的主持类
 * 通过MainMvpView来建立act与model 之间交互
 */
public class DZPresenter implements DZContract.IPresenter {


    private final DZContract.IView mView;
    private DZModelImpl newModel;

    public DZPresenter(DZContract.IView view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
        RxBus.get().register(this);
    }

    //通过tag 订阅发送的数据
    @Subscribe(
            thread = EventThread.IO,
            tags = {
            @Tag(DZModelImpl.NEWINFO_TAG)
    }
    )
    //接收带tag 的数据
    @Tag(DZModelImpl.NEWINFO_TAG)
    public void getProduceMsg(NewInfo info) {
        String status = info.getStatus();
        System.out.println(DZModelImpl.NEWINFO_TAG+"_rxbus: " + status);
    }

    @Override
    public void loadNewDatas(Context cont, int page) {

        newModel.loadNewDatas(cont, page, new ILoadingListner<BsbdqjInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BsbdqjInfo newInfo) {
                mView.showDZInfos(newInfo);
            }
        });
    }

    /**
     * 创建model实例
     */
    @Override
    public void start() {
        newModel = DZModelImpl.newInstance();
    }

}

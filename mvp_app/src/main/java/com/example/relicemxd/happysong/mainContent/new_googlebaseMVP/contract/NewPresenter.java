package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.contract;


import android.content.Context;

import com.example.relicemxd.happysong.ILoadingListner;
import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.model.NewModelImpl;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import static com.example.relicemxd.happysong.utils.Preconditions.checkNotNull;

/**
 * MainActivity 与Model之间的主持类
 * 通过MainMvpView来建立act与model 之间交互
 */
public class NewPresenter implements NewContract.IPresenter {


    private final NewContract.IView mView;
    private NewModelImpl newModel;

    public NewPresenter(NewContract.IView view) {
        mView = checkNotNull(view);
        mView.setPresenter(this);
        RxBus.get().register(this);
    }

    //通过tag 订阅发送的数据
    @Subscribe(
            thread = EventThread.IO,
            tags = {
                    @Tag(NewModelImpl.NEWINFO_TAG)
            }
    )
    //接收带tag 的数据
    @Tag(NewModelImpl.NEWINFO_TAG)
    public void getProduceMsg(NewInfo info) {
        String status = info.getStatus();
        System.out.println("rxbus+++++++" + status);
    }

    @Override
    public void loadNewDatas(Context cont, int page) {
        newModel.loadNewDatas(cont, page, new ILoadingListner<NewInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewInfo newInfo) {
                mView.showNewInfos(newInfo);
            }
        });
    }

    /**
     * 创建model实例
     */
    @Override
    public void start() {
        newModel = NewModelImpl.newInstance();
    }
}

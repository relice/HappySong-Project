package com.happysong.app.mainContent.videoModule.presenter;


import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.videoModule.model.VideoModel;
import com.happysong.app.mainContent.videoModule.view.VideoMvpView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.videoModule.view.VideoMvpView;
import com.happysong.app.utils.Preconditions;
import rx.Subscription;

import static com.happysong.app.mainContent.videoModule.model.VideoModel.VIDEOINFO_TAG;
import static com.happysong.app.utils.Preconditions.checkNotNull;

/**
 * VideoMainActivity 与Model之间的主持类
 * 通过MainMvpView来建立act与model 之间交互
 */
public class VideoPresenter implements IPresenter<VideoMvpView> {


    private VideoMvpView mMainMvpView;
    private Subscription subscribe;
    private BsbdqjInfo mSmileInfos;
    private VideoModel model;

    /**
     * 绑定View
     *
     * @param view MianActivity的父接口(多态)
     */
    @Override
    public void attachView(VideoMvpView view) {
        RxBus.get().register(this);
        mMainMvpView = Preconditions.checkNotNull(view);
        model = new VideoModel();
    }

    /**
     * 解绑
     */
    @Override
    public void detachView() {
        this.mMainMvpView = null;
        if (subscribe != null)
            model.detachView();
    }

    /**
     * 加载Video数据
     *
     * @param page
     */
    public void loadBsbdqjInfo(int page) {
        model.getBsbdqjInfo(mMainMvpView.getContext(), page);
    }

    //通过tag 订阅RxBus发送的数据
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(VIDEOINFO_TAG)
            }
    )

    //接收RxBus带tag 的数据
    @Tag(VIDEOINFO_TAG)
    public void getVideoInfo(BsbdqjInfo info) {
        System.out.println("rxbus_videoInfo:" + info.getShowapi_res_body().getPagebean().getContentlist().get(0).getText());

        mMainMvpView.showVideoInfos(info);
    }
}

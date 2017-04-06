package com.happysong.app.mainContent.smileModule.presenter;


import com.happysong.app.HappySongApp;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.smileModule.view.SmileMvpView;
import com.happysong.app.net.SuperService;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.happysong.app.utils.Preconditions.checkNotNull;

/**
 * VideoMainActivity 与Model之间的主持类
 * 通过MainMvpView来建立act与model 之间交互
 */
public class SmilePresenter implements Presenter<SmileMvpView> {


    private SmileMvpView mMainMvpView;
    private Subscription subscribe;
    private BsbdqjInfo mSmileInfos;

    /**
     * 绑定View
     *
     * @param view MianActivity的父接口(多态)
     */
    @Override
    public void attachView(SmileMvpView view) {
        mMainMvpView = checkNotNull(view);
    }

    /**
     * 解绑
     */
    @Override
    public void detachView() {
        this.mMainMvpView = null;
        if (subscribe != null)
            subscribe.unsubscribe();
    }

    public void loadSmileDatas(int page) {
        if (subscribe != null)
            subscribe.unsubscribe();

        HappySongApp app = HappySongApp.get(mMainMvpView.getContext());
        SuperService superService = app.getSmileService();
        subscribe = superService
                .getBSBDQJInfos("10", page, "31249", "137df2f5f9f046fa96703a0578fbe098")//1执行请求参数
//                .getBSBDQJInfos(1000,6,5,page)//2执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())//IO线程
                .subscribe(new Subscriber<BsbdqjInfo>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted: smileinfo");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BsbdqjInfo smileInfo) {
                        mSmileInfos = checkNotNull(smileInfo);

                        mMainMvpView.showSmileInfos(mSmileInfos);
                    }
                });
    }
}

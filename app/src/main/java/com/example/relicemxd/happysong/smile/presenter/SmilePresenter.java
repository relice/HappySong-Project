package com.example.relicemxd.happysong.smile.presenter;


import com.example.relicemxd.happysong.InfoApplication;
import com.example.relicemxd.happysong.smile.model.SmileInfo;
import com.example.relicemxd.happysong.smile.model.SuperService;
import com.example.relicemxd.happysong.smile.view.SmileMvpView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * MainActivity 与Model之间的主持类
 * 通过MainMvpView来建立act与model 之间交互
 */
public class SmilePresenter implements Presenter<SmileMvpView> {


    private SmileMvpView mMainMvpView;
    private Subscription subscribe;
    private SmileInfo mSmileInfos;

    /**
     * 绑定View
     *
     * @param view MianActivity的父接口(多态)
     */
    @Override
    public void attachView(SmileMvpView view) {
        this.mMainMvpView = view;
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

    public void loadMainDatas(int page) {
        if (subscribe != null)
            subscribe.unsubscribe();

        InfoApplication app = InfoApplication.get(mMainMvpView.getContext());
        SuperService superService = app.getSmileService();
        subscribe = superService
                .publicRepositories("xiaohua", 1000, 6, 5, page)//执行请求参数
//                .publicRepositories(1000,6,1,page)//执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(app.defaultSubscribeScheduler())//IO线程
                .subscribe(new Subscriber<SmileInfo>() {
                    @Override
                    public void onCompleted() {
                        if (mSmileInfos != null) {
                            mMainMvpView.showSmileInfos(mSmileInfos);
//                            System.out.println("onCompleted");
                        } else {
//                            mMainMvpView.showMessage(R.string.text_empty_repos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        System.out.println("Throwable:" + e.getMessage());
                    }

                    @Override
                    public void onNext(SmileInfo smileInfo) {
                        SmilePresenter.this.mSmileInfos = smileInfo;
//                        System.out.println("onNext:" + smileInfo.getDetail());
                    }
                });
    }
}

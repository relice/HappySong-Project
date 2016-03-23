package com.example.relicemxd.happysong.mainContent.smile_commonMVP.presenter;


import com.example.relicemxd.happysong.InfoApplication;
import com.example.relicemxd.happysong.bean.SmileInfo;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.view.SmileMvpView;
import com.example.relicemxd.happysong.net.SuperService;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.relicemxd.happysong.utils.Preconditions.checkNotNull;

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

        InfoApplication app = InfoApplication.get(mMainMvpView.getContext());
        SuperService superService = app.getSmileService();
        subscribe = superService
                .publicSmileRepositories("xiaohua", 1000, 6, 5, page)//1执行请求参数
//                .publicSmileRepositories(1000,6,5,page)//2执行请求参数
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())//IO线程
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
                    }

                    @Override
                    public void onNext(SmileInfo smileInfo) {
                        mSmileInfos = checkNotNull(smileInfo);
                    }
                });
    }
}

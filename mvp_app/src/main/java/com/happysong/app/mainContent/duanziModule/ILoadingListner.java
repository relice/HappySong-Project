package com.happysong.app.mainContent.duanziModule;

/**
 * activity的根,共有数据
 */
public interface ILoadingListner<T> {

    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);
}

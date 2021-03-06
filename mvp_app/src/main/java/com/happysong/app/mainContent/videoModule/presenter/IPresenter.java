package com.happysong.app.mainContent.videoModule.presenter;

/**
 * DZContract 主持基类,主要功能:
 * 1.调用业务逻辑(Model)
 * 2.响应数据(View与Model通过Presenter交互)
 * 3.处理用户动作,如click事件
 *
 * @param <V>
 */
public interface IPresenter<V> {
    /**
     * 依附,绑定View(一般是Activity)
     * @param view
     */
    void attachView(V view);

    /**
     * 分离View
     */
    void detachView();

}

package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP;

import android.content.Context;

/**
 * activity的根,共有数据
 */
public interface IBaseView<T> {
    /**
     * activity上下环境
     *
     * @return
     */
    Context getContext();

    void setPresenter(T t);
}

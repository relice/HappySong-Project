package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.contract;

import android.content.Context;

import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.IBasePresenter;
import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.IBaseView;

/**
 * 契约接口,连接presenter 和 view
 */
public interface NewContract {
    interface IPresenter extends IBasePresenter {
        void loadNewDatas(Context cont, int page);
    }

    interface IView extends IBaseView<IPresenter> {
        void showNewInfos(NewInfo newInfo);
    }
}

package com.happysong.app.mainContent.duanziModule.contract;

import android.content.Context;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.duanziModule.IBasePresenter;
import com.happysong.app.mainContent.duanziModule.IBaseView;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.duanziModule.IBaseView;

/**
 * 契约接口,连接presenter 和 view
 */
public interface DZContract {
    interface IPresenter extends IBasePresenter {
        void loadNewDatas(Context cont, int page);
    }

    interface IView extends IBaseView<IPresenter> {
        void showDZInfos(BsbdqjInfo newInfo);
    }
}

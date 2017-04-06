package com.happysong.app.mainContent.duanziModule.model;

import android.content.Context;

import com.happysong.app.mainContent.duanziModule.ILoadingListner;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.duanziModule.ILoadingListner;

/**
 * @Package: com.example.relicemxd.happysong.new_mvp_googlebase.model
 * @Author: Relice
 * @Date: 16/4/22
 * @Des: TODO
 */
public interface IDZModel {
    void loadNewDatas(Context cont, int page, ILoadingListner<BsbdqjInfo> listner);
}

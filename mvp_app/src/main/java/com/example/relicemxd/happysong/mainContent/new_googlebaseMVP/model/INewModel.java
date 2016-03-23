package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.model;

import android.content.Context;

import com.example.relicemxd.happysong.ILoadingListner;
import com.example.relicemxd.happysong.bean.NewInfo;

/**
 * @Package: com.example.relicemxd.happysong.new_mvp_googlebase.model
 * @Author: Relice
 * @Date: 16/4/22
 * @Des: TODO
 */
public interface INewModel {
    void loadNewDatas(Context cont, int page, ILoadingListner<NewInfo> listner);
}

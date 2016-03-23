package com.example.relicemxd.happysong.smile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by relice on 16/3/9.
 * Fragment 基类
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAct = getActivity();
        initUtils();

        initData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initComponent();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract String getTabTitle();

    /**
     * 如果子Fragment要加载数据，复写此方法
     */
    protected void initData() {

    }

    /**
     * 如果子Fragment要添加Utils,Applaation 等辅助类实例，复写此方法
     */
    protected void initUtils() {
    }

    /**
     * 如果子Fragment要添加Receiver，复写此方法
     */
    protected void initComponent() {
    }

}

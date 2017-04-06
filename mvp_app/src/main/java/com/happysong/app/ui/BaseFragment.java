package com.happysong.app.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happysong.app.utils.SToast;
import com.happysong.app.utils.Utils;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

/**
 * Created by relice on 16/3/9.
 * Fragment 基类
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mAct;
    private int currentPage = 1;//初始页数
    public static final String FRAGMENT_TITLE = "FRAGMENT_TITLE";//标题

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        initArguments(getArguments());
        mAct = getActivity();
        initUtils();

        initData();

    }

    protected abstract void initArguments(Bundle arguments);


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

    /**
     * 如果子Fragment要添加Utils,Applaation 等辅助类实例，复写此方法
     */
    protected abstract String handleTitle();

    /**
     * 处理单击,双击事件
     */
    protected void loadMore(FloatingActionButton mainFloatview, LinearLayoutManager manager, OnLoadMoreListener listner) {
        mainFloatview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回顶部
                manager.scrollToPosition(0);
                ++currentPage;

                listner.onLoadMore(currentPage);

                //弹窗显示图片
                Utils.getInstance().setRandom(Utils.RANDOM_NUM, new Utils.RandomListner() {
                    @Override
                    public void onRandomRunListner(int random) {
                        Log.d("BaseFragment:", "randomNum:" + random);
                        Utils.getInstance().showRandomPOP(mAct);
                    }
                });
            }
        });

        mainFloatview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //返回顶部
                manager.scrollToPosition(0);

                --currentPage;
                if (currentPage <= 0) {
                    currentPage = 1;
                    SToast.s(getContext(), "已经是第一页了");
                    return true;
                }
                listner.onLoadBack(currentPage);//上一页
                return true;
            }
        });
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page);

        void onLoadBack(int page);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(MainActivity.MAINTACT_PRESS_BACK)}
    )

    @Tag(MainActivity.MAINTACT_PRESS_BACK)
    protected void onBackPressed() {
        System.out.println("video_Fragment: onBackPressed");
    }
}

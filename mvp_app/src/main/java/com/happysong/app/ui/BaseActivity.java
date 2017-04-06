package com.happysong.app.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.happysong.app.utils.SToast;

import butterknife.ButterKnife;

/**
 * Created by relice on 17/2/17.
 */
public abstract class BaseActivity extends FragmentActivity {

    public static final String ACTION_GO_BACK_HOME = "action_go_back_home";
    protected FragmentActivity mAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        handleIntent(getIntent());

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getContentView());

        initView();

        initData();

         /*基本上大部分页面都有返回首页的一个操作，所以提出来*/
        registerBackHome();
    }


    @Override
    protected void onResume() {
        super.onResume();
        handleOnResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handleOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handleOnDestroy();
        unregisterReceiver(goBackHomeReceiver);
    }


    protected void initView() {
        mAct = this;
    }

    private void registerBackHome() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GO_BACK_HOME);
        registerReceiver(goBackHomeReceiver, filter);
    }

    private void initData() {
        //執行到這裏子類的view才被設置完畢
        ButterKnife.bind(this);
        handleBackImg();
        initDatas();
    }


    /**
     * 跳转
     *
     * @param targetActivity
     */
    protected void handleJump(Class<Activity> targetActivity) {

        startActivity(new Intent(this, targetActivity));
    }

    /**
     * toast提示
     *
     * @param content
     */
    protected void handleToast(String content) {
        SToast.s(this, content);
    }

    /**
     * 返回按钮的处理
     */
    protected void handleBackImg() {
        View backImg = getBackImg();
        if (backImg != null) {
            if (backImg instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) backImg;
                toolbar.setNavigationOnClickListener(v -> finish());
                return;
            }
            backImg.setOnClickListener(v -> finish());
        }
    }


    /**
     * 返回按钮的处理
     */
    protected void handleTitle(String title) {
        View backImg = getBackImg();
        if (backImg != null) {
            if (backImg instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) backImg;
                toolbar.setTitle(title);
                return;
            }
            backImg.setOnClickListener(v -> finish());
        }
    }

    protected abstract View getBackImg();


    /**
     * ondestroy生命周期
     */
    protected abstract void handleOnDestroy();

    /**
     * onstop生命周期
     */
    protected abstract void handleOnStop();

    /**
     * onResume生命周期
     */
    protected abstract void handleOnResume();

    /**
     * 设置ContentView
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化数据
     */
    protected abstract void initDatas();


    /**
     * 继承者选择性复写
     *
     * @param intent
     */
    protected void handleIntent(Intent intent) {
    }


    private GobackHomeReceiver goBackHomeReceiver = new GobackHomeReceiver();

    class GobackHomeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //把所有的页面都关了，就认为是返回到首页
            finish();
        }
    }
}

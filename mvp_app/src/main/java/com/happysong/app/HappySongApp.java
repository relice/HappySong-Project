package com.happysong.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.bmob.v3.Bmob;
import rx.Scheduler;

/**
 * Created by relicemxd on 16/1/22.
 */
public class HappySongApp extends Application {

    private static com.happysong.app.net.SuperService mSuperService;
    private Scheduler mDefaultSubscribeScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "06566d17031ddb7ded1f512cf029062b");

        MultiDex.install(this);
    }

    public static HappySongApp get(Context context) {
        return (HappySongApp) context.getApplicationContext();
    }

    public com.happysong.app.net.SuperService getSmileService() {
        if (mSuperService == null) {
            mSuperService = com.happysong.app.net.SuperService.Factory.create();
        }
        return mSuperService;
    }

    public void setSmileService(com.happysong.app.net.SuperService superService) {
        this.mSuperService = superService;
    }
}

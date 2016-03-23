package com.example.relicemxd.happysong;

import android.app.Application;
import android.content.Context;

import com.example.relicemxd.happysong.net.SuperService;

import rx.Scheduler;

/**
 * Created by relicemxd on 16/1/22.
 */
public class InfoApplication extends Application {

    private static SuperService mSuperService;
    private Scheduler mDefaultSubscribeScheduler;
//    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
//        mRefWatcher = LeakCanary.install(this);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        InfoApplication application = (InfoApplication) context.getApplicationContext();
//        return application.mRefWatcher;
//    }

    public static InfoApplication get(Context context) {
        return (InfoApplication) context.getApplicationContext();
    }

    public SuperService getSmileService() {
        if (mSuperService == null) {
            mSuperService = SuperService.Factory.create();
        }
        return mSuperService;
    }

    public void setSmileService(SuperService superService) {
        this.mSuperService = superService;
    }
}

package com.happysong.app.mainContent.smileModule.view;

import com.happysong.app.bean.BsbdqjInfo;

/**
 * Created by relicemxd on 16/1/21.
 * <p>
 * 接口继承接口
 * 1.唯独mainActivity 需要显示的产库数据
 * 这也是为什么笑项目不需要mvp,因为一个接口就给一个activity用有点
 * 太铺张浪费
 */
public interface SmileMvpView extends MvpView {
    void showSmileInfos(BsbdqjInfo smileInfo);
}

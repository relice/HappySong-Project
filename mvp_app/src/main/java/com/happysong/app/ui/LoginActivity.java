package com.happysong.app.ui;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.happysong.app.bean.LoginInfo;
import com.happysong.app.utils.BmobUtils;
import com.happysong.app.utils.SToast;
import com.happysong.app.utils.Utils;
import com.happysong.app.utils.gsyvideoplayer.utils.JumpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import view.SDelEditText;

import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    // 账号
    private SDelEditText login_username_et;
    // 密码
    private SDelEditText login_password_et;
    private Toolbar tb;
    private Button wxButton;

    @Override
    protected void initView() {
        super.initView();
        tb = (Toolbar) findViewById(com.happysong.app.R.id.mian_toolbar);
        // 账号
        login_username_et = (SDelEditText) findViewById(com.happysong.app.R.id.user_name);
        // 密码
        login_password_et = (SDelEditText) findViewById(com.happysong.app.R.id.pwd);
        // 微信登录
        wxButton = (Button) findViewById(com.happysong.app.R.id.user_login_wx_button);
        wxButton.setOnClickListener(this);

        // 提交数据的回调
        TextView bnConfirm = (TextView) findViewById(com.happysong.app.R.id.login_btn);
        bnConfirm.setOnClickListener(this);
    }

    @Override
    protected View getBackImg() {
        return tb;
    }

    @Override
    protected int getContentView() {
        return com.happysong.app.R.layout.login_activity;
    }

    @Override
    protected void initDatas() {
        handleTitle("登录");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 使dilog的activity点击窗体之外不消失本身
        View v = findViewById(com.happysong.app.R.id.rootView);
        int height = v.getTop();
        v.setFocusable(true);
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                //不finish
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case com.happysong.app.R.id.login_btn:
                setButtonOnclick(v);
                break;
            case com.happysong.app.R.id.user_login_wx_button:
                handleToast("微信登录");
                break;
        }
    }


    // 设置保存按钮点击事件
    private void setButtonOnclick(View v) {
        if (Utils.checkNetworkInfo(mAct)) {
            String userName = login_username_et.getText().toString();
            String passWord = login_password_et.getText().toString();

            if (isNullOrEmpty(userName)) {
                SToast.l(mAct, "账号不能为空");
            } else if (isNullOrEmpty(passWord)) {
                SToast.l(mAct, "密码不能为空");
            } else {

                LoginInfo info = new LoginInfo();
                info.setUsername(userName);
                info.setPassword(passWord);
                info.login(mAct, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        BmobUtils.INSTANCE.queryLoginUser(mAct, "fcj", new BmobUtils.OnBombQueryListner() {
                            @Override
                            public void onSusses(BmobObject obj) {
                                LoginInfo queryInfo = (LoginInfo) obj;
                                //更新登录次数
                                String num = queryInfo.getUserlogin_num();
                                int i = Integer.parseInt(num);
                                i = ++i;
                                info.setUserlogin_num(i + "");
                                //更新登录时间
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                                String dt = sdf.format(new Date());
                                info.setUserlogin_time(dt);
                                info.update(mAct);

                                //TODO 打开视频播放
                                //直接一个页面播放的
                                JumpUtils.goToVideoPlayer(mAct, v, queryInfo.getVideo_url());
                            }

                            @Override
                            public void onError(String e) {
                                Log.d("LoginDialogActivity:", e);
                            }
                        });

                        SToast.l(mAct, "登录成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        SToast.l(mAct, "登录失败:" + s);
                    }
                });
            }
        } else {
            SToast.l(mAct, "网络已断开，请稍后再试");
        }
    }

    @Override
    protected void handleOnDestroy() {

    }

    @Override
    protected void handleOnStop() {

    }

    @Override
    protected void handleOnResume() {

    }
}

package com.happysong.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
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

import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

/**
 * 用户弹窗登录
 */
public class LoginDialogActivity extends Activity implements OnClickListener {
    private Activity mAct;
    // 登录
    private TextView bnConfirm;
    // 账号
    private EditText login_username_et;
    // 密码
    private EditText login_password_et;
    // 悄悄离开
    private TextView login_back_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 点击dilog之外不消失
        setFinishOnTouchOutside(false);
        setContentView(com.happysong.app.R.layout.login_dlg_activity);
        this.mAct = this;
        initView();
    }

    private void initView() {
        // 账号
        login_username_et = (EditText) findViewById(com.happysong.app.R.id.login_username_et);
        // 密码
        login_password_et = (EditText) findViewById(com.happysong.app.R.id.login_password_et);

        // 退出设置
        login_back_tv = (TextView) findViewById(com.happysong.app.R.id.login_back_tv);
        login_back_tv.setVisibility(View.VISIBLE);
        login_back_tv.setOnClickListener(this);

        // 提交数据的回调
        bnConfirm = (TextView) findViewById(com.happysong.app.R.id.login_btn);
        bnConfirm.setOnClickListener(this);
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
            // 悄悄离开
            case com.happysong.app.R.id.login_back_tv:
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.happysong.app.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.happysong.app.bean.LoginInfo;
import com.happysong.app.bean.RndomInfo;

import java.util.List;

import com.happysong.app.utils.SToast;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Bmob后端云 工具类
 */
public enum BmobUtils {
    INSTANCE;

    /**
     * 保存随机图片
     * @param act
     * @param info
     */
    public void saveRandomImg(Activity act, RndomInfo info) {
        info.save(act, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(act, "创建数据成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(act, "创建数据失败：" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询登录用户
     * @param act
     * @param userName
     * @param listner
     */
    public void queryLoginUser(Activity act, String userName, OnBombQueryListner listner) {
        BmobQuery<LoginInfo> bmobQuery = new BmobQuery<>();
        //查询username字段的值含有“sm”的数据
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.setLimit(10);
        bmobQuery.findObjects(act, new FindListener<LoginInfo>() {
            @Override
            public void onSuccess(List<LoginInfo> list) {
                if (list.size() > 0) {
                    LoginInfo info = list.get(0);
                    String objID = info.getObjectId();
                    Log.d("BmobUtils:", "查询成功objectId为：" + objID);
                    listner.onSusses(info);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.d("BmobUtils:", "查询失败：" + s);
                listner.onError(s);
            }
        });
    }


    /**
     * 查询随机图片信息
     * @param act
     * @param objectId
     * @param listner
     */
    public void queryRandomInfo(Activity act, String objectId, OnBombQueryListner listner) {
        BmobQuery<RndomInfo> bmobQuery = new BmobQuery<>();
        //查询username字段的值含有“sm”的数据
        bmobQuery.addWhereEqualTo("objectId", objectId);
        bmobQuery.setLimit(10);
        bmobQuery.findObjects(act, new FindListener<RndomInfo>() {
            @Override
            public void onSuccess(List<RndomInfo> list) {
                if (list.size() > 0) {
                    RndomInfo info = list.get(0);
                    String objID = info.getObjectId();
                    Log.d("BmobUtils:", "查询成功objectId为：" + objID);
                    listner.onSusses(info);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.d("BmobUtils:", "查询失败：" + s);
                listner.onError(s);
            }
        });
    }

    public interface OnBombQueryListner {
        void onSusses(BmobObject obj);

        void onError(String e);
    }

    /**
     * 注册新用户
     *
     * @param act
     * @param userName
     * @param passWord
     */
    public void regeistUser(Activity act, String userName, String passWord) {
        LoginInfo info = new LoginInfo();
        info.setUserID("");
        info.setUsername(userName);
        info.setPassword(passWord);
        info.setUser_comment("评论");
        info.setUser_grade("评分");
        info.setUserlogin_num("登录次数");
        info.setUserlogin_time("登录数据");
        info.setVideo_url("http://mvideo.spriteapp.cn/video/2017/0212/9af5d17a-f0cc-11e6-bafe-d4ae5296039d_wpc.mp4");
        info.signUp(act, new SaveListener() {
            @Override
            public void onSuccess() {
                SToast.l(act, "注册成功");
            }

            @Override
            public void onFailure(int i, String s) {
                SToast.l(act, "注册失败:" + s);
            }
        });
        info.save(act);
    }
}

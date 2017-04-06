package com.happysong.app.bean;

import cn.bmob.v3.BmobUser;

/**
 * @Author: Relice
 * @Des: 登录信息
 */
public class LoginInfo extends BmobUser {
    //用户ID
    private String userID;
    //评论
    private String user_comment;
    //评分
    private String user_grade;
    //登录时间
    private String userlogin_time;
    //登录次数
    private String userlogin_num;
    //relice视频
    private String video_url;


    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getUser_grade() {
        return user_grade;
    }

    public void setUser_grade(String user_grade) {
        this.user_grade = user_grade;
    }

    public String getUserlogin_time() {
        return userlogin_time;
    }

    public void setUserlogin_time(String userlogin_time) {
        this.userlogin_time = userlogin_time;
    }

    public String getUserlogin_num() {
        return userlogin_num;
    }

    public void setUserlogin_num(String userlogin_num) {
        this.userlogin_num = userlogin_num;
    }
}

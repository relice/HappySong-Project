package com.happysong.app.bean;

import cn.bmob.v3.BmobObject;

/**
 * 后端云数据
 * <p>
 * http://chuantu.biz/t5/46/1486356515x242647194.jpg
 */
public class RndomInfo extends BmobObject {

    //图片ID 与objID不同
    private String img_id;
    //地址
    private String img_url;
    //诗歌描述
    private String img_des;
    //标题
    private String img_title;
    //类型:ssr,sr,r,n
    private String img_type;
    //是否被收藏
    private boolean is_liked;
    //relice寄语
    private String relice_say;
    //评论
    private String user_comment;
    //评分
    private String user_grade;

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

    public String getRelice_say() {
        return relice_say;
    }

    public void setRelice_say(String relice_say) {
        this.relice_say = relice_say;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_type() {
        return img_type;
    }

    public void setImg_type(String img_type) {
        this.img_type = img_type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_des() {
        return img_des;
    }

    public void setImg_des(String img_des) {
        this.img_des = img_des;
    }

    public String getImg_title() {
        return img_title;
    }

    public void setImg_title(String img_title) {
        this.img_title = img_title;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    @Override
    public String toString() {
        return "RndomInfo{" +
                "obj_id='" + getObjectId() + '\'' +
                "img_id='" + img_id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", img_des='" + img_des + '\'' +
                ", img_title='" + img_title + '\'' +
                ", img_type='" + img_type + '\'' +
                ", is_liked=" + is_liked +
                '}';
    }
}

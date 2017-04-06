package com.happysong.app.utils.gsyvideoplayer.model;

import android.graphics.Bitmap;

/**
 * Created by shuyu on 2016/11/11.
 */

public class VideoModel {
    private String video_uri;
    private String text;
    private String hate;
    private String love;
    private Bitmap video_bitmap;
    private String name;
    private String create_time;

    public String getVideo_uri() {
        return video_uri;
    }

    public void setVideo_uri(String video_uri) {
        this.video_uri = video_uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHate() {
        return hate;
    }

    public void setHate(String hate) {
        this.hate = hate;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Bitmap getVideo_bitmap() {
        return video_bitmap;
    }

    public void setVideo_bitmap(Bitmap video_bitmap) {
        this.video_bitmap = video_bitmap;
    }
}

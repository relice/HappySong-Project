package com.happysong.app.utils.gsyvideoplayer.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.happysong.app.R;

import video.CustomGSYVideoPlayer;


/**
 * Created by shuyu on 2016/12/23.
 * CustomGSYVideoPlayer是试验中，建议使用的时候使用StandardGSYVideoPlayer
 */
public class LandLayoutVideo extends CustomGSYVideoPlayer {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LandLayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LandLayoutVideo(Context context) {
        super(context);
    }

    public LandLayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return com.happysong.app.R.layout.sample_video_land;
        }
        return com.happysong.app.R.layout.sample_video;
    }

    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(com.happysong.app.R.drawable.video_click_pause_selector);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(com.happysong.app.R.drawable.video_click_play_selector);
            } else {
                imageView.setImageResource(com.happysong.app.R.drawable.video_click_play_selector);
            }
        } else {
            super.updateStartImage();
        }
    }


}

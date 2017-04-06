package com.happysong.app.utils.gsyvideoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.happysong.app.utils.gsyvideoplayer.utils.JumpUtils;
import com.happysong.app.R;

import com.happysong.app.utils.gsyvideoplayer.utils.JumpUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.Debuger;
import video.GSYVideoManager;

public class VideoMainActivity extends AppCompatActivity {

    @Bind(com.happysong.app.R.id.open_btn)
    Button openBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happysong.app.R.layout.activity_main_video);
        Debuger.enable();
        ButterKnife.bind(this);
    }

    @OnClick({com.happysong.app.R.id.open_btn, com.happysong.app.R.id.list_btn, com.happysong.app.R.id.list_btn_2, com.happysong.app.R.id.list_detail, com.happysong.app.R.id.clear_cache, com.happysong.app.R.id.recycler, com.happysong.app.R.id.recycler_2, com.happysong.app.R.id.list_detail_list, com.happysong.app.R.id.web_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.happysong.app.R.id.open_btn:
                //直接一个页面播放的
                JumpUtils.goToVideoPlayer(this, openBtn,"");
                break;
            case com.happysong.app.R.id.list_btn:
                //普通列表播放，只支持全屏，但是不支持屏幕重力旋转，滑动后不持有
                JumpUtils.goToVideoPlayer(this);
                break;
            case com.happysong.app.R.id.list_btn_2:
                //支持全屏重力旋转的列表播放，滑动后不会被销毁
                JumpUtils.goToVideoPlayer2(this);
                break;
            case com.happysong.app.R.id.recycler:
                //recycler的demo
                JumpUtils.goToVideoRecyclerPlayer(this);
                break;
            case com.happysong.app.R.id.recycler_2:
                //recycler的demo
                JumpUtils.goToVideoRecyclerPlayer2(this);
                break;
            case com.happysong.app.R.id.list_detail:
                //支持旋转全屏的详情模式
                JumpUtils.goToDetailPlayer(this);
                break;
            case com.happysong.app.R.id.list_detail_list:
                //播放一个连续列表
                JumpUtils.goToDetailListPlayer(this);
                break;
            case com.happysong.app.R.id.web_detail:
                //播放一个连续列表
                JumpUtils.gotoWebDetail(this);
                break;
            case com.happysong.app.R.id.clear_cache:
                //清理缓存
                GSYVideoManager.clearAllDefaultCache(VideoMainActivity.this);
                //String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                //GSYVideoManager.clearDefaultCache(VideoMainActivity.this, url);
                break;
        }
    }
}

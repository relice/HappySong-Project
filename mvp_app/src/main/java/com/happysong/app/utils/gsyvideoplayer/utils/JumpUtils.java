package com.happysong.app.utils.gsyvideoplayer.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;

import com.happysong.app.utils.gsyvideoplayer.DetailListPlayer;
import com.happysong.app.utils.gsyvideoplayer.DetailPlayer;
import com.happysong.app.utils.gsyvideoplayer.ListVideo2Activity;
import com.happysong.app.utils.gsyvideoplayer.ListVideoActivity;
import com.happysong.app.utils.gsyvideoplayer.PlayActivity;
import com.happysong.app.utils.gsyvideoplayer.RecyclerView2Activity;
import com.happysong.app.utils.gsyvideoplayer.RecyclerViewActivity;
import com.happysong.app.utils.gsyvideoplayer.WebDetailActivity;
import com.happysong.app.R;

import com.happysong.app.utils.gsyvideoplayer.DetailPlayer;
import com.happysong.app.utils.gsyvideoplayer.ListVideo2Activity;
import com.happysong.app.utils.gsyvideoplayer.ListVideoActivity;
import com.happysong.app.utils.gsyvideoplayer.PlayActivity;
import com.happysong.app.utils.gsyvideoplayer.RecyclerViewActivity;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;


/**
 * Created by shuyu on 2016/11/11.
 */

public class JumpUtils {

    /**
     * 跳转到视频播放
     *
     * @param activity
     * @param view
     */
    public static void goToVideoPlayer(Activity activity, View view, String url) {
        Log.d("goToVideoPlayer_url:",url);
        Intent intent = new Intent(activity, PlayActivity.class);
        intent.putExtra(PlayActivity.VIDEO_URL, url);
        intent.putExtra(PlayActivity.TRANSITION, true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
            ActivityOptionsCompat activityOptions = makeSceneTransitionAnimation(
                    activity, pair);
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(com.happysong.app.R.anim.abc_fade_in, com.happysong.app.R.anim.abc_fade_out);
        }
    }

    /**
     * 跳转到视频列表
     *
     * @param activity
     */
    public static void goToVideoPlayer(Activity activity) {
        Intent intent = new Intent(activity, ListVideoActivity.class);
        ActivityOptionsCompat activityOptions = makeSceneTransitionAnimation(activity);
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    /**
     * 跳转到视频列表2
     *
     * @param activity
     */
    public static void goToVideoPlayer2(Activity activity) {
        Intent intent = new Intent(activity, ListVideo2Activity.class);
        ActivityOptionsCompat activityOptions = makeSceneTransitionAnimation(activity);
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    /**
     * 跳转到视频列表
     *
     * @param activity
     */
    public static void goToVideoRecyclerPlayer(Activity activity) {
        Intent intent = new Intent(activity, RecyclerViewActivity.class);
        ActivityOptionsCompat activityOptions = makeSceneTransitionAnimation(activity);
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    /**
     * 跳转到视频列表2
     *
     * @param activity
     */
    public static void goToVideoRecyclerPlayer2(Activity activity) {
        Intent intent = new Intent(activity, RecyclerView2Activity.class);
        ActivityOptionsCompat activityOptions = makeSceneTransitionAnimation(activity);
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    /**
     * 跳转到详情播放
     *
     * @param activity
     */
    public static void goToDetailPlayer(Activity activity) {
        Intent intent = new Intent(activity, DetailPlayer.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到详情播放
     *
     * @param activity
     */
    public static void goToDetailListPlayer(Activity activity) {
        Intent intent = new Intent(activity, DetailListPlayer.class);
        activity.startActivity(intent);
    }


    /**
     * 跳转到详情播放
     *
     * @param activity
     */
    public static void gotoWebDetail(Activity activity) {
        Intent intent = new Intent(activity, WebDetailActivity.class);
        activity.startActivity(intent);
    }
}

package com.happysong.app.utils.gsyvideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.happysong.app.utils.gsyvideoplayer.listener.SampleListener;
import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import com.happysong.app.R;

import java.util.ArrayList;
import java.util.List;

import com.happysong.app.utils.gsyvideoplayer.listener.SampleListener;
import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import utils.Debuger;
import video.GSYVideoManager;
import video.StandardGSYVideoPlayer;

/**
 * Created by shuyu on 2016/11/12.
 */

public class ListNormalAdapter extends BaseAdapter {

    public static final String TAG = "ListNormalAdapter";

    private List<VideoModel> list = new ArrayList<>();
    private LayoutInflater inflater = null;
    private Context context;

    private boolean isFullVideo;

    public ListNormalAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 40; i++) {
            list.add(new VideoModel());
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(com.happysong.app.R.layout.list_video_item_normal, null);
            holder.gsyVideoPlayer = (StandardGSYVideoPlayer) convertView.findViewById(com.happysong.app.R.id.video_item_player);
            holder.imageView = new ImageView(context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (position % 2 == 0) {
            holder.imageView.setImageResource(com.happysong.app.R.drawable.xxx1);
        } else {
            holder.imageView.setImageResource(com.happysong.app.R.drawable.xxx2);
        }
        if (holder.imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) holder.imageView.getParent();
            viewGroup.removeView(holder.imageView);
        }
        holder.gsyVideoPlayer.setThumbImageView(holder.imageView);

        final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";

        //默认缓存路径
        holder.gsyVideoPlayer.setUp(url, true , null, "这是title");

        //holder.gsyVideoPlayer.setNeedShowWifiTip(false);

        /************************下方为其他路径************************************/
        //如果一个列表的缓存路劲都一一致
        //holder.gsyVideoPlayer.setUp(url, true, new File(FileUtils.getTestPath(), ""));

        /************************下方为其他路径************************************/
        //如果一个列表里的缓存路劲不一致
        //int playPosition = GSYVideoManager.instance().getPlayPosition();
        //避免全屏返回的时候不可用了
        /*if (playPosition < 0 || playPosition != position ||
                !GSYVideoManager.instance().getPlayTag().equals(ListNormalAdapter.TAG)) {
            holder.gsyVideoPlayer.initUIState();
        }*/
        //如果设置了点击封面可以播放，如果缓存列表路径不一致，还需要设置封面点击
        /*holder.gsyVideoPlayer.setThumbPlay(true);

        holder.gsyVideoPlayer.getStartButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要切换缓存路径的
                holder.gsyVideoPlayer.setUp(url, true, new File(FileUtils.getTestPath(), ""));
                holder.gsyVideoPlayer.startPlayLogic();
            }
        });

        holder.gsyVideoPlayer.getThumbImageViewLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要切换缓存路径的
                holder.gsyVideoPlayer.setUp(url, true, new File(FileUtils.getTestPath(), ""));
                holder.gsyVideoPlayer.startPlayLogic();
            }
        });*/

        //增加title
        holder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        holder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        holder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(holder.gsyVideoPlayer);
            }
        });
        holder.gsyVideoPlayer.setRotateViewAuto(true);
        holder.gsyVideoPlayer.setLockLand(true);
        holder.gsyVideoPlayer.setPlayTag(TAG);
        holder.gsyVideoPlayer.setShowFullAnimation(true);
        //循环
        //holder.gsyVideoPlayer.setLooping(true);
        holder.gsyVideoPlayer.setNeedLockFull(true);

        //holder.gsyVideoPlayer.setSpeed(2);

        holder.gsyVideoPlayer.setPlayPosition(position);

        holder.gsyVideoPlayer.setStandardVideoAllCallBack(new SampleListener(){
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("onPrepared");
                if (!holder.gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                    GSYVideoManager.instance().setNeedMute(true);
                }

            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(true);
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(false);
            }
        });

        return convertView;
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, false, true);
        isFullVideo = true;
    }


    class ViewHolder {
        StandardGSYVideoPlayer gsyVideoPlayer;
        ImageView imageView;
    }

}

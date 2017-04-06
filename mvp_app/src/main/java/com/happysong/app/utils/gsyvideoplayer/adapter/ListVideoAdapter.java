package com.happysong.app.utils.gsyvideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import com.happysong.app.R;

import java.util.ArrayList;
import java.util.List;

import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import utils.ListVideoUtil;
import utils.OrientationUtils;

/**
 * Created by shuyu on 2016/11/11.
 */

public class ListVideoAdapter extends BaseAdapter {

    public final static String TAG = "TT2";

    private List<VideoModel> list = new ArrayList<>();
    private LayoutInflater inflater = null;
    private Context context;

    private ViewGroup rootView;
    private OrientationUtils orientationUtils;

    private boolean isFullVideo;

    private ListVideoUtil listVideoUtil;

    public ListVideoAdapter(Context context, ListVideoUtil listVideoUtil) {
        super();
        this.context = context;
        this.listVideoUtil = listVideoUtil;

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
            convertView = inflater.inflate(com.happysong.app.R.layout.list_video_item, null);
            holder.videoContainer = (FrameLayout) convertView.findViewById(com.happysong.app.R.id.list_item_container);
            holder.playerBtn = (ImageView) convertView.findViewById(com.happysong.app.R.id.list_item_btn);
            holder.imageView = new ImageView(context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setImageResource(com.happysong.app.R.drawable.xxx1);

        listVideoUtil.addVideoPlayer(position, holder.imageView, TAG, holder.videoContainer, holder.playerBtn);

        holder.playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);
                final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(url);
            }
        });
        return convertView;
    }


    class ViewHolder {
        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }
}
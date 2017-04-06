package com.happysong.app.utils.gsyvideoplayer.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import com.happysong.app.R;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GUO on 2015/12/3.
 */
public class RecyclerItemViewHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;
    @Bind(com.happysong.app.R.id.list_item_container)
    FrameLayout listItemContainer;
    @Bind(com.happysong.app.R.id.list_item_btn)
    ImageView listItemBtn;
    @Bind(com.happysong.app.R.id.video_item_title_tv)
    TextView video_item_title_tv;

    private ImageView imageView;

    public RecyclerItemViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
    }

    public void onBind(final int position, BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean model) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(com.happysong.app.R.drawable.smile_loading_1);


//        new GetVideoImgTask(new ITaskCallbackListener() {
//            @Override
//            public void doTaskComplete(Object o) {
//                if (o != null) {
//                    Bitmap bitmap = (Bitmap) o;
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }).execute(model.getVideo_uri(), "40", "40");


        //添加标题
        video_item_title_tv.setText(model.getText());

        listVideoUtil.addVideoPlayer(position, imageView, TAG, listItemContainer, listItemBtn);

        listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);

                final String video_uri = model.getVideo_uri();
                System.out.println("RecyclerItemViewHolder: " + video_uri);
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(video_uri);
            }
        });
    }

    public void onBind(final int position, VideoModel model) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(com.happysong.app.R.drawable.loading_anima);

        //添加标题
        video_item_title_tv.setText(model.getText());

        listVideoUtil.addVideoPlayer(position, imageView, TAG, listItemContainer, listItemBtn);

        listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);

                String video_uri = model.getVideo_uri();
                if (TextUtils.isEmpty(video_uri)) {
                    video_uri = "http://baobab.wdjcdn.com/14564977406580.mp4";
                }

                System.out.println("RecyclerItemViewHolder: " + video_uri);
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(video_uri);
            }
        });
    }


}






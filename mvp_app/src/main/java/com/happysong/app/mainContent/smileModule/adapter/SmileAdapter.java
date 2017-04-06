package com.happysong.app.mainContent.smileModule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.utils.Utils;
import com.happysong.app.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.Collections;
import java.util.List;

/**
 * Created by relicemxd on 16/1/21.
 */
public class SmileAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mSmileInfos;
    private Callback callback;
    private SimpleDraweeView itemCallBack;
    private final Utils mUtils;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将itemLayout传给holder即可
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(com.happysong.app.R.layout.smile_list_item, parent, false);
        return new SmileViewHolder(itemView);
    }

    public SmileAdapter(Context context) {
        mContext = context;
        this.mSmileInfos = Collections.emptyList();
        mUtils = Utils.getInstance();

        //png,wep多格式支持
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(mContext)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(mContext, config);
    }

    public void setmSmileInfos(List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> smileInfos) {
        this.mSmileInfos = smileInfos;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void setItemCallBack(final SimpleDraweeView img, final BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean info) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    //监听回调,具体实现让activity来
                    callback.onItemClick(info);
                }
            }
        });
    }

    public interface Callback {
        void onItemClick(BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean repository);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SmileViewHolder svh = (SmileViewHolder) holder;
        BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean info = mSmileInfos.get(position);

        svh.edt_tv.setText(info.getName());
        svh.cont_tv.setText(info.getText());

        SimpleDraweeView contIV = svh.getContIV();
        String picUrl = info.getImage1();
        if (picUrl != null && !TextUtils.isEmpty(picUrl)) {
            //提示gif图片
            if (picUrl.endsWith(".gif")) {
                svh.gif_notic_iv.setVisibility(View.VISIBLE);
            } else {
                svh.gif_notic_iv.setVisibility(View.GONE);
            }

            contIV.setVisibility(View.VISIBLE);

//            Picasso.with(mContext)//创建picasso实例
//                    .load(picUrl)//加载图片地址
//                    .placeholder(R.drawable.smilde_loading_anima)//加载中
//                    .into(contIV);//要显示的ImageView

            mUtils.showThumb(mContext, picUrl, contIV, false);
        } else {
            contIV.setVisibility(View.GONE);
        }

        setItemCallBack(contIV, info);
    }


    @Override
    public int getItemCount() {
        if (mSmileInfos != null) {
            return mSmileInfos.size();
        }
        return 0;
    }


    //VideoAdapter 的holder
    private class SmileViewHolder extends RecyclerView.ViewHolder {
        TextView edt_tv;
        TextView cont_tv;
        ImageView gif_notic_iv;
        private SimpleDraweeView cont_iv;

        SmileViewHolder(View itemView) {
            super(itemView);
            edt_tv = (TextView) itemView.findViewById(com.happysong.app.R.id.item_title_tv);
            cont_tv = (TextView) itemView.findViewById(com.happysong.app.R.id.item_cont_tv);
            gif_notic_iv = (ImageView) itemView.findViewById(com.happysong.app.R.id.gif_notic_iv);
            cont_iv = (SimpleDraweeView) itemView.findViewById(com.happysong.app.R.id.cont_iv);
        }

        SimpleDraweeView getContIV() {
            return cont_iv;
        }
    }
}

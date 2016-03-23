package com.example.relicemxd.happysong.mainContent.smile_commonMVP.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.relicemxd.happysong.utils.Utils;
import com.example.relicemxd.happysong.bean.SmileInfo;
import com.example.relicemxd.leanrrv.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collections;
import java.util.List;

/**
 * Created by relicemxd on 16/1/21.
 *
 */
public class SmileListRecyclerViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<SmileInfo.DetailEntity> mSmileInfos;
    private Callback callback;
    private SimpleDraweeView itemCallBack;
    private final Utils mUtils;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将itemLayout传给holder即可
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.smile_list_item, parent, false);
        return new SmileViewHolder(itemView);
    }

    public SmileListRecyclerViewAdapter(Context context) {
        mContext = context;
        this.mSmileInfos = Collections.emptyList();
        mUtils = Utils.getInstance();
        Fresco.initialize(context);
    }

    public void setmSmileInfos(List<SmileInfo.DetailEntity> smileInfos) {
        this.mSmileInfos = smileInfos;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setItemCallBack(final SimpleDraweeView img, final SmileInfo.DetailEntity info) {
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
        void onItemClick(SmileInfo.DetailEntity repository);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SmileViewHolder svh = (SmileViewHolder) holder;
        SmileInfo.DetailEntity info = mSmileInfos.get(position);

        svh.edt_tv.setText(info.getAuthor());
        svh.cont_tv.setText(info.getContent());

        SimpleDraweeView contIV = svh.getContIV();
        String picUrl = info.getPicUrl();
        if (picUrl != null && !TextUtils.isEmpty(picUrl)) {
            contIV.setVisibility(View.VISIBLE);
            //Picasso 加载图片
           /* Picasso.with(mContext)//创建picasso实例
                    .load(picUrl)//加载图片地址
                    .placeholder(R.mipmap.ic_launcher)//加载失败,默认图片
                    .into(contIV);//要显示的ImageView */

            //Fresco 加载图片
            Uri uri = Uri.parse(picUrl);
            contIV.setImageURI(uri);

            setItemCallBack(contIV, info);
        } else {
            contIV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mSmileInfos != null) {
            return mSmileInfos.size();
        }
        return 0;
    }

    //NewsListRecyclerViewAdapter 的holder
    class SmileViewHolder extends RecyclerView.ViewHolder {
        public TextView edt_tv;
        public TextView cont_tv;
        private SimpleDraweeView cont_iv;

        public SmileViewHolder(View itemView) {
            super(itemView);
            edt_tv = (TextView) itemView.findViewById(R.id.item_title_tv);
            cont_tv = (TextView) itemView.findViewById(R.id.item_cont_tv);
            cont_iv = (SimpleDraweeView) itemView.findViewById(R.id.cont_iv);
        }

        SimpleDraweeView getContIV() {
            return cont_iv;
        }
    }
}

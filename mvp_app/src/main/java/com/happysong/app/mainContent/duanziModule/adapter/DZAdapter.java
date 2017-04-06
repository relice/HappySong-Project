package com.happysong.app.mainContent.duanziModule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.happysong.app.R;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.utils.Preconditions;

import java.util.Collections;
import java.util.List;


/**
 * Created by relicemxd on 16/1/21.
 */
public class DZAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> listInfos = Collections.emptyList();
    private Callback callback;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将itemLayout传给holder即可
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.duanzi_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    public DZAdapter(Context context) {
        mContext = Preconditions.checkNotNull(context);
        //Fresco 图片加载
        Fresco.initialize(context);
    }

    public void seListInfos(List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> newInfos) {
        listInfos = Preconditions.checkNotNull(newInfos);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder svh = (NewsViewHolder) holder;
        BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean info = listInfos.get(position);

        svh.item_title_tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        svh.item_title_tv.setText("  "+(position+1) + ". " + info.getName());

        System.out.println("duanzi: "+info.getText());
        svh.item_cont_tv.setText(info.getText());

//        svh.cont_iv.setVisibility(View.VISIBLE);
//        Glide
//             .with(mContext)
//                .load(info.getVideo_uri())
//                .into(svh.cont_iv);

        setItemCallBack(svh.item_list_rl, info);
    }

    @Override
    public int getItemCount() {
        if (listInfos != null) {
            return listInfos.size();
        }
        return 0;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setItemCallBack(final RelativeLayout item, final BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean info) {
        item.setOnClickListener(v -> {
            if (callback != null) {
                //监听回调,具体实现让activity来
                callback.onItemClick(info);
            }
        });
    }

    public interface Callback {
        void onItemClick(BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean repository);
    }

    //VideoAdapter 的holder
    class NewsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout item_list_rl;
        public TextView item_title_tv;
        public TextView item_cont_tv;
        public SimpleDraweeView cont_iv;

        public NewsViewHolder(View itemView) {
            super(itemView);
            item_list_rl = (RelativeLayout) itemView.findViewById(R.id.item_list_rl);
            item_title_tv = (TextView) itemView.findViewById(R.id.item_title_tv);
            item_cont_tv = (TextView) itemView.findViewById(R.id.item_cont_tv);
            cont_iv = (SimpleDraweeView) itemView.findViewById(R.id.cont_iv);
        }
    }
}

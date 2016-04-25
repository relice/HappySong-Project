package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.utils.Utils;
import com.example.relicemxd.leanrrv.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.relicemxd.happysong.utils.Preconditions.checkNotNull;

/**
 * Created by relicemxd on 16/1/21.
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<NewInfo.DetailEntity> listInfos = Collections.emptyList();
    private Callback callback;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将itemLayout传给holder即可
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.smile_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    public NewsListRecyclerViewAdapter(Context context) {
        mContext = checkNotNull(context);
        //Fresco 图片加载
        Fresco.initialize(context);

    }

    public void seListInfos(List<NewInfo.DetailEntity> newInfos) {
        listInfos = checkNotNull(newInfos);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder svh = (NewsViewHolder) holder;
        NewInfo.DetailEntity info = listInfos.get(position);

        svh.item_title_tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        svh.item_title_tv.setText(position + ". " + info.getTitle());
        String format = Utils.getInstance().format(new Date(info.getBehot_time()));
        svh.item_cont_tv.setText(info.getSource() + "    " + format);

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

    public void setItemCallBack(final RelativeLayout item, final NewInfo.DetailEntity info) {
        item.setOnClickListener(v -> {
            if (callback != null) {
                //监听回调,具体实现让activity来
                callback.onItemClick(info);
            }
        });
    }

    public interface Callback {
        void onItemClick(NewInfo.DetailEntity repository);
    }

    //NewsListRecyclerViewAdapter 的holder
    class NewsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout item_list_rl;
        public TextView item_title_tv;
        public TextView item_cont_tv;

        public NewsViewHolder(View itemView) {
            super(itemView);
            item_list_rl = (RelativeLayout) itemView.findViewById(R.id.item_list_rl);
            item_title_tv = (TextView) itemView.findViewById(R.id.item_title_tv);
            item_cont_tv = (TextView) itemView.findViewById(R.id.item_cont_tv);
        }
    }
}

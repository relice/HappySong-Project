package com.happysong.app.ui.masonry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happysong.app.bean.RndomInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {
    private List<RndomInfo> products;
    private Context mCont;
    private static RecycleItemClickListener itemClickListener;


    public MasonryAdapter(Context cont, List<RndomInfo> list, RecycleItemClickListener clickListener) {
        products = list;
        mCont = cont;
        itemClickListener = clickListener;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(com.happysong.app.R.layout.collect_masonry_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        Picasso.with(mCont)//创建picasso实例
                .load(products.get(position).getImg_url())//加载图片地址
                .placeholder(com.happysong.app.R.drawable.image_loading)//加载中
                .into(masonryView.imageView);//要显示的ImageView

        masonryView.textView.setText(products.get(position).getImg_title());
    }


    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    //viewholder
    public static class MasonryView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;


        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(com.happysong.app.R.id.masonry_item_img);
            textView = (TextView) itemView.findViewById(com.happysong.app.R.id.masonry_item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, this.getLayoutPosition());
        }
    }


}

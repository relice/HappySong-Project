package com.example.relicemxd.happysong.main_googleRxbusMVP;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.relicemxd.happysong.utilsUI.BaseFragment;
import com.example.relicemxd.leanrrv.R;

import java.util.List;

/**
 * Created by relicemxd on 16/3/11.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> mList;

    private int[] imageResId = {R.drawable.dian_nor,
            R.drawable.dian_nor,
            R.drawable.dian_nor};
    private Context context;

    public MainTabAdapter(FragmentManager fm, Context context, List<BaseFragment> list) {
        super(fm);
        this.context = context;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        //添加 Fragment实例
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mList != null && mList.size() > 0) {
            return mList.get(position).getTabTitle();
        } else {
            return "null";
        }
    }

//    public View getTabView(int position) {
//        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
//        TextView tv = (TextView) view.findViewById(R.id.textView);
//        tv.setText(tabTitles[position]);
//        ImageView img = (ImageView) view.findViewById(R.id.imageView);
//        img.setImageResource(imageResId[position]);
//        return view;
//    }
}

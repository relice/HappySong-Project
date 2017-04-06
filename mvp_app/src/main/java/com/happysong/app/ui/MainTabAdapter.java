package com.happysong.app.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import static com.happysong.app.utils.Preconditions.checkNotNull;
import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

/**
 * Created by relicemxd on 16/3/11.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<BaseFragment> mList;

    private Integer[] imageResId = {com.happysong.app.R.drawable.dian_nor,
            com.happysong.app.R.drawable.dian_nor,
            com.happysong.app.R.drawable.dian_nor};
    private Context mContext;

    public MainTabAdapter(FragmentManager fm, Context context, ArrayList<BaseFragment> list) {
        super(fm);
        this.mContext = checkNotNull(context);
        this.mList = checkNotNull(list);
    }

    @Override
    public Fragment getItem(int position) {
        //添加 Fragment实例
        if (!isNullOrEmpty(mList)) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (!isNullOrEmpty(mList)) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (!isNullOrEmpty(mList)) {
            return mList.get(position).handleTitle();
        } else {
            return "null";
        }
    }


}

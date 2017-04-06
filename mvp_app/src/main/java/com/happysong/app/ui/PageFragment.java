package com.happysong.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by relicemxd on 16/3/11.
 */
public class PageFragment extends BaseFragment {

    public static PageFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE, title);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    protected void initArguments(Bundle arguments) {
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(com.happysong.app.R.layout.fragment_page, container, false);
        TextView textView = (TextView) view.findViewById(com.happysong.app.R.id.page_tv);
        textView.setText(handleTitle());
        return view;
    }

    @Override
    protected String handleTitle() {
        return getArguments().getString(BaseFragment.FRAGMENT_TITLE);
    }
}

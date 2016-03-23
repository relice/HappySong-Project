package com.example.relicemxd.happysong.utilsUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.relicemxd.leanrrv.R;

/**
 * Created by relicemxd on 16/3/11.
 */
public class PageFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;


    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view.findViewById(R.id.page_tv);
        textView.setText("测试数据: " + mPage);
        return view;
    }

    @Override
    public String getTabTitle() {
        return "Title";
    }
}

package com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.contract;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.relicemxd.happysong.bean.NewInfo;
import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.adapter.NewsListRecyclerViewAdapter;
import com.example.relicemxd.happysong.utils.SToast;
import com.example.relicemxd.happysong.utilsUI.BaseFragment;
import com.example.relicemxd.happysong.utilsUI.Html5Activity;
import com.example.relicemxd.leanrrv.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.relicemxd.happysong.utils.Preconditions.checkNotNull;

/**
 * Created by relicemxd on 16/3/11.
 * 笑话页面
 */
public class NewFragment extends BaseFragment implements NewContract.IView, View.OnClickListener {

    @Bind(R.id.mian_rv)
    RecyclerView mainRv;
    @Bind(R.id.main_floatview)
    FloatingActionButton mainFloatview;
    private String title;
    private int currentPage;
    private NewContract.IPresenter mPresenter;

    public NewFragment(String title) {
        this.title = title;
    }

    @Override
    protected void initUtils() {
        super.initUtils();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.smile_layout, container, false);
        ButterKnife.bind(this, view);

        //微笑内容列表
        initNewList();

        //处理view点击事件
        handlerClickEvent();
        return view;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
        mPresenter.loadNewDatas(getContext(), currentPage);
    }

    private void initNewList() {
        NewsListRecyclerViewAdapter ada =
                new NewsListRecyclerViewAdapter(getContext());
        //回调操作
        ada.setCallback(info -> {
            //打开webView
            startActivity(new Intent(getContext(),
                    Html5Activity.class).putExtra("title", info.getTitle()).
                    putExtra("url", info.getArticle_url()));
        });
        mainRv.setLayoutManager(new LinearLayoutManager(mAct));
        mainRv.setAdapter(ada);
    }

    /**
     * 处理单击,双击事件
     */
    private void handlerClickEvent() {
        mainFloatview.setOnClickListener(v -> {
            ++currentPage;
            mPresenter.loadNewDatas(getContext(), currentPage);
        });

        //上一页
        mainFloatview.setOnLongClickListener(v -> {
            --currentPage;
            if (currentPage < 0) {
                currentPage = 0;
                SToast.s(getContext(), "已经是第一页了");
                return true;
            }
            mPresenter.loadNewDatas(getContext(), currentPage);
            return true;
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void showNewInfos(NewInfo newInfo) {
        Log.d("new_fragment: ", newInfo.toString());
        NewsListRecyclerViewAdapter adapter = (NewsListRecyclerViewAdapter) mainRv.getAdapter();
        adapter.seListInfos(newInfo.getDetail());
        mainRv.requestFocus();
        mainRv.setAdapter(adapter);
    }

    @Override
    public void setPresenter(NewContract.IPresenter iPresenter) {
        mPresenter = checkNotNull(iPresenter);
    }
}

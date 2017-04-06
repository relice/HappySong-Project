package com.happysong.app.mainContent.duanziModule.contract;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.bean.NewInfo;
import com.happysong.app.mainContent.duanziModule.adapter.DZAdapter;
import com.happysong.app.ui.BaseFragment;
import com.happysong.app.ui.Html5Activity;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.happysong.app.utils.Preconditions.checkNotNull;

/**
 * Created by relicemxd on 16/3/11.
 * 段子页面
 */
public class DZFragment extends BaseFragment implements DZContract.IView, View.OnClickListener {

    @Bind(com.happysong.app.R.id.mian_rv)
    RecyclerView mainRv;
    @Bind(com.happysong.app.R.id.main_floatview)
    FloatingActionButton mainFloatview;
    private DZContract.IPresenter mPresenter;
    private LinearLayoutManager manager;

    //在 fragment或者activity中不可以使用构造方法,签名编译会报错
//    public DZFragment(String title) {
//        this.title = title;
//    }

    @Override
    protected void initUtils() {
        super.initUtils();
    }

    @Override
    protected String handleTitle() {
        return getArguments().getString(BaseFragment.FRAGMENT_TITLE);
    }

    @Override
    protected void initArguments(Bundle arguments) {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(com.happysong.app.R.layout.smile_layout, container, false);
        ButterKnife.bind(this, view);

        //微笑内容列表
        initNewList();

        initLoadmore();
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
        mPresenter.loadNewDatas(getContext(), 1);
    }

    private void initNewList() {
        DZAdapter ada =
                new DZAdapter(getContext());
        //回调操作
//        ada.setCallback(this::jumpToWebView);

        manager = new LinearLayoutManager(mAct);
        mainRv.setLayoutManager(manager);
        mainRv.setAdapter(ada);
    }

    /**
     * 跳转webView
     *
     * @param info
     */
    public void jumpToWebView(NewInfo.DetailEntity info) {
        startActivity(new Intent(getContext(),
                Html5Activity.class).putExtra("title", info.getTitle()).
                putExtra("url", info.getArticle_url()));
    }

    /**
     * 加载更多,加载上一页
     */
    private void initLoadmore() {
        loadMore(mainFloatview, manager, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                mPresenter.loadNewDatas(getContext(), page);
            }

            @Override
            public void onLoadBack(int page) {
                mPresenter.loadNewDatas(getContext(), page);
            }
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
    public void showDZInfos(BsbdqjInfo newInfo) {
        Log.d("DZFragment: ", newInfo.toString());
        DZAdapter adapter = (DZAdapter) mainRv.getAdapter();
        adapter.seListInfos(newInfo.getShowapi_res_body().getPagebean().getContentlist());
        mainRv.requestFocus();
        mainRv.setAdapter(adapter);
    }

    @Override
    public void setPresenter(DZContract.IPresenter iPresenter) {
        mPresenter = checkNotNull(iPresenter);
    }
}

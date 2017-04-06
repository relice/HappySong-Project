package com.happysong.app.mainContent.smileModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happysong.app.R;
import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.smileModule.adapter.SmileAdapter;
import com.happysong.app.mainContent.smileModule.presenter.SmilePresenter;
import com.happysong.app.mainContent.smileModule.view.SmileMvpView;
import com.happysong.app.ui.BaseFragment;
import com.happysong.app.ui.PhotoViewActivity;

import java.util.List;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.mainContent.smileModule.adapter.SmileAdapter;
import com.happysong.app.mainContent.smileModule.presenter.SmilePresenter;
import com.happysong.app.mainContent.smileModule.view.SmileMvpView;
import com.happysong.app.ui.BaseFragment;
import com.happysong.app.ui.PhotoViewActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by relicemxd on 16/3/11.
 * 笑话页面
 */
public class SmileFragment extends BaseFragment implements View.OnClickListener, SmileMvpView {

    @Bind(com.happysong.app.R.id.mian_rv)
    RecyclerView mainRv;
    @Bind(com.happysong.app.R.id.main_floatview)
    FloatingActionButton mainFloatview;

    private SmilePresenter mianPre;
    private FragmentActivity mAct;
    private LinearLayoutManager manager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(com.happysong.app.R.layout.smile_layout, container, false);
        ButterKnife.bind(this, view);

        mAct = getActivity();

        //微笑内容列表
        initSmileList();

        //加载更多礼拜
        initLoadMore();
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        //建立 presenter
        mianPre = new SmilePresenter();
        mianPre.attachView(this);
        mianPre.loadSmileDatas(1);
    }

    @Override
    protected String handleTitle() {
        return getArguments().getString(BaseFragment.FRAGMENT_TITLE);
    }

    private void initSmileList() {
        SmileAdapter ada =
                new SmileAdapter(getContext());
        //回调操作
        ada.setCallback(new SmileAdapter.Callback() {
            @Override
            public void onItemClick(BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean repository) {
                //PhotoView查看图片
                System.out.println("getImage0: " + repository.getImage0());

                startActivity(new Intent(mAct,
                        PhotoViewActivity.class).
                        putExtra("pic_url", repository.getImage0()));
            }
        });
        manager = new LinearLayoutManager(mAct);
        mainRv.setLayoutManager(manager);
        mainRv.setAdapter(ada);
    }

    /**
     * 处理单击,双击事件
     */
    private void initLoadMore() {
        loadMore(mainFloatview, manager, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                mianPre.loadSmileDatas(page);//下一页

            }

            @Override
            public void onLoadBack(int page) {
                mianPre.loadSmileDatas(page);//上一页
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
            case com.happysong.app.R.id.item_title_tv:
                startActivity(new Intent(mAct, EditTextActivity.class));
                break;
        }
    }

    /**
     * 1.在MainP里attachView方法对MainActivity进行了绑定,
     * 2.而MainPresenter 是处理网络请求和解析数据的,
     * 3.因此加载和处理完数据后,会在complete内调用mainMvpView.showVideoInfos(smileInfo),并传入数据
     * 注意:
     * MainPresenter的请求数据方法是在MainActivity调用的,
     * 也就是说该activity 有进行用户动作(点击,mianPre.loadBsbdqjInfo(currentPage)),
     * 而动作后的响应数据由MainPresenter处理,最后返回showSmileInfos给该act
     *
     * @param smileInfo
     */
    @Override
    public void showSmileInfos(BsbdqjInfo smileInfo) {
        Log.d("fragment_smileInfo", smileInfo.toString());
        SmileAdapter adapter = (SmileAdapter) mainRv.getAdapter();
        List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = smileInfo.getShowapi_res_body().getPagebean().getContentlist();
        adapter.setmSmileInfos(contentlist);
        adapter.notifyDataSetChanged();
        mainRv.requestFocus();
    }

    @Override
    protected void initArguments(Bundle arguments) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

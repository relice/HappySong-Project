package com.happysong.app.mainContent.videoModule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.happysong.app.bean.BsbdqjInfo;
import com.happysong.app.bean.RndomInfo;
import com.happysong.app.mainContent.smileModule.EditTextActivity;
import com.happysong.app.utils.gsyvideoplayer.adapter.ListNormalAdapter;
import com.happysong.app.utils.gsyvideoplayer.adapter.RecyclerBaseAdapter;
import com.happysong.app.mainContent.videoModule.presenter.VideoPresenter;
import com.happysong.app.mainContent.videoModule.view.VideoMvpView;
import com.happysong.app.ui.BaseFragment;
import com.happysong.app.utils.BmobUtils;
import com.happysong.app.R;

import java.util.ArrayList;
import java.util.List;

import com.happysong.app.mainContent.videoModule.view.VideoMvpView;
import butterknife.Bind;
import butterknife.ButterKnife;
import utils.ListVideoUtil;
import video.GSYVideoManager;
import video.GSYVideoPlayer;

/**
 * 视频页面
 */
@SuppressLint("ValidFragment")
public class VideoFragment extends BaseFragment implements View.OnClickListener, VideoMvpView {

    @Bind(com.happysong.app.R.id.list_item_recycler)
    RecyclerView listItemRecycler;
    @Bind(com.happysong.app.R.id.video_full_container)
    FrameLayout videoFullContainer;
    @Bind(com.happysong.app.R.id.video_floatview)
    FloatingActionButton videoFloatview;

    List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> dataList = new ArrayList<>();

    ListVideoUtil listVideoUtil;
    RecyclerBaseAdapter recyclerBaseAdapter;

    private String tag = "VideoFragment";
    int lastVisibleItem;
    int firstVisibleItem;
    private VideoPresenter mianPre;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(com.happysong.app.R.layout.activity_video, container, false);
        ButterKnife.bind(this, view);

        initView();
        //微笑内容列表
        initVideoList();
        //初始化加载更多
        initLoadMore();

        //更新Bmob网络数据,如果有新添加的参数就初始化下
//        initBmob();
        // 注册用户
//        BmobUtils.INSTANCE.regeistUser(mAct,"relice","123");
        return view;
    }

    private void initBmob() {
        RndomInfo addInfo = new RndomInfo();
        addInfo.setImg_type("ssr");
        addInfo.setIs_liked(false);
        addInfo.setImg_title("手机标题");
        addInfo.setImg_des("床前明月光,疑是地上霜");
        addInfo.setImg_id("11");
        addInfo.setImg_url("http://image.made-in-china.com/43f34j00DBATgYWCCJcU/MP4-Player-AP5002-.jpg");
        addInfo.setRelice_say("relice,美丽的是人");
        addInfo.setUser_comment("评论");
        addInfo.setUser_grade("10");
        BmobUtils.INSTANCE.saveRandomImg(mAct,addInfo);
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(mAct);
        listItemRecycler.setLayoutManager(linearLayoutManager);
        recyclerBaseAdapter = new RecyclerBaseAdapter(mAct, dataList);
        listItemRecycler.setAdapter(recyclerBaseAdapter);

        listVideoUtil = new ListVideoUtil(mAct);
        listVideoUtil.setFullViewContainer(videoFullContainer);
        listVideoUtil.setHideStatusBar(true);
        recyclerBaseAdapter.setListVideoUtil(listVideoUtil);
    }

    @Override
    protected void initData() {
        super.initData();
        //建立 presenter
        mianPre = new VideoPresenter();
        mianPre.attachView(this);
        mianPre.loadBsbdqjInfo(1);
    }

    @Override
    protected String handleTitle() {
        return getArguments().getString(BaseFragment.FRAGMENT_TITLE);
    }

    private void initVideoList() {
        listVideoUtil.setHideActionBar(true);

        listItemRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();

                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(ListNormalAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoPlayer.releaseAllVideos();
                        recyclerBaseAdapter.notifyDataSetChanged();
                    }
                } else {
                    // 滚动需要暂停视频
                    GSYVideoManager.onPause();
                }
            }
        });
    }

    /**
     * 处理加载更多
     */
    private void initLoadMore() {
        loadMore(videoFloatview, linearLayoutManager, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                mianPre.loadBsbdqjInfo(page);//下一页

            }

            @Override
            public void onLoadBack(int page) {
                mianPre.loadBsbdqjInfo(page);//上一页
                //  测试
                //  mAct.startActivity(new Intent(mAct, VideoMainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.happysong.app.R.id.item_title_tv:
                startActivity(new Intent(mAct, EditTextActivity.class));
                break;
        }
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

    @Override
    public void showVideoInfos(BsbdqjInfo info) {
        listVideoUtil.releaseVideoPlayer();
        listVideoUtil.smallVideoToNormal();
        GSYVideoPlayer.releaseAllVideos();

        List<BsbdqjInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = info.getShowapi_res_body().getPagebean().getContentlist();

        if (recyclerBaseAdapter != null)
            recyclerBaseAdapter.setListData(contentlist);
    }

    @Override
    protected void onBackPressed() {
        if (listVideoUtil.backFromFull()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            GSYVideoManager.onPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        listVideoUtil.releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();

        GSYVideoManager.clearAllDefaultCache(mAct);
    }
}

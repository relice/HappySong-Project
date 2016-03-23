package com.example.relicemxd.happysong.mainContent.smile_commonMVP;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.relicemxd.happysong.bean.SmileInfo;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.adapter.SmileListRecyclerViewAdapter;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.presenter.SmilePresenter;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.view.SmileMvpView;
import com.example.relicemxd.happysong.utils.SToast;
import com.example.relicemxd.happysong.utilsUI.BaseFragment;
import com.example.relicemxd.happysong.utilsUI.PhotoViewActivity;
import com.example.relicemxd.leanrrv.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by relicemxd on 16/3/11.
 * 笑话页面
 */
public class SmileFragment extends BaseFragment implements View.OnClickListener, SmileMvpView {

    @Bind(R.id.mian_rv)
    RecyclerView mainRv;
    @Bind(R.id.main_floatview)
    FloatingActionButton mainFloatview;
    private String title;
    private int currentPage;
    private SmilePresenter mianPre;

    public SmileFragment(String title) {
        this.title = title;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.smile_layout, container, false);
        ButterKnife.bind(this, view);

        //微笑内容列表
        initSmileList();

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
        //建立 presenter
        mianPre = new SmilePresenter();
        mianPre.attachView(this);
        mianPre.loadSmileDatas(currentPage);//默认加载第一页数据
    }

    private void initSmileList() {
        SmileListRecyclerViewAdapter ada =
                new SmileListRecyclerViewAdapter(getContext());
        //回调操作
        ada.setCallback(new SmileListRecyclerViewAdapter.Callback() {
            @Override
            public void onItemClick(SmileInfo.DetailEntity repository) {
                //PhotoView查看图片
                startActivity(new Intent(getContext(),
                        PhotoViewActivity.class).
                        putExtra("pic_url", repository.getPicUrl()));
            }
        });
        mainRv.setLayoutManager(new LinearLayoutManager(mAct));
        mainRv.setAdapter(ada);
    }

    /**
     * 处理单击,双击事件
     */
    private void handlerClickEvent() {
        mainFloatview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ++currentPage;
                mianPre.loadSmileDatas(currentPage);//下一页
            }
        });

        mainFloatview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                --currentPage;
                if (currentPage < 0) {
                    currentPage = 0;
                    SToast.s(getContext(), "已经是第一页了");
                    return true;
                }
                mianPre.loadSmileDatas(currentPage);//上一页
                return true;
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
            case R.id.item_title_tv:
                startActivity(new Intent(mAct, EditTextActivity.class));
                break;
        }
    }

    /**
     * 1.在MainP里attachView方法对MainActivity进行了绑定,
     * 2.而MainPresenter 是处理网络请求和解析数据的,
     * 3.因此加载和处理完数据后,会在complete内调用mainMvpView.showSmileInfos(smileInfo),并传入数据
     * 注意:
     * MainPresenter的请求数据方法是在MainActivity调用的,
     * 也就是说该activity 有进行用户动作(点击,mianPre.loadSmileDatas(currentPage)),
     * 而动作后的响应数据由MainPresenter处理,最后返回showSmileInfos给该act
     *
     * @param smileInfo
     */
    @Override
    public void showSmileInfos(SmileInfo smileInfo) {
        Log.d("main_act", smileInfo.toString());
        SmileListRecyclerViewAdapter adapter = (SmileListRecyclerViewAdapter) mainRv.getAdapter();
        adapter.setmSmileInfos(smileInfo.getDetail());
        adapter.notifyDataSetChanged();
        mainRv.requestFocus();
    }
}

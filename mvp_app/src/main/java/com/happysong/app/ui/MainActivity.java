package com.happysong.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.happysong.app.R;
import com.happysong.app.db.DBHelper;
import com.happysong.app.mainContent.duanziModule.contract.DZFragment;
import com.happysong.app.mainContent.duanziModule.contract.DZPresenter;
import com.happysong.app.mainContent.smileModule.SmileFragment;
import com.happysong.app.mainContent.videoModule.VideoFragment;
import com.happysong.app.utils.SToast;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 笑话首页act
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.mian_toolbar)
    Toolbar mianToolbar;
    @Bind(R.id.main_act_dl)
    DrawerLayout mainActDl;
    @Bind(R.id.main_sliding_tabs)
    TabLayout mainSlidingTabs;
    @Bind(R.id.main_vp)
    ViewPager mainVp;
    @Bind(R.id.nav_view)
    NavigationView navView;

    private ActionBarDrawerToggle mDrawerToggle;
    private MainTabAdapter tabAda;

    public static final String MAINTACT_PRESS_BACK = "maintact_press_back";
    private MainActivity mAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAct = MainActivity.this;
        initView();
    }

    private void initView() {
        //ToolBar需要先setSupportActionBar 才可以使用
        setSupportActionBar(mianToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TabLayout + ViewPager
        initTabContent();

        //DrawerLayou 抽屉菜单
        initMenu();
    }

    /**
     * 内容页面
     */
    private void initTabContent() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        SmileFragment smile = new SmileFragment();
        Bundle smileb = new Bundle(1);
        smileb.putString(BaseFragment.FRAGMENT_TITLE, "搞笑图");
        smile.setArguments(smileb);
        fragments.add(smile);

        //TODO google mvp的presenter是在activity中实例化,并顺带将view传到presenter内
        DZFragment dz = new DZFragment();
        Bundle dzb = new Bundle(1);
        dzb.putString(BaseFragment.FRAGMENT_TITLE, "段子手");
        dz.setArguments(dzb);
        new DZPresenter(dz);
        fragments.add(dz);

        VideoFragment video = new VideoFragment();
        Bundle videob = new Bundle(1);
        videob.putString(BaseFragment.FRAGMENT_TITLE, "逗视频");
        video.setArguments(videob);
        fragments.add(video);
//        fragments.add(PageFragment.newInstance("测试"));

        tabAda = new MainTabAdapter(getSupportFragmentManager(), this, fragments);
        mainVp.setAdapter(tabAda);
        //tabLayout绑定ViewPager
        mainSlidingTabs.setupWithViewPager(mainVp);
        //设置tabLayout的item填充
        mainSlidingTabs.setTabMode(TabLayout.MODE_FIXED);
    }

    /**
     * 侧拉菜单
     */
    private void initMenu() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mainActDl, mianToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mainActDl.setDrawerListener(mDrawerToggle);

        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_collect:
                    startActivity(new Intent(mAct, CollectActivity.class));
                    break;
//                case R.id.menu_search:
//                    SToast.s(mAct, "功能下个版本开发");
//                    break;
                case R.id.menu_youSar:
                    SToast.s(mAct, "功能下个版本开发");
                    break;
                case R.id.menu_heSay:
                    startActivity(new Intent(mAct, LoginDialogActivity.class));
                    break;
//                case R.id.menu_weAre:
//                    SToast.s(mAct, "功能下个版本开发");
//                    break;
            }
            return true;
        });


        //长按删除数据库的表
        View headerView = navView.getHeaderView(0);
        View headImg = headerView.findViewById(R.id.menu_head_iv);
        headImg.setOnLongClickListener(v -> {
            //删除表
            DBHelper dbHelper = new DBHelper(mAct);
            dbHelper.deleteRandomTable();
            SToast.l(mAct, "你已把除数据库删除...");
            return true;
        });

        View headLogin = headerView.findViewById(R.id.login_btn);
        headLogin.setOnClickListener(v -> {
            startActivity(new Intent(mAct, LoginActivity.class));
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RxBus.get().post(MAINTACT_PRESS_BACK, MAINTACT_PRESS_BACK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

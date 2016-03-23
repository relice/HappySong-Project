package com.example.relicemxd.happysong.main_googleRxbusMVP;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.contract.NewFragment;
import com.example.relicemxd.happysong.mainContent.new_googlebaseMVP.contract.NewPresenter;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.SmileFragment;
import com.example.relicemxd.happysong.utils.EasyCache;
import com.example.relicemxd.happysong.utils.SToast;
import com.example.relicemxd.happysong.utilsUI.BaseFragment;
import com.example.relicemxd.happysong.utilsUI.PageFragment;
import com.example.relicemxd.leanrrv.R;

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
    @Bind(R.id.main_left_lv)
    ListView leftDrawerLv;
    @Bind(R.id.main_sliding_tabs)
    TabLayout mainSlidingTabs;
    @Bind(R.id.main_vp)
    ViewPager mainVp;
    @Bind(R.id.main_left_ll)
    LinearLayout mainLeftLl;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] lvs = {"List Item 01", "List Item 02",
            "List Item 03", "List Item 04"};
    private MainTabAdapter tabAda;
    private EasyCache<String, String> cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //ToolBar需要先setSupportActionBar 才可以使用
        setSupportActionBar(mianToolbar);
//        mianToolbar.setNavigationIcon(R.drawable.zan_icon);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//隐藏ActionBar上的应用图标，只显示文字label
//        getActionBar().setDisplayShowHomeEnabled(false);

        //TabLayout + ViewPager
        initTabContent();

        //DrawerLayou 抽屉菜单
        initDrawerlayout();

        cache = new EasyCache<>(1024);
        cache.put("name", "Tom_Jack");
    }

    /**
     * 内容页面
     */
    private void initTabContent() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new SmileFragment("笑吧"));
        //google mvp的presenter是在activity中实例化,并顺带将view传到presenter内
        NewFragment newFragment = new NewFragment("新闻");
        new NewPresenter(newFragment);
        fragments.add(newFragment);

        fragments.add(PageFragment.newInstance(2));
        fragments.add(PageFragment.newInstance(3));

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
    private void initDrawerlayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mainActDl, mianToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mainActDl.setDrawerListener(mDrawerToggle);

        ArrayAdapter adapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        leftDrawerLv.setAdapter(adapter);
        leftDrawerLv.setOnItemClickListener((parent, view, position, id) -> SToast.s(MainActivity.this, "第:" + position + "====" + cache.get("name")));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

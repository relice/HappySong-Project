package com.happysong.app.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happysong.app.bean.RndomInfo;
import com.happysong.app.db.DBHelper;
import com.happysong.app.utils.BmobUtils;
import com.happysong.app.utils.Utils;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobObject;

import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

/**
 * Created by clevo on 2015/7/30.
 */
public class CollectDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout frontView, bottomView;
    public static final String INFO_OBJ_ID = "info_obj_id";
    private FloatingActionButton fab;
    private AnimatorSet showAnim, hiddenAnim;
    private long fWidth, fHeight, bHeight;
    private TextView tvCloseBottom;
    private String title[] = {"寄语", "诗语"};
    private ImageView colll_detail_iv;
    private RndomInfo rndomInfo;
    private TextView colle_detail_tv;
    private TextView colle_detail_comment;
    private TextView colle_detail_grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happysong.app.R.layout.collect_detail_activity);

        //toobar标题和返回设置
        Toolbar tb = (Toolbar) findViewById(com.happysong.app.R.id.mian_toolbar);
        tb.setTitle("照骗详情");
        tb.setNavigationIcon(com.happysong.app.R.drawable.ic_arrow_back_white);
        tb.setNavigationOnClickListener(v -> finish());

        String objid = getIntent().getStringExtra(INFO_OBJ_ID);
        if (!isNullOrEmpty(objid)) {
            DBHelper helper = new DBHelper(this);
            rndomInfo = helper.queryRandomByID(objid, helper.OBJ_ID);

            //更新标题
            tb.setTitle(Utils.getInstance().changeTitle(rndomInfo.getImg_type()));
        }


        //浮动按键
        fab = (FloatingActionButton) findViewById(com.happysong.app.R.id.fab);
        fab.setOnClickListener(this);

        //关闭键
        tvCloseBottom = (TextView) findViewById(com.happysong.app.R.id.tv_close_bottom);
        //吐槽一下
        colle_detail_tv = (TextView) findViewById(com.happysong.app.R.id.colle_detail_tv);
        //评论
        colle_detail_comment = (TextView) findViewById(com.happysong.app.R.id.colle_detail_comment);
        //评分
        colle_detail_grade = (TextView) findViewById(com.happysong.app.R.id.colle_detail_grade);
        tvCloseBottom.setOnClickListener(this);
        colle_detail_tv.setOnClickListener(this);
        colle_detail_comment.setOnClickListener(this);
        colle_detail_grade.setOnClickListener(this);

        ViewPager viewPager = (ViewPager) findViewById(com.happysong.app.R.id.view_pager_detail);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //滑动layout
        TabLayout tabLayout = (TabLayout) findViewById(com.happysong.app.R.id.tab_layout);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initView();
        initData();
    }

    private void initView() {
        frontView = (LinearLayout) findViewById(com.happysong.app.R.id.front);
        colll_detail_iv = (ImageView) findViewById(com.happysong.app.R.id.colll_detail_iv);
        ViewTreeObserver vto = frontView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fWidth = frontView.getMeasuredWidth();
                fHeight = frontView.getMeasuredHeight();
            }
        });
        bottomView = (LinearLayout) findViewById(com.happysong.app.R.id.bottom);
        ViewTreeObserver sVto = bottomView.getViewTreeObserver();
        sVto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bHeight = bottomView.getMeasuredHeight();
                initShowAnim();
                initHiddenAnim();
            }
        });
    }


    private void initData() {
        if (rndomInfo != null) {
            Picasso.with(this)//创建picasso实例
                    .load(rndomInfo.getImg_url())//加载图片地址
                    .placeholder(com.happysong.app.R.drawable.image_loading)//加载中
                    .into(colll_detail_iv);//要显示的ImageView
        }


        BmobUtils.INSTANCE.queryRandomInfo(this, rndomInfo.getObjectId(), new BmobUtils.OnBombQueryListner() {
            @Override
            public void onSusses(BmobObject obj) {
                RndomInfo queryInfo = (RndomInfo) obj;
                String user_comment = queryInfo.getUser_comment();
                String user_grade = queryInfo.getUser_grade();
                if (!isNullOrEmpty(user_comment)) {
                    colle_detail_comment.setText("评论: " + user_comment);
                }
                if (!isNullOrEmpty(user_grade)) {
                    colle_detail_grade.setText("评分: " + user_grade);
                }
            }

            @Override
            public void onError(String e) {
                Log.d("CommentDialogActivity", "查询失败:" + e);
            }
        });
    }

    private void initShowAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(frontView, "scaleX", 1.0f, 0.8f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(frontView, "scaleY", 1.0f, 0.8f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(frontView, "alpha", 1.0f, 0.5f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(frontView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(frontView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(frontView, "translationY", 0, -0.1f * fHeight);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(bottomView, "translationY", bHeight, 0);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                bottomView.setVisibility(View.VISIBLE);
            }
        });
        showAnim = new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim, fViewRotationXAnim, fViewResumeAnim, fViewTransYAnim, fViewAlphaAnim, fViewScaleYAnim, sViewTransYAnim);
    }

    private void initHiddenAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(frontView, "scaleX", 0.8f, 1.0f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(frontView, "scaleY", 0.8f, 1.0f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(frontView, "alpha", 0.5f, 1.0f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationAnim = ObjectAnimator.ofFloat(frontView, "rotationX", 0f, 10f);
        fViewRotationAnim.setDuration(150);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(frontView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(200);
        fViewResumeAnim.setStartDelay(150);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(frontView, "translationY", -fHeight * 0.1f, 0);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(bottomView, "translationY", 0, bHeight);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bottomView.setVisibility(View.INVISIBLE);
            }
        });
        hiddenAnim = new AnimatorSet();
        hiddenAnim.playTogether(fViewScaleXAnim, fViewAlphaAnim, fViewRotationAnim, fViewResumeAnim, fViewScaleYAnim, fViewTransYAnim, sViewTransYAnim);
        hiddenAnim.setDuration(350);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.happysong.app.R.id.fab:
                showAnim.start();
                break;
            case com.happysong.app.R.id.tv_close_bottom:
                hiddenAnim.start();
                break;
            case com.happysong.app.R.id.colle_detail_tv:
                //吐槽一下
                startActivity(new Intent(this, CommentDialogActivity.class)
                        .putExtra(CommentDialogActivity.RANDOM_INFO_ID, rndomInfo.getObjectId())
                        .putExtra(CommentDialogActivity.RANDOM_INFO_TITLE, "吐槽一下")
                );
                break;
//            case R.id.colle_detail_grade:
//                SToast.s(this, "评分");
//                break;
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {


        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageFragment.newInstance(rndomInfo.getRelice_say());
                case 1:
                    return PageFragment.newInstance(rndomInfo.getImg_des());
                default:
                    return PageFragment.newInstance(title[0]);
            }
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return title[0];
                case 1:
                    return title[1];
                default:
                    return title[0];
            }
        }
    }
}

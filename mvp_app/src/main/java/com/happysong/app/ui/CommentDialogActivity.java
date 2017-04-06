package com.happysong.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.happysong.app.bean.RndomInfo;
import com.happysong.app.utils.BmobUtils;
import com.happysong.app.utils.SToast;
import com.happysong.app.utils.Utils;

import cn.bmob.v3.BmobObject;

import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

/**
 * 评分
 */
public class CommentDialogActivity extends Activity implements OnClickListener {
    private Activity mAct;
    // 确定
    private TextView bnConfirm;
    // 评论
    private EditText comment_et;
    // 悄悄离开
    private TextView login_back_tv;
    private TextView title_tv;
    public static final String RANDOM_INFO_ID = "RANDOM_INFO_ID";
    public static final String RANDOM_INFO_TITLE = "RANDOM_INFO_TITLE";
    private String infoID;
    private String infoTitle;
    private Spinner grade_spinner;
    private String selectedGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 点击dilog之外不消失
        setFinishOnTouchOutside(false);
        setContentView(com.happysong.app.R.layout.comment_dlg_activity);
        this.mAct = this;
        intData();
        initView();
    }

    private void intData() {
        infoID = getIntent().getStringExtra(RANDOM_INFO_ID);
        infoTitle = getIntent().getStringExtra(RANDOM_INFO_TITLE);

        BmobUtils.INSTANCE.queryRandomInfo(mAct, infoID, new BmobUtils.OnBombQueryListner() {
            @Override
            public void onSusses(BmobObject obj) {
                RndomInfo queryInfo = (RndomInfo) obj;
                String user_comment = queryInfo.getUser_comment();
                if (!isNullOrEmpty(user_comment)) {
                    comment_et.setText(user_comment);
                }
            }

            @Override
            public void onError(String e) {
                SToast.l(mAct, "获取数据失败:" + e);
            }
        });
    }

    private void initView() {
        //  评论
        comment_et = (EditText) findViewById(com.happysong.app.R.id.login_username_et);
        //设置EditText的显示方式为多行文本输入
        comment_et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        comment_et.setGravity(Gravity.TOP);
        //改变默认的单行模式
        comment_et.setSingleLine(false);
        //水平滚动设置为False
        comment_et.setHorizontallyScrolling(false);

        // 下拉框
        grade_spinner = (Spinner) findViewById(com.happysong.app.R.id.grade_spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, com.happysong.app.R.array.grade, android.R.layout.simple_spinner_item);
        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade_spinner.setAdapter(adapter);
        grade_spinner.setPrompt("评分");
        grade_spinner.setOnItemSelectedListener(new spinnerListener());


        // 退出设置
        login_back_tv = (TextView) findViewById(com.happysong.app.R.id.login_back_tv);
        login_back_tv.setVisibility(View.VISIBLE);
        login_back_tv.setOnClickListener(this);

        //标题
        title_tv = (TextView) findViewById(com.happysong.app.R.id.title_tv);
        if (!isNullOrEmpty(infoTitle)) {
            title_tv.setText(infoTitle);
        }

        // 提交数据的回调
        bnConfirm = (TextView) findViewById(com.happysong.app.R.id.login_btn);
        bnConfirm.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 使dilog的activity点击窗体之外不消失本身
        View v = findViewById(com.happysong.app.R.id.rootView);
        int height = v.getTop();
        v.setFocusable(true);
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                //不finish
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case com.happysong.app.R.id.login_btn:
                setButtonOnclick(v);
                break;
            // 悄悄离开
            case com.happysong.app.R.id.login_back_tv:
                finish();
                break;
        }
    }


    // 设置保存按钮点击事件
    private void setButtonOnclick(View v) {
        if (Utils.checkNetworkInfo(mAct)) {
            String comment = comment_et.getText().toString();

            if (isNullOrEmpty(comment)) {
                SToast.l(mAct, "内容不能为空");
            } else {
                BmobUtils.INSTANCE.queryRandomInfo(mAct, infoID, new BmobUtils.OnBombQueryListner() {
                    @Override
                    public void onSusses(BmobObject obj) {
                        RndomInfo queryInfo = (RndomInfo) obj;
                        queryInfo.setUser_comment(comment);
                        queryInfo.setUser_grade(selectedGrade);
                        queryInfo.update(mAct);

                        SToast.l(mAct, "吐槽成功");
                        CommentDialogActivity.this.finish();
                    }

                    @Override
                    public void onError(String e) {
                        SToast.l(mAct, "评论失败:" + e);
                    }
                });
            }
        } else {
            SToast.l(mAct, "网络已断开，请稍后再试");
        }
    }

    class spinnerListener implements android.widget.AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            //将选择的元素显示出来
            selectedGrade = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            System.out.println("nothingSelect");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

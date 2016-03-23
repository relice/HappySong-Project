package com.example.relicemxd.happysong.smile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.example.relicemxd.leanrrv.R;

/**
 * @author Relice
 *         0.身份证私密隐藏,如: 445955*******4444654
 *         1.已存在身份证号码时: 1.1.隐藏第7-14位置的身份证号码 1.2.删除键为全部删除，不能删除单个号码。
 *         2.编辑身份证号码时：弹出数字键盘，隐藏输入第7-14位置的身份证号码 3.身份证号码为空时： 输入框默认提示语为“请输入真实身份证号码”
 */
public class IDNumberView extends EditText implements
        OnFocusChangeListener {


    //真实字符
    private String realStr = "";
    //右边del图
    private Drawable mClearDrawable;

    public IDNumberView(Context context) {
        this(context, null);
    }

    public IDNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public IDNumberView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPadding(0, 0, 25, 0);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.mipmap.s_login_del_all_btn);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());

        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(new MyTextWatcher());
        // 监听键盘删除键
//        setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//                    IdNumberEDT.this.getText().delete(getSelectionStart() - 1, getSelectionStart());
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    class MyTextWatcher implements TextWatcher {
        //显示在editext上的私密字符串
        private String displayStr = "";

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //这个判断一定要写不然要陷入死循环
            // （et.setText会触发onTextChanged我又在onTextChanged里使用了setText方法）
            if (s.toString().trim().equals(displayStr.trim())) {
                System.out.println("重复");
                return;
            }
            System.out.println(IDNumberView.this.getSelectionStart() + "++++++++++" + IDNumberView.this.getSelectionEnd());

            System.out.println("start=" + start + " before=" + before + "  count=" + count);
            System.out.println("s=" + s);
            if (before > 0) {//before有变动,控制删除字符
                String delStr = realStr.substring(start, start + before);
                System.out.println("删除字符=" + delStr);
                String result = realStr.substring(0, start) + realStr.substring(start + before);
                System.out.println("删除后result=" + result);
                realStr = result;
            }
            if (count > 0) {//count有变动,控制添加字符
                CharSequence tmp = s.subSequence(start, start + count);
                System.out.println("增加的sub str =" + tmp);
                StringBuilder sb = new StringBuilder(realStr);
                sb.insert(start, tmp);

                realStr = sb.toString();
                //realStr += tmp;
            }

            System.out.println("realStr= " + realStr + "---length=" + realStr.length());
            relItf.showRelContent(realStr);//监听回调

            displayStr = getDisplayStr(realStr);//控制显示*内容
            String old = IDNumberView.this.getText().toString().trim();
            System.out.println("old =" + old + " displayStr = " + displayStr);
            IDNumberView.this.setText(displayStr);

            IDNumberView.this.setSelection(displayStr.length());
            System.out.println("displayStr = " + displayStr);
            System.out.println("------------------------------------------");
        }

        /**
         * 获取用于显示的带星花的字符，不提交后台
         */
        private String getDisplayStr(String realStr) {
            String result = new String(realStr);
            char[] cs = result.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                if (i >= 6 && i <= 13) {//把7和14区间的字符隐藏
                    cs[i] = '*';
                }
            }
            return new String(cs);
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            setClearIconVisible(s.length() > 0);
        }
    }


    public void setClearIconVisible(boolean visible) {
        // 如果你想让它一直显示DrawableRight的图标，并且还需要让它触发事件，直接把null改成mClearDrawable即可
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public RelContentInterFace relItf;

    public interface RelContentInterFace {
        void showRelContent(String relContent);
    }

    public void setOnRelContentListener(RelContentInterFace relItf) {
        this.relItf = relItf;
    }
}

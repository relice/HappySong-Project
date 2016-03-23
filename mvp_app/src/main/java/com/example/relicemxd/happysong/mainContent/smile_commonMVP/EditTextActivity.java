package com.example.relicemxd.happysong.mainContent.smile_commonMVP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.relicemxd.happysong.mainContent.smile_commonMVP.adapter.IDNumberView;
import com.example.relicemxd.happysong.mainContent.smile_commonMVP.view.EditTextMvpView;
import com.example.relicemxd.happysong.main_googleRxbusMVP.MainActivity;
import com.example.relicemxd.leanrrv.R;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 编辑首页act
 */
public class EditTextActivity extends Activity implements EditTextMvpView {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.idnum_et)
    IDNumberView idnumEt;
    @Bind(R.id.content_tv)
    TextView contentTv;
    private WeakRHandler myHD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edittextact);
        ButterKnife.bind(this);

        showInputContent();

        //外部给handler this引用
        myHD = new WeakRHandler(this);
    }


    @Override
    public void showEditInfo(String str) {
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void showInputContent() {
        idnumEt.setOnRelContentListener(new IDNumberView.RelContentInterFace() {
            @Override
            public void showRelContent(String relContent) {
                if (relContent != null) {
                    contentTv.setText(relContent);

                    //测试内容
                    Message msg = new Message();
                    msg.obj = relContent;
                    myHD.sendMessage(msg);
                }
            }
        });
    }

    /**
     * //测试内容:
     * <p/>
     * 内部类持有外部类Activity的引用，当Handler对象有Message在排队，
     * 则无法释放，进而导致Activity对象不能释放。
     * 1. 如果是声明为static，则该内部类不持有外部Acitivity的引用，
     * 则不会阻塞Activity对象的释放。
     * 2. 如果声明为static后，可在其内部声明一个弱引用（WeakReference）
     * 引用外部类。
     */
    static class WeakRHandler extends Handler {
        // 内部声明一个弱引用，引用外部类
        private WeakReference<MainActivity> activityWeakReference;

        public WeakRHandler(Activity activity) {
            activityWeakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                String str = (String) msg.obj;
                Log.v("WRef_Handler: ", str);
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHD.removeCallbacksAndMessages(null);
    }
}
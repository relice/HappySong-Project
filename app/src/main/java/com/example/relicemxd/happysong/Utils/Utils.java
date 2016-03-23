package com.example.relicemxd.happysong.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * Created by relicemxd on 16/3/9.
 */
public class Utils {

    private static Utils mUtils;
    private static Context mContext;

    private Utils() {
    }

    public static Utils getInstance() {
        if (mUtils == null) {
            mUtils = new Utils();
        }
        return mUtils;
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public int getWidth(Context context) {
        mContext = context;
        if (mContext != null) {
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity) mContext).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(metric);
            return metric.widthPixels; // 屏幕宽度（像素）
        }
        return 0;
    }

    /**
     * @Description: 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @Description: 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void SToast(Context cotext) {
        Toast.makeText(cotext, "已经是第一页了", Toast.LENGTH_SHORT).show();
    }
}

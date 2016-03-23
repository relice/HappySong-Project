package com.example.relicemxd.happysong.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by relicemxd on 16/3/9.
 */
public class SToast {

    private static SToast sToast;
    private static Context mContext;

    private SToast() {
    }

    public static SToast getInstance() {
        if (sToast == null) {
            sToast = new SToast();
        }
        return sToast;
    }

    public static void s(Context cotext, String content) {
        Toast.makeText(cotext, content, Toast.LENGTH_SHORT).show();
    }

    public static void l(Context cotext, String content) {
        Toast.makeText(cotext, content, Toast.LENGTH_LONG).show();
    }


}

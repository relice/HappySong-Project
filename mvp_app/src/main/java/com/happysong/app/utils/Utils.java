package com.happysong.app.utils;
//com.happysong.app


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.happysong.app.bean.RndomInfo;
import com.happysong.app.db.DBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import app.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import app.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import static com.happysong.app.utils.Preconditions.isNullOrEmpty;

public class Utils {
    public static final int RANDOM_NUM = 45;
    private static Utils mUtils;

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
        if (context != null) {
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity) context).getWindowManager()
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

    public static void toast(Context cotext, String content) {
        Toast.makeText(cotext, content, Toast.LENGTH_SHORT).show();
    }


    public String format(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 解决fresco oom问题
     *
     * @param url
     * @param draweeView
     */
    public void showThumb(Context cont, String url, SimpleDraweeView draweeView, boolean isAnima) {
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(Utils.dp2px(cont, 144), Utils.dp2px(cont, 144)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setAutoPlayAnimations(isAnima) // 设置加载图片完成后是否直接进行播放
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        draweeView.setController(controller);
    }


    /**
     * 获取视频缩略图
     *
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public Bitmap createVideoThumbnail(String url, String width, String height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, Integer.parseInt(width), Integer.parseInt(height),
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    /**
     * 随机弹窗显示后台图片
     *
     * @param act
     */
    public void showRandomPOP(Activity act) {
        //TODO 随机查询后台的图片
        Random random = new Random();
        BmobQuery<RndomInfo> bmobQuery = new BmobQuery<>();
        //按照时间降序
        bmobQuery.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        bmobQuery.findObjects(act, new FindListener<RndomInfo>() {
            @Override
            public void onSuccess(List<RndomInfo> list) {
                if (!Preconditions.isNullOrEmpty(list)) {
                    int nextInt = random.nextInt(list.size());
                    RndomInfo info = list.get(nextInt);
                    String objID = info.getObjectId();

                    Log.d("Utils:", "查询成功objectId为：" + objID);
                    showPOP(act, info);
                }else{
                    Log.d("Utils:", "查询不到数据");
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.d("Utils:", "查询失败：" + s);
            }
        });
    }


    /**
     * 弹窗控件
     *
     * @param act
     */
    public void showPOP(Activity act, RndomInfo info) {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(act);

        dialogBuilder
                .withTitle(changeTitle(info.getImg_type()))
                .withMessage(info.getRelice_say())
                .withMessageImg(info.getImg_url())
                .withEffect(Effectstype.Newspager)
                .withButton1Text("收藏")
                .withButton2Text("取消")
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper helper = new DBHelper(act);

                        //判断数据库是否有该数据
                        RndomInfo infoID = helper.queryRandomByID(info.getImg_id(), helper.IMG_ID);
                        if (infoID.getImg_id() == null) {
                            helper.saveRandomImg(info);
                            //刷新服务器数据
                            infoID.setIs_liked(true);
                            infoID.update(act);

                            SToast.s(v.getContext(), "收藏成功");
                            dialogBuilder.dismiss();
                        } else if (infoID.getImg_id().equals(info.getImg_id())) {
                            SToast.s(v.getContext(), "该图片已经收藏过");
                        } else {
                            helper.saveRandomImg(info);
                            infoID.setIs_liked(true);
                            infoID.update(act);

                            SToast.s(v.getContext(), "收藏成功");
                            dialogBuilder.dismiss();
                        }

                        //查询数据库
                        List<RndomInfo> rndomInfos = helper.queryRandomList();
                        if (!isNullOrEmpty(rndomInfos)) {
                            System.out.println("Utils_Database:" + rndomInfos.get(0).toString() + " size:" + rndomInfos.size());
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    public String changeTitle(String img_type) {
        if (isNullOrEmpty(img_type)) {
            return "";
        } else if (img_type.equals("ssr")) {
            return "SSR卡：" + "执子之手,与子偕老";
        } else if (img_type.equals("sr")) {
            return "SR卡：" + "人之相知,贵在知心";
        } else if (img_type.equals("r")) {
            return "R卡：" + "地生连理枝,水出并头莲";
        } else if (img_type.equals("n")) {
            return "N卡：" + "月上柳梢头,人约黄昏后";
        } else {
            return "";
        }
    }

    /**
     * @param prizeRate 中奖率
     */
    public void setRandom(int prizeRate, RandomListner listner) {
        int random = new Random().nextInt(100);
        if (random < prizeRate) {
            listner.onRandomRunListner(random);
        }
    }

    public interface RandomListner {
        void onRandomRunListner(int random);
    }

    /**
     * 获取当前网络连接状态
     *
     * @param context
     * @return boolean
     */
    public static boolean checkNetworkInfo(Context context) {
        try {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo mobileNetInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo.State mobile = null, wifi = null;
            if (mobileNetInfo != null)
                // 获取3G网络状态
                mobile = mobileNetInfo.getState();
            if (wifiNetInfo != null)
                // 获取wifi网络状态
                wifi = wifiNetInfo.getState();

            // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
            if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
                return true;
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
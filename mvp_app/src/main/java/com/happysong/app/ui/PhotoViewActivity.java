package com.happysong.app.ui;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.happysong.app.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 该activity只是一个显示图片的activity,不必使用mvp
 * 查看图片act
 */
public class PhotoViewActivity extends BaseActivity {

    SubsamplingScaleImageView photoIv;
    SimpleDraweeView faceImg;
    Toolbar tb;


    @Override
    protected void initView() {
        super.initView();
        String pic_url = getIntent().getStringExtra("pic_url");

        photoIv = (SubsamplingScaleImageView) findViewById(com.happysong.app.R.id.photo_iv);
        faceImg = (SimpleDraweeView) findViewById(com.happysong.app.R.id.face_img);

        if (pic_url != null && !TextUtils.isEmpty(pic_url)) {
            if (pic_url.endsWith("gif")) {
                photoIv.setVisibility(View.GONE);
                faceImg.setVisibility(View.VISIBLE);

                //大图支持gif
                //Glide 加载gif有问题

                Utils.getInstance().showThumb(this, pic_url, faceImg, true);
                faceImg.setOnClickListener(view -> PhotoViewActivity.this.finish());
            } else {
                photoIv.setVisibility(View.VISIBLE);
                faceImg.setVisibility(View.GONE);
                //Picasso加载比较长图的图片加载不出

                photoIv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                photoIv.setMinScale(1.0F);
                final String downPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Glide";
                String img_file_name = pic_url.substring(pic_url.lastIndexOf('/') + 1);

                //使用Glide下载图片,保存到本地
                Glide.with(this)
                        .load(pic_url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                File file = new File(downPath);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                //生成img图片
                                File imgfile = new File(downPath, img_file_name);

                                FileOutputStream fout = null;
                                try {
                                    //保存图片
                                    fout = new FileOutputStream(imgfile);
                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                                    // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
                                    photoIv.setImage(ImageSource.uri(imgfile.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (fout != null) fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                //Glide可以加载长图但是对gif图片加载有缺陷,偶现加载不出
                //PhotoView控件支持放大缩小
                photoIv.setOnClickListener(v -> PhotoViewActivity.this.finish());
            }
        }
    }

    @Override
    protected View getBackImg() {
        return null;
    }

    @Override
    protected void handleOnDestroy() {

    }

    @Override
    protected void handleOnStop() {

    }

    @Override
    protected void handleOnResume() {

    }

    @Override
    protected int getContentView() {
        return com.happysong.app.R.layout.photoview_layout;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
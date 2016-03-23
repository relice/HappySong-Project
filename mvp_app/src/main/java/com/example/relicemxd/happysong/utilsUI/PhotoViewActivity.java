package com.example.relicemxd.happysong.utilsUI;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.relicemxd.leanrrv.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 该activity只是一个显示图片的activity,不必使用mvp
 * 查看图片act
 */
public class PhotoViewActivity extends Activity {

    @Bind(R.id.photo_iv)
    PhotoView photoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoview_layout);
        ButterKnife.bind(this);

        String pic_url = getIntent().getStringExtra("pic_url");
        if (pic_url != null && !TextUtils.isEmpty(pic_url)) {
            Picasso
                    .with(this)
                    .load(pic_url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(photoIv);

            //PhotoView控件支持放大缩小
//            new PhotoViewAttacher(photoIv).update();
        }

        //注意:photoView的不可以直接setOnclicListnter,他注册了自己的监听
        photoIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                PhotoViewActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
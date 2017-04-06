package com.happysong.app.utils.gsyvideoplayer.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;
import com.happysong.app.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import com.happysong.app.utils.gsyvideoplayer.model.VideoModel;

public class ImgVideoAdapter extends RecyclerView.Adapter<ImgVideoAdapter.ViewHolder> {

    private List<VideoModel> mImages;
    private Context mContext;
    private LayoutInflater mInflater;

    private Bitmap mBitmap; //

    private LruCache<String, BitmapDrawable> mMemoryCache;//

    public ImgVideoAdapter(Context context, List<VideoModel> imagesUrls) {
        this.mContext = context;
        mImages = imagesUrls;
        mInflater = LayoutInflater.from(context);
        //默认显示的图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), com.happysong.app.R.drawable.loading_anima);
        //计算内存，并且给Lrucache 设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 6;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }


    @Override
    public ImgVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(com.happysong.app.R.layout.list_video_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView = (ImageView) view.findViewById(com.happysong.app.R.id.list_item_btn);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImgVideoAdapter.ViewHolder holder, int position) {


        String imageUrl = mImages.get(position).getVideo_uri();
        BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(imageUrl);
        if (drawable != null) {
            holder.imageView.setImageDrawable(drawable);
        } else if (cancelPotentialTask(imageUrl, holder.imageView)) {
            //执行下载操作
            DownLoadTask task = new DownLoadTask(holder.imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mBitmap, task);
            holder.imageView.setImageDrawable(asyncDrawable);
            task.execute(imageUrl);
        }
    }

    /**
     * 检查复用的ImageView中是否存在其他图片的下载任务，如果存在就取消并且返回ture 否则返回 false
     *
     * @param imageUrl
     * @param imageView
     * @return
     */
    private boolean cancelPotentialTask(String imageUrl, ImageView imageView) {
        DownLoadTask task = getDownLoadTask(imageView);
        if (task != null) {
            String url = task.url;
            if (url == null || !url.equals(imageUrl)) {
                task.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 從缓存中获取已存在的图片
     *
     * @param imageUrl
     * @return
     */
    private BitmapDrawable getBitmapDrawableFromMemoryCache(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }

    /**
     * 添加图片到缓存中
     *
     * @param imageUrl
     * @param drawable
     */
    private void addBitmapDrawableToMemoryCache(String imageUrl, BitmapDrawable drawable) {
        if (getBitmapDrawableFromMemoryCache(imageUrl) == null) {
            mMemoryCache.put(imageUrl, drawable);
        }
    }

    /**
     * 获取当前ImageView 的图片下载任务
     *
     * @param imageView
     * @return
     */
    private DownLoadTask getDownLoadTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                return ((AsyncDrawable) drawable).getDownLoadTaskFromAsyncDrawable();
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 新建一个类 继承BitmapDrawable
     * 目的： BitmapDrawable 和DownLoadTask建立弱引用关联
     */
    class AsyncDrawable extends BitmapDrawable {
        private WeakReference<DownLoadTask> downLoadTaskWeakReference;

        public AsyncDrawable(Resources resources, Bitmap bitmap, DownLoadTask downLoadTask) {
            super(resources, bitmap);
            downLoadTaskWeakReference = new WeakReference<DownLoadTask>(downLoadTask);
        }

        private DownLoadTask getDownLoadTaskFromAsyncDrawable() {
            return downLoadTaskWeakReference.get();
        }
    }

    /**
     * 异步加载图片
     * DownLoadTash 和 ImagaeView建立弱引用关联。
     */
    class DownLoadTask extends AsyncTask<String, Void, BitmapDrawable> {
        String url;
        private WeakReference<ImageView> imageViewWeakReference;

        public DownLoadTask(ImageView imageView) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = downLoadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
            addBitmapDrawableToMemoryCache(url, drawable);
            return drawable;
        }

        /**
         * 验证ImageView 中的下载任务是否相同 如果相同就返回
         *
         * @return
         */
        private ImageView getAttachedImageView() {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null) {
                DownLoadTask task = getDownLoadTask(imageView);
                if (this == task) {
                    return imageView;
                }
            }
            return null;
        }

        /**
         * 下载图片 这里使用google 推荐使用的OkHttp
         *
         * @param url
         * @return
         */
        private Bitmap downLoadBitmap(String url) {
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);
            ImageView imageView = getAttachedImageView();
            if (imageView != null && drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        }


    }

}
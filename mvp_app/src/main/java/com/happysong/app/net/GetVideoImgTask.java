package com.happysong.app.net;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.happysong.app.utils.Utils;
import com.happysong.app.utils.Utils;

public class GetVideoImgTask extends
        AsyncTask<String, Integer, Bitmap> {
    private ITaskCallbackListener taskListener;

    /**
     * @param taskListener
     */
    public GetVideoImgTask(
            ITaskCallbackListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // 执行请求
        return Utils.getInstance().createVideoThumbnail(params[0], params[1], params[2]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // 执行结果回调函数
        taskListener.doTaskComplete(result);
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

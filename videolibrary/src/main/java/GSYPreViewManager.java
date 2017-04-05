import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;

import java.io.IOException;
import java.util.Map;

import model.GSYModel;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by shuyu on 2016/12/11.
 */

public class GSYPreViewManager implements IMediaPlayer.OnPreparedListener, IjkMediaPlayer.OnSeekCompleteListener {

    public static String TAG = "GSYPreViewManager";

    private static GSYPreViewManager videoManager;

    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_SETDISPLAY = 1;
    public static final int HANDLER_RELEASE = 2;

    private IjkMediaPlayer mediaPlayer;
    private HandlerThread mMediaHandlerThread;
    private GSYPreViewManager.MediaHandler mMediaHandler;

    private boolean seekToComplete = true;

    public static synchronized GSYPreViewManager instance() {
        if (videoManager == null) {
            videoManager = new GSYPreViewManager();
        }
        return videoManager;
    }

    public GSYPreViewManager() {
        mediaPlayer = new IjkMediaPlayer();
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new GSYPreViewManager.MediaHandler((mMediaHandlerThread.getLooper()));
    }

    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    initVideo(msg);
                    break;
                case HANDLER_SETDISPLAY:
                    showDisplay(msg);
                    break;
                case HANDLER_RELEASE:
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    break;
            }
        }

    }

    private void initVideo(Message msg) {
        try {
            mediaPlayer.release();

            initIJKPlayer(msg);

            mediaPlayer.setOnPreparedListener(GSYPreViewManager.this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setVolume(0, 0);
            mediaPlayer.prepareAsync();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initIJKPlayer(Message msg) {
        mediaPlayer = new IjkMediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(((GSYModel) msg.obj).getUrl(), ((GSYModel) msg.obj).getMapHeadData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDisplay(Message msg) {
        if (msg.obj == null && mediaPlayer != null) {
            mediaPlayer.setSurface(null);
        } else {
            Surface holder = (Surface) msg.obj;
            if (mediaPlayer != null && holder.isValid()) {
                mediaPlayer.setSurface(holder);
            }
        }
    }


    @Override
    public void onPrepared(IMediaPlayer mp) {
        mp.pause();
        seekToComplete = true;
    }

    @Override
    public void onSeekComplete(IMediaPlayer mp) {
        seekToComplete = true;
    }

    public void prepare(final String url, final Map<String, String> mapHeadData, boolean loop, float speed) {
        if (TextUtils.isEmpty(url)) return;
        Message msg = new Message();
        msg.what = HANDLER_PREPARE;
        GSYModel fb = new GSYModel(url, mapHeadData, loop, speed);
        msg.obj = fb;
        mMediaHandler.sendMessage(msg);
    }

    public void releaseMediaPlayer() {
        Message msg = new Message();
        msg.what = HANDLER_RELEASE;
        mMediaHandler.sendMessage(msg);
    }

    public void setDisplay(Surface holder) {
        Message msg = new Message();
        msg.what = HANDLER_SETDISPLAY;
        msg.obj = holder;
        mMediaHandler.sendMessage(msg);
    }

    public IjkMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isSeekToComplete() {
        return seekToComplete;
    }

    public void setSeekToComplete(boolean seekToComplete) {
        this.seekToComplete = seekToComplete;
    }
}
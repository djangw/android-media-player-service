package com.example.mediaplayerservice;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;

public class MediaPlayerHost {
    private static final String TAG = "MediaPlayer";

    private MediaPlayer mMediaPlayer = null;

    public MediaPlayerHost(Context context) {
        mMediaPlayer = new MediaPlayer();

        try {
            final String videoUri = "http://html5demos.com/assets/dizzy.mp4";
            mMediaPlayer.setDataSource(videoUri);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void setSurface(Surface surface) {
        mMediaPlayer.setSurface(surface);
    }

    public void start() {
        try {
            mMediaPlayer.prepare();
        } catch (IOException t) {
            Log.e(TAG, "media player prepare failed");
        }
        mMediaPlayer.start();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void onPause() {
        stop();
    }

    public void onResume() {
        start();
    }
}

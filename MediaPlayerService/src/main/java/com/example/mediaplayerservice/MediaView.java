package com.example.mediaplayerservice;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MediaView extends GLSurfaceView {
    private MediaRenderer mRenderer;

    public MediaView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        mRenderer = new MediaRenderer(context);
        setRenderer(mRenderer);
    }

    public void setSurface(MediaSurface surface) {
        mRenderer.setSurface(surface);
    }
}

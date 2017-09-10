package com.example.mediaplayerservice;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.Surface;

public class MediaSurface implements SurfaceTexture.OnFrameAvailableListener {
    public interface OnCreateSurfaceTextureListener {
        void onCreateSurfaceTexture(Surface surface);
    }

    public static int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

    private GLSurfaceView mContext;
    private OnCreateSurfaceTextureListener mOnCreateSurfaceTextureListener;
    private int mTextureName = 0;
    private SurfaceTexture mSurfaceTexture = null;

    public MediaSurface(GLSurfaceView context,
                        OnCreateSurfaceTextureListener listener) {
        mContext = context;
        mOnCreateSurfaceTextureListener = listener;
    }

    private void createSurfaceTexture() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);

        mTextureName = textures[0];
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, mTextureName);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        mSurfaceTexture = new SurfaceTexture(mTextureName);
        final Surface surface = new Surface(mSurfaceTexture);
        mContext.post(new Runnable() {
            @Override
            public void run() {
                mOnCreateSurfaceTextureListener.onCreateSurfaceTexture(surface);
            }
        });
    }

    public void updateTexture(float[] mtx) {
        if (mTextureName == 0)
            createSurfaceTexture();

        mSurfaceTexture.updateTexImage();
        mSurfaceTexture.getTransformMatrix(mtx);
    }

    public int getTextureName() {
        return mTextureName;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mContext.requestRender();
    }
}

package com.lsm.androidx.tool.activitys;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.IOException;

public class MySurfaceView extends SurfaceView implements Callback {
    private static final String TAG = "Kintai";
    private static SurfaceHolder holder;
    private Camera mCamera;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "new View ...");
        holder = getHolder();
        holder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i(TAG, "surfaceCreated...");
        if (this.mCamera == null) {
            this.mCamera = Camera.open();
            try {
                this.mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Log.i(TAG, "surfaceChanged...");
        this.mCamera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i(TAG, "surfaceChanged...");
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }
}

package com.lsm.androidx.tool.view;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class ToolLevelSurface extends SurfaceView implements Callback {
    private Camera mCameraDevices;
    private SurfaceHolder mHolder;
    private Parameters mParameters;
    public int surfaceCreatedC;

    public ToolLevelSurface(Context context) {
        super(context);
        this.mHolder = null;
        this.mCameraDevices = null;
        this.mParameters = null;
        this.surfaceCreatedC = 0;
        this.mHolder = getHolder();
        this.mHolder.setType(3);
        this.mHolder.addCallback(this);
    }

    public ToolLevelSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHolder = null;
        this.mCameraDevices = null;
        this.mParameters = null;
        this.surfaceCreatedC = 0;
        this.mHolder = getHolder();
        this.mHolder.setType(3);
        this.mHolder.addCallback(this);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            if (this.surfaceCreatedC != 2) {
                this.mCameraDevices.startPreview();
            }
        } catch (Exception e) {
            this.surfaceCreatedC = 2;
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.mCameraDevices = Camera.open();
            this.mCameraDevices.setDisplayOrientation(90);
            this.mCameraDevices.setPreviewDisplay(this.mHolder);
            this.surfaceCreatedC = 1;
        } catch (Exception e) {
            this.surfaceCreatedC = 2;
            if (this.mCameraDevices != null) {
                this.mCameraDevices.release();
            }
            this.mCameraDevices = null;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mCameraDevices != null) {
            this.mCameraDevices.stopPreview();
            this.mCameraDevices.release();
            this.mCameraDevices = null;
        }
    }

    public void setFlashlightSwitch(boolean on) {
        if (this.mCameraDevices != null) {
            this.mParameters = this.mCameraDevices.getParameters();
            if (on) {
                this.mParameters.setFlashMode("torch");
            } else {
                this.mParameters.setFlashMode("off");
            }
            this.mCameraDevices.setParameters(this.mParameters);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}

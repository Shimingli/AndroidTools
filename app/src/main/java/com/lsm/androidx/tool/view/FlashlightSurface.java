package com.lsm.androidx.tool.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.lsm.androidx.tool.activitys.ToolFlashLight;

import java.lang.reflect.Method;
import java.util.List;

public class FlashlightSurface extends SurfaceView implements Callback {
    private Camera mCameraDevices;
    private SurfaceHolder mHolder = getHolder();
    private Parameters mParameters;
    public int surfaceCreatedC = 0;

    public FlashlightSurface(Context context) {
        super(context);
        this.mHolder.setType(3);
        this.mHolder.addCallback(this);
    }

    public FlashlightSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHolder.setType(3);
        this.mHolder.addCallback(this);
    }

    @SuppressLint({"NewApi"})
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            if (this.surfaceCreatedC != 2) {
                this.mParameters = this.mCameraDevices.getParameters();
                if (this.mCameraDevices.getParameters().getMaxZoom() == 0 || !this.mParameters.isZoomSupported()) {
                    this.mCameraDevices.startPreview();
                    return;
                }
                if (this.mParameters != null) {
                    this.mParameters.setPictureFormat(256);
                }
                List<Size> sizes = this.mParameters.getSupportedPreviewSizes();
                Size cs = null;
                int i = 0;
                while (i < sizes.size()) {
                    cs = (Size) sizes.get(i);
                    if ((cs.width == ToolFlashLight.iscreenWidth && cs.height == ToolFlashLight.iscreenHeight) || (cs.width == ToolFlashLight.iscreenHeight && cs.height == ToolFlashLight.iscreenWidth)) {
                        break;
                    }
                    i++;
                }
                if (i >= sizes.size()) {
                    cs = (Size) sizes.get(0);
                }
                this.mParameters.setPictureSize(cs.width, cs.height);
                this.mParameters.setPreviewSize(cs.width, cs.height);
                this.mCameraDevices.setParameters(this.mParameters);
                this.mCameraDevices.startPreview();
            }
        } catch (Exception e) {
            this.mCameraDevices.startPreview();
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

    private void setDisplayOrientation(int orientation) {
        try {
            Method setCameraDisplayOrientation = this.mCameraDevices.getClass().getMethod("setDisplayOrientation", new Class[]{Integer.TYPE});
            if (setCameraDisplayOrientation != null) {
                setCameraDisplayOrientation.invoke(this.mCameraDevices, new Object[]{Integer.valueOf(orientation)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

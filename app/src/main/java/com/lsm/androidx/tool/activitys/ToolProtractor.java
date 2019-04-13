package com.lsm.androidx.tool.activitys;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.view.CameraManager;
import com.lsm.androidx.tool.view.CycleRulerView;

import java.io.IOException;

public class ToolProtractor extends BroadcastActivity implements Callback, OnCheckedChangeListener {
    Drawable btnDrawable;
    private CycleRulerView cycleRulerView;
    private FrameLayout fm;
    private int h;
    private boolean hasSurface;
    Resources resources;
    SurfaceView surfaceView;
    private ToggleButton tb;
    private int w;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_protractor);
        CameraManager.init(getApplication());
        this.hasSurface = false;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.w = dm.widthPixels;
        this.h = dm.heightPixels;
        this.tb = (ToggleButton) findViewById(R.id.camera_swicth);
        this.fm = (FrameLayout) findViewById(R.id.fm);
        this.cycleRulerView = (CycleRulerView) findViewById(R.id.cycleView);
        this.tb.setOnCheckedChangeListener(this);
        if (Integer.parseInt(getIntent().getStringExtra("indes")) == 0) {
            Toast.makeText(this, "点击返回键，返回上一级！", 0).show();
            this.resources = getBaseContext().getResources();
            this.btnDrawable = this.resources.getDrawable(R.color.white);
        }
    }

    protected void onResume() {
        super.onResume();
        startPreview();
    }

    protected void onPause() {
        super.onPause();
        stopPreview();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!this.hasSurface) {
            this.hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.hasSurface = false;
    }

    protected void onDestroy() {
        CameraManager.get().stopPreview();
        super.onDestroy();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (this.surfaceView != null) {
                this.surfaceView.setVisibility(0);
            }
            startPreview();
            this.fm.setVisibility(8);
            return;
        }
        if (this.surfaceView != null) {
            this.surfaceView.setVisibility(4);
        }
        stopPreview();
        this.fm.setVisibility(0);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e) {
        } catch (RuntimeException e2) {
        }
    }

    private void startPreview() {
        this.surfaceView = (SurfaceView) findViewById(R.id.surface);
        this.surfaceView.getHolder().setFormat(-3);
        SurfaceHolder surfaceHolder = this.surfaceView.getHolder();
        if (this.hasSurface) {
            initCamera(surfaceHolder);
            return;
        }
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(3);
    }

    private void stopPreview() {
        CameraManager.get().stopPreview();
        CameraManager.get().closeDriver();
    }
}

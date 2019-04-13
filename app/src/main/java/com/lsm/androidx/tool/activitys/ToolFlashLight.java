package com.lsm.androidx.tool.activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.view.FlashlightSurface;


public class ToolFlashLight extends BroadcastActivity implements OnClickListener {
    private ImageButton btnBack;
    private boolean isFlashlightOn = false;
    private ImageView mImageView = null;
    private FlashlightSurface mSurface = null;
    private TextView title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_flashlight);
        getWindow().setFlags(128, 128);
        this.mSurface = (FlashlightSurface) findViewById(R.id.surfaceview);
        this.mImageView = (ImageView) findViewById(R.id.image);
        this.title = (TextView) findViewById(R.id.title);
        this.title.setText("手电筒");
        this.btnBack = (ImageButton) findViewById(R.id.btnBack);
        this.btnBack.setOnClickListener(this);
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!checkCameraHardware(getApplicationContext()) || this.mSurface.surfaceCreatedC == 2) {
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.id_cap_cam), 1).show();
            finish();
            return false;
        }
        if (1 == event.getAction()) {
            if (this.isFlashlightOn) {
                this.mSurface.setFlashlightSwitch(false);
                this.isFlashlightOn = false;
            } else {
                this.mSurface.setFlashlightSwitch(true);
                this.isFlashlightOn = true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                return;
            default:
                return;
        }
    }
}

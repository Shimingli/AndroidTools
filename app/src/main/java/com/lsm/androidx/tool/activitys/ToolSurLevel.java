package com.lsm.androidx.tool.activitys;

import android.app.Activity;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.view.ToolLevelSurView;
import com.lsm.androidx.tool.view.ToolLevelSurface;


public class ToolSurLevel extends BroadcastActivity implements OnClickListener {
    private Sensor accSensor = null;
    private ImageButton btnBack;
    private ImageView center;
    private Sensor gyroscope = null;
    private ImageView horline;
    private boolean isFlashlightOn = false;
    private final SensorEventListener mSensorLisener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (1 == sensorEvent.sensor.getType()) {
                float[] values = sensorEvent.values;
                float ax = values[0];
                float ay = values[1];
                double cos = ((double) ay) / Math.sqrt((double) ((ax * ax) + (ay * ay)));
                if (cos > 1.0d) {
                    cos = 1.0d;
                } else if (cos < -1.0d) {
                    cos = -1.0d;
                }
                double rad = Math.acos(cos);
                if (ax < 0.0f) {
                    rad = 6.283185307179586d - rad;
                }
                ToolLevelSurView.setDegrees((float) ((180.0d * (rad - (1.5707963267948966d * ((double) ToolSurLevel.this.getWindowManager().getDefaultDisplay().getRotation())))) / 3.141592653589793d));
                ToolSurLevel.this.tlLineView.invalidate();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    private Sensor magnetSensor = null;
    private Matrix matrix = new Matrix();
    private SensorManager mySensorManager = null;
    private TextView title;
    private ToolLevelSurView tlLineView = null;
    private ToolLevelSurface tlevelView = null;
    private ImageView verline;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_levelsur);
        getWindow().setFlags(128, 128);
        this.title = (TextView) findViewById(R.id.title);
        this.title.setText("挂画校准");
        PermissionGen.with((Activity) this).addRequestCode(105).permissions("android.permission.CAMERA").request();
        this.tlevelView = (ToolLevelSurface) findViewById(R.id.surfaceview);
        this.tlLineView = (ToolLevelSurView) findViewById(R.id.id_surfaceLineView);
        this.mySensorManager = (SensorManager) getSystemService("sensor");
        this.accSensor = this.mySensorManager.getDefaultSensor(1);
        this.gyroscope = this.mySensorManager.getDefaultSensor(4);
        this.magnetSensor = this.mySensorManager.getDefaultSensor(11);
        this.btnBack = (ImageButton) findViewById(R.id.btnBack);
        this.btnBack.setOnClickListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult((Activity) this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 105)
    public void doSomething() {
    }

    @PermissionFail(requestCode = 105)
    public void doFailSomething() {
        Toast.makeText(this, "相机权限打开失败！", 0).show();
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

    public void onStart() {
        if (this.isFlashlightOn) {
            this.tlevelView.setFlashlightSwitch(false);
            this.isFlashlightOn = false;
        }
        super.onStart();
    }

    protected void onResume() {
        if (this.gyroscope != null) {
            this.mySensorManager.registerListener(this.mSensorLisener, this.gyroscope, 3);
        }
        if (this.accSensor != null) {
            this.mySensorManager.registerListener(this.mSensorLisener, this.accSensor, 3);
        }
        if (this.magnetSensor != null) {
            this.mySensorManager.registerListener(this.mSensorLisener, this.magnetSensor, 3);
        }
        super.onResume();
    }

    protected void onPause() {
        this.mySensorManager.unregisterListener(this.mSensorLisener);
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}

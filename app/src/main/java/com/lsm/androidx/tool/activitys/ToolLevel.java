package com.lsm.androidx.tool.activitys;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.utils.PhoneUtils;
import com.lsm.androidx.tool.view.ToolLevelView;


public class ToolLevel extends BroadcastActivity implements OnClickListener {
    private ImageButton bBackbt = null;
    private Display display;
    private float hx;
    public double inchHeight = 4.360000133514404d;
    private int k = 40;
    private float lhy;
    private float lly;
    private float lx;
    private final SensorListener mSensorLisener = new SensorListener() {
        public void onAccuracyChanged(int sensor, int accuracy) {
        }

        public boolean isContain(int x, int y) {
            int tempx = (int) (((double) x) + (((double) ToolLevel.this.tlevelV.zhongBitmap2.getWidth()) / 2.0d));
            int tempy = (int) (((double) y) + (((double) ToolLevel.this.tlevelV.zhongBitmap2.getWidth()) / 2.0d));
            int ox = (int) (((double) ToolLevel.this.tlevelV.zhong1_X) + (((double) ToolLevel.this.tlevelV.zhongBitmap1.getWidth()) / 2.0d));
            int oy = (int) (((double) ToolLevel.this.tlevelV.zhong1_Y) + (((double) ToolLevel.this.tlevelV.zhongBitmap1.getWidth()) / 2.0d));
            if (Math.sqrt((double) (((tempx - ox) * (tempx - ox)) + ((tempy - oy) * (tempy - oy)))) > (((double) ToolLevel.this.tlevelV.zhongBitmap1.getWidth()) / 2.0d) - (((double) ToolLevel.this.tlevelV.zhongBitmap2.getWidth()) / 2.0d)) {
                return false;
            }
            return true;
        }

        public void onSensorChanged(int sensor, float[] values) {
            if (sensor == 1) {
                int x;
                int y;
                double pitch = (double) values[1];
                double roll = (double) values[2];
                if (Math.abs(roll) <= ((double) ToolLevel.this.k)) {
                    ToolLevel.this.tlevelV.shang2_X = ToolLevel.this.tlevelV.shang1_X + ((int) ((((double) (ToolLevel.this.tlevelV.shangBitmap1.getWidth() - ToolLevel.this.tlevelV.zhongBitmap2.getWidth())) / 2.0d) - (((((double) (ToolLevel.this.tlevelV.shangBitmap1.getWidth() - ToolLevel.this.tlevelV.zhongBitmap2.getWidth())) / 2.0d) * roll) / ((double) ToolLevel.this.k))));
                    x = ToolLevel.this.tlevelV.zhong1_X + ((int) ((((double) (ToolLevel.this.tlevelV.zhongBitmap1.getWidth() - ToolLevel.this.tlevelV.zhongBitmap2.getWidth())) / 2.0d) - (((((double) (ToolLevel.this.tlevelV.zhongBitmap1.getWidth() - ToolLevel.this.tlevelV.zhongBitmap2.getWidth())) / 2.0d) * roll) / ((double) ToolLevel.this.k))));
                } else if (roll > ((double) ToolLevel.this.k)) {
                    ToolLevel.this.tlevelV.shang2_X = ToolLevel.this.tlevelV.shang1_X;
                    x = ToolLevel.this.tlevelV.zhong1_X;
                } else {
                    ToolLevel.this.tlevelV.shang2_X = (ToolLevel.this.tlevelV.shang1_X + ToolLevel.this.tlevelV.shangBitmap1.getWidth()) - ToolLevel.this.tlevelV.zhongBitmap2.getWidth();
                    x = (ToolLevel.this.tlevelV.zhong1_X + ToolLevel.this.tlevelV.zhongBitmap1.getWidth()) - ToolLevel.this.tlevelV.zhongBitmap2.getWidth();
                }
                if (Math.abs(pitch) <= ((double) ToolLevel.this.k)) {
                    ToolLevel.this.tlevelV.zuo2_Y = ToolLevel.this.tlevelV.zuo1_Y + ((int) ((((double) (ToolLevel.this.tlevelV.zuoBitmap1.getHeight() - ToolLevel.this.tlevelV.zhongBitmap2.getHeight())) / 2.0d) + (((((double) (ToolLevel.this.tlevelV.zuoBitmap1.getHeight() - ToolLevel.this.tlevelV.zhongBitmap2.getHeight())) / 2.0d) * pitch) / ((double) ToolLevel.this.k))));
                    y = ToolLevel.this.tlevelV.zhong1_Y + ((int) ((((double) (ToolLevel.this.tlevelV.zhongBitmap1.getHeight() - ToolLevel.this.tlevelV.zhongBitmap2.getHeight())) / 2.0d) + (((((double) (ToolLevel.this.tlevelV.zhongBitmap1.getHeight() - ToolLevel.this.tlevelV.zhongBitmap2.getHeight())) / 2.0d) * pitch) / ((double) ToolLevel.this.k))));
                } else if (pitch > ((double) ToolLevel.this.k)) {
                    ToolLevel.this.tlevelV.zuo2_Y = (ToolLevel.this.tlevelV.zuo1_Y + ToolLevel.this.tlevelV.zuoBitmap1.getHeight()) - ToolLevel.this.tlevelV.zhongBitmap2.getHeight();
                    y = (ToolLevel.this.tlevelV.zhong1_Y + ToolLevel.this.tlevelV.zhongBitmap1.getHeight()) - ToolLevel.this.tlevelV.zhongBitmap2.getHeight();
                } else {
                    ToolLevel.this.tlevelV.zuo2_Y = ToolLevel.this.tlevelV.zuo1_Y;
                    y = ToolLevel.this.tlevelV.zhong1_Y;
                }
                if (isContain(x, y)) {
                    ToolLevel.this.tlevelV.zhong2_X = x;
                    ToolLevel.this.tlevelV.zhong2_Y = y;
                }
                if (((float) ToolLevel.this.tlevelV.shang2_X) <= ToolLevel.this.lx || ((float) ToolLevel.this.tlevelV.shang2_X) >= ToolLevel.this.hx) {
                    ToolLevel.this.tlevelV.shangBitmap2 = ToolLevel.this.tlevelV.zhongBitmap3;
                } else {
                    ToolLevel.this.tlevelV.shangBitmap2 = ToolLevel.this.tlevelV.zhongBitmap;
                }
                if (((float) ToolLevel.this.tlevelV.zuo2_Y) <= ToolLevel.this.lly || ((float) ToolLevel.this.tlevelV.zuo2_Y) >= ToolLevel.this.lhy) {
                    ToolLevel.this.tlevelV.zuoBitmap2 = ToolLevel.this.tlevelV.zhongBitmap3;
                } else {
                    ToolLevel.this.tlevelV.zuoBitmap2 = ToolLevel.this.tlevelV.zhongBitmap;
                }
                if (((float) ToolLevel.this.tlevelV.zhong2_Y) <= ToolLevel.this.mly || ((float) ToolLevel.this.tlevelV.zhong2_Y) >= ToolLevel.this.mhy || ((float) ToolLevel.this.tlevelV.zhong2_X) <= ToolLevel.this.mlx || ((float) ToolLevel.this.tlevelV.zhong2_X) >= ToolLevel.this.mhx) {
                    ToolLevel.this.tlevelV.zhongBitmap2 = ToolLevel.this.tlevelV.zhongBitmap3;
                } else {
                    ToolLevel.this.tlevelV.zhongBitmap2 = ToolLevel.this.tlevelV.zhongBitmap;
                }
                ToolLevel.this.txt_x.setText("X：" + ((((float) ToolLevel.this.tlevelV.shang2_X) - ToolLevel.this.mlx) - 7.0f));
                ToolLevel.this.txt_y.setText("Y：" + ((((float) ToolLevel.this.tlevelV.zuo2_Y) - ToolLevel.this.mly) - 11.0f));
                ToolLevel.this.tlevelV.postInvalidate();
            }
        }
    };
    private float mhx;
    private float mhy;
    private float mlx;
    private float mly;
    private SensorManager mySensorManager = null;
    private double pxmm;
    private TextView title;
    private ToolLevelView tlevelV = null;
    private TextView txt_x;
    private TextView txt_y;
    double y = 0.0d;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_level);
        getWindow().setFlags(128, 128);
        float temp = PhoneUtils.getInchHeight(iscreenWidth, iscreenHeight, this);
        if (temp != 0.0f) {
            this.inchHeight = (double) temp;
        }
        this.y = this.inchHeight * 25.4d;
        this.pxmm = ((double) iscreenHeight) / this.y;
        this.title = (TextView) findViewById(R.id.title);
        this.title.setText("水平仪");
        this.tlevelV = (ToolLevelView) findViewById(R.id.toolleveView);
        this.mySensorManager = (SensorManager) getSystemService("sensor");
        this.bBackbt = (ImageButton) findViewById(R.id.btnBack);
        this.txt_x = (TextView) findViewById(R.id.txt_x);
        this.txt_y = (TextView) findViewById(R.id.txt_y);
        this.bBackbt.setOnClickListener(this);
        this.display = getWindowManager().getDefaultDisplay();
        if (this.display.getWidth() == 1080) {
            initCenterPosition1080();
        } else {
            initCenterPosition();
        }
    }

    private void initCenterPosition1080() {
        this.lx = (float) (((this.tlevelV.shang1_X + (this.tlevelV.shangBitmap1.getWidth() / 2)) - 38) - 23);
        this.hx = this.lx + 18.0f;
        this.lly = (float) (((this.tlevelV.zuo1_Y + (this.tlevelV.zuoBitmap1.getHeight() / 2)) - 38) - 28);
        this.lhy = this.lly + 16.0f;
        if (this.pxmm >= 10.0d) {
            this.mlx = this.lx;
            this.mhx = this.hx - 13.0f;
            this.mly = this.lly - 3.0f;
            this.mhy = this.lhy - 10.0f;
            return;
        }
        this.mlx = this.lx + 8.0f;
        this.mhx = this.hx - 5.0f;
        this.mly = this.lly + 8.0f;
        this.mhy = this.lhy - 3.0f;
    }

    private void initCenterPosition() {
        this.lx = (float) (((this.tlevelV.shang1_X + (this.tlevelV.shangBitmap1.getWidth() / 2)) - 38) - 4);
        this.hx = this.lx + 18.0f;
        this.lly = (float) (((this.tlevelV.zuo1_Y + (this.tlevelV.zuoBitmap1.getHeight() / 2)) - 38) - 4);
        this.lhy = this.lly + 16.0f;
        if (this.pxmm >= 10.0d) {
            this.mlx = this.lx;
            this.mhx = this.hx - 13.0f;
            this.mly = this.lly - 3.0f;
            this.mhy = this.lhy - 10.0f;
            return;
        }
        this.mlx = this.lx + 8.0f;
        this.mhx = this.hx - 5.0f;
        this.mly = this.lly + 8.0f;
        this.mhy = this.lhy - 3.0f;
    }

    protected void onResume() {
        this.mySensorManager.registerListener(this.mSensorLisener, 1);
        super.onResume();
    }

    protected void onPause() {
        this.mySensorManager.unregisterListener(this.mSensorLisener);
        super.onPause();
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

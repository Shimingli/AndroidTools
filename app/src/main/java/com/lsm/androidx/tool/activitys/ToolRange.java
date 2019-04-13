package com.lsm.androidx.tool.activitys;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shenfei.tools.utils.PhoneUtils;
import com.example.shenfei.tools.view.CustomRelativeLayout;
import com.example.shenfei.tools.view.CustomRelativeLayout.KeyboardChangeListener;
import com.oda_tools.R;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ToolRange extends BroadcastActivity implements OnClickListener, OnTouchListener {
    private double PI = 3.141592653589793d;
    private Sensor accSensor = null;
    private float apertureSize = 2.4f;
    private double b = 0.0d;
    private ImageButton btnBack;
    private EditText dialText = null;
    private float distance;
    private double distances;
    private float focusDistance = 0.371f;
    private Object geoMagnetic = null;
    private Object gravity = null;
    private float iBottomLineY = 0.0f;
    private float iTopLineY = 0.0f;
    private int iTouch_item = 0;
    private int imageLintH = 98;
    private LayoutParams imglayoutBottom = null;
    private LayoutParams imglayoutTop = null;
    private Matrix imgmatrixBottom = null;
    private Matrix imgmatrixTop = null;
    private float inchHeight = 4.36f;
    private float k = 0.0363f;
    private LinearLayout layoutTop;
    private float lineHeight;
    private float mFirstX = 0.0f;
    private float mFirstY = 0.0f;
    private ImageView mImageViewBottom = null;
    private ImageView mImageViewTop = null;
    private SensorManager mSensorManager = null;
    private float m_MoveSizeP = 0.0f;
    private float m_Range = 0.0f;
    private float m_holdHeight = 1.5f;
    private float m_moveSize = 10.0f;
    private float m_targetHeight = 0.0f;
    private Sensor magnetSensor = null;
    private Sensor oritationSensor;
    private float pxmm;
    private CustomRelativeLayout relativeMain;
    private float screenHeight;
    private TextView textrange = null;
    private TextView textrheight = null;
    private TextView tip;
    private TextView title;
    private TextView toptextrange;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_range);
        getWindow().setFlags(128, 128);
        PermissionGen.with((Activity) this).addRequestCode(105).permissions("android.permission.CAMERA").request();
        this.title = (TextView) findViewById(R.id.title);
        this.title.setText("距离测量");
        this.tip = (TextView) findViewById(R.id.tip);
        this.toptextrange = (TextView) findViewById(R.id.toptextrange);
        this.layoutTop = (LinearLayout) findViewById(R.id.layout_top);
        this.tip.getBackground().setAlpha(100);
        this.toptextrange.getBackground().setAlpha(100);
        this.layoutTop.getBackground().setAlpha(100);
        float temp = PhoneUtils.getInchHeight(iscreenWidth, iscreenHeight, this);
        if (temp != 0.0f) {
            this.inchHeight = temp;
        }
        this.screenHeight = (float) (((double) this.inchHeight) * 25.4d);
        this.pxmm = ((float) iscreenHeight) / this.screenHeight;
        this.imgmatrixTop = new Matrix();
        this.mImageViewTop = (ImageView) findViewById(R.id.topbluepoint);
        this.mImageViewTop.setImageMatrix(this.imgmatrixTop);
        this.mImageViewTop.setOnTouchListener(this);
        this.imgmatrixTop.set(this.mImageViewTop.getImageMatrix());
        this.imglayoutTop = new LayoutParams(iscreenWidth, this.imageLintH);
        this.iTopLineY = (float) (iscreenHeight / 4);
        this.imglayoutTop.setMargins(0, (int) this.iTopLineY, iscreenWidth, ((int) this.iTopLineY) + this.imageLintH);
        this.mImageViewTop.setLayoutParams(this.imglayoutTop);
        this.imgmatrixBottom = new Matrix();
        this.mImageViewBottom = (ImageView) findViewById(R.id.bottombluepoint);
        this.mImageViewBottom.setImageMatrix(this.imgmatrixBottom);
        this.mImageViewBottom.setOnTouchListener(this);
        this.imgmatrixBottom.set(this.mImageViewBottom.getImageMatrix());
        this.imglayoutBottom = new LayoutParams(iscreenWidth, this.imageLintH);
        this.iBottomLineY = (float) (iscreenHeight / 2);
        this.imglayoutBottom.setMargins(0, (int) this.iBottomLineY, iscreenWidth, ((int) this.iBottomLineY) + this.imageLintH);
        this.mImageViewBottom.setLayoutParams(this.imglayoutBottom);
        this.textrange = (TextView) findViewById(R.id.toptextrange);
        this.textrange.setText(getText(R.string.id_toptextrange) + String.valueOf(this.m_Range) + getText(R.string.id_toptextunits) + "");
        this.textrheight = (TextView) findViewById(R.id.toptextheight);
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.accSensor = this.mSensorManager.getDefaultSensor(1);
        this.oritationSensor = this.mSensorManager.getDefaultSensor(3);
        this.magnetSensor = this.mSensorManager.getDefaultSensor(2);
        this.btnBack = (ImageButton) findViewById(R.id.btnBack);
        this.btnBack.setOnClickListener(this);
        this.relativeMain = (CustomRelativeLayout) findViewById(R.id.relative_main);
        this.relativeMain.setOnKeyboardChangeListener(new KeyboardChangeListener() {
            public void onKeyboardChange(int w, int h, int oldw, int oldh) {
                if (h > oldh) {
                    if (ToolRange.this.textrheight.getText().toString() == null || ToolRange.this.textrheight.getText().toString().equals("")) {
                        ToolRange.this.m_targetHeight = 0.0f;
                    } else {
                        try {
                            ToolRange.this.m_targetHeight = new Float(ToolRange.this.textrheight.getText().toString()).floatValue();
                        } catch (NumberFormatException e) {
                            ToolRange.this.m_targetHeight = 1.0f;
                        }
                    }
                    ToolRange.this.m_Range = ToolRange.this.iBottomLineY - ToolRange.this.iTopLineY;
                    ToolRange.this.lineHeight = (ToolRange.this.m_Range / ToolRange.this.pxmm) / 10.0f;
                    ToolRange.this.distance = (float) (((double) (((ToolRange.this.focusDistance * ToolRange.this.m_targetHeight) / ToolRange.this.k) / ToolRange.this.lineHeight)) + ToolRange.this.b);
                    ToolRange.this.textrange.setText(ToolRange.this.getString(R.string.id_toptextrange) + " " + String.format("%.2f", new Object[]{Float.valueOf(ToolRange.this.distance)}) + " " + ToolRange.this.getString(R.string.id_toptextunits));
                }
            }
        });
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

    public void onStart() {
        super.onStart();
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

    public boolean onTouch(View v, MotionEvent event) {
        float ltop;
        switch (event.getAction()) {
            case 0:
                if (this.textrheight.getText().toString() == null || "".equals(this.textrheight.getText().toString())) {
                    this.m_targetHeight = 1.0f;
                    this.textrheight.setText("1");
                } else {
                    try {
                        this.m_targetHeight = new Float(this.textrheight.getText().toString()).floatValue();
                    } catch (NumberFormatException e) {
                        this.m_targetHeight = 1.0f;
                    }
                }
                this.iTouch_item = 0;
                this.mFirstX = event.getRawX();
                this.mFirstY = event.getRawY();
                if (v.getId() != R.id.topbluepoint) {
                    if (v.getId() == R.id.bottombluepoint) {
                        ltop = (event.getRawY() - this.mFirstY) + this.iBottomLineY;
                        if (ltop >= ((float) (iscreenHeight - 200))) {
                            ltop = (float) (iscreenHeight - 200);
                        } else if (ltop <= ((float) (iscreenHeight / 2))) {
                            ltop = (float) (iscreenHeight / 2);
                        }
                        this.imglayoutBottom.setMargins(0, (int) ltop, iscreenWidth, ((int) ltop) + this.imageLintH);
                        this.mImageViewBottom.setLayoutParams(this.imglayoutBottom);
                        this.iTouch_item = 2;
                        break;
                    }
                }
                ltop = (event.getRawY() - this.mFirstY) + this.iTopLineY;
                if (ltop >= ((float) ((iscreenHeight / 2) - this.imageLintH))) {
                    ltop = (float) ((iscreenHeight / 2) - this.imageLintH);
                } else if (ltop <= 0.0f) {
                    ltop = 0.0f;
                }
                this.imglayoutTop.setMargins(0, (int) ltop, iscreenWidth, ((int) ltop) + this.imageLintH);
                this.mImageViewTop.setLayoutParams(this.imglayoutTop);
                this.iTouch_item = 1;
                break;
                break;
            case 1:
                if (this.iTouch_item == 1) {
                    this.iTopLineY = (event.getRawY() - this.mFirstY) + this.iTopLineY;
                    if (this.iTopLineY >= ((float) ((iscreenHeight / 2) - this.imageLintH))) {
                        this.iTopLineY = (float) ((iscreenHeight / 2) - this.imageLintH);
                    } else if (this.iTopLineY <= 0.0f) {
                        this.iTopLineY = 0.0f;
                    }
                    this.imglayoutTop.setMargins(0, (int) this.iTopLineY, iscreenWidth, ((int) this.iTopLineY) + this.imageLintH);
                    this.mImageViewTop.setLayoutParams(this.imglayoutTop);
                } else if (this.iTouch_item == 2) {
                    this.iBottomLineY = (event.getRawY() - this.mFirstY) + this.iBottomLineY;
                    if (this.iBottomLineY >= ((float) (iscreenHeight - 200))) {
                        this.iBottomLineY = (float) (iscreenHeight - 200);
                    } else if (this.iBottomLineY <= ((float) (iscreenHeight / 2))) {
                        this.iBottomLineY = (float) (iscreenHeight / 2);
                    }
                    this.imglayoutBottom.setMargins(0, (int) this.iBottomLineY, iscreenWidth, ((int) this.iBottomLineY) + this.imageLintH);
                    this.mImageViewBottom.setLayoutParams(this.imglayoutBottom);
                }
                this.iTouch_item = 0;
                this.mFirstX = 0.0f;
                this.mFirstY = 0.0f;
                this.m_Range = this.iBottomLineY - this.iTopLineY;
                this.lineHeight = (this.m_Range / this.pxmm) / 10.0f;
                this.distance = (float) (((double) (((this.focusDistance * this.m_targetHeight) / this.k) / this.lineHeight)) + this.b);
                this.textrange.setText(getString(R.string.id_toptextrange) + " " + String.format("%.2f", new Object[]{Float.valueOf(this.distance)}) + " " + getString(R.string.id_toptextunits));
                break;
            case 2:
                float lbottom = this.iBottomLineY;
                ltop = this.iTopLineY;
                if (this.iTouch_item == 1) {
                    ltop = (event.getRawY() - this.mFirstY) + this.iTopLineY;
                    if (ltop >= ((float) ((iscreenHeight / 2) - this.imageLintH))) {
                        ltop = (float) ((iscreenHeight / 2) - this.imageLintH);
                    } else if (ltop <= 0.0f) {
                        ltop = 0.0f;
                    }
                    this.imglayoutTop.setMargins(0, (int) ltop, iscreenWidth, ((int) ltop) + this.imageLintH);
                    this.mImageViewTop.setLayoutParams(this.imglayoutTop);
                } else if (this.iTouch_item == 2) {
                    lbottom = (event.getRawY() - this.mFirstY) + this.iBottomLineY;
                    if (lbottom >= ((float) (iscreenHeight - 200))) {
                        lbottom = (float) (iscreenHeight - 200);
                    } else if (lbottom <= ((float) (iscreenHeight / 2))) {
                        lbottom = (float) (iscreenHeight / 2);
                    }
                    this.imglayoutBottom.setMargins(0, (int) lbottom, iscreenWidth, ((int) lbottom) + this.imageLintH);
                    this.mImageViewBottom.setLayoutParams(this.imglayoutBottom);
                }
                this.m_Range = lbottom - ltop;
                this.lineHeight = (this.m_Range / this.pxmm) / 10.0f;
                this.distance = (float) (((double) (((this.focusDistance * this.m_targetHeight) / this.k) / this.lineHeight)) + this.b);
                this.textrange.setText(getString(R.string.id_toptextrange) + " " + String.format("%.2f", new Object[]{Float.valueOf(this.distance)}) + " " + getString(R.string.id_toptextunits));
                break;
        }
        return true;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        iscreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        iscreenHeight = getWindowManager().getDefaultDisplay().getHeight();
    }
}

package com.lsm.androidx.tool.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsm.androidx.tool.R;


public class MainActivity extends BroadcastActivity {
    @BindView(2131492959)
    LinearLayout bRange;
    private Camera camera;
    @BindView(2131492963)
    TextView compass;
    private Context context;
    @BindView(2131492957)
    LinearLayout flashLight;
    final int[] icon = new int[]{R.drawable.bg_brange, R.drawable.bg_compass, R.drawable.bg_fashlight, R.drawable.bg_levelair};
    private int indes = 0;
    private int index = 0;
    private boolean isFlashlightOn = false;
    @BindView(2131492962)
    TextView levelAir;
    @BindView(2131492958)
    LinearLayout levelSur;
    private String mCameraId;
    private CameraManager mCameraManager;
    private ImageView menu;
    private OnMenuListener menuListener = new OnMenuListener() {
        public void openMenu() {
        }

        public void closeMenu() {
        }
    };
    @BindView(2131492961)
    TextView protractor;
    private ResideMenu resideMenu;
    @BindView(2131492960)
    TextView ruler;
    @BindView(2131492956)
    ImageView soundMeter;
    final String[] titles = new String[]{"首页", "分享", "问题和建议", "给我们评分"};

    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        setContentView(R.layout.activity_main);
        this.context = this;
        ButterKnife.bind((Activity) this);
        init();
    }

    public void hideNavigationBar() {
        int uiFlags;
        if (VERSION.SDK_INT >= 19) {
            uiFlags = 1536 | 4096;
        } else {
            uiFlags = 1536 | 1;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    private void init() {
        initMenu();
    }

    private void initMenu() {
        this.resideMenu = new ResideMenu(this);
        this.resideMenu.setBackground(R.color.theme_color);
        this.resideMenu.attachToActivity(this);
        this.resideMenu.setMenuListener(this.menuListener);
        for (int i = 0; i < this.titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem((Context) this, this.icon[i], this.titles[i]);
            item.setId(i);
            item.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    switch (v.getId()) {
                        case 0:
                            Toast.makeText(MainActivity.this.context, "首页", 0).show();
                            return;
                        case 1:
                            Toast.makeText(MainActivity.this.context, "分享", 0).show();
                            return;
                        case 2:
                            Toast.makeText(MainActivity.this.context, "问题和建议", 0).show();
                            return;
                        case 3:
                            Toast.makeText(MainActivity.this.context, "给我们评分", 0).show();
                            return;
                        default:
                            return;
                    }
                }
            });
            this.resideMenu.addMenuItem(item, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult((Activity) this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 106)
    public void doSomeething() {
        startActivity(new Intent(this.context, ToolSoundMeter.class));
    }

    @PermissionFail(requestCode = 106)
    public void doFailSomeething() {
        Toast.makeText(this, "录音机权限打开失败！", 0).show();
    }

    @PermissionSuccess(requestCode = 107)
    public void aVoid() {
        startActivity(new Intent(this.context, ToolSurLevel.class));
    }

    @PermissionFail(requestCode = 107)
    public void aVoidfail() {
        Toast.makeText(this, "相机权限打开失败！", 0).show();
    }

    @PermissionSuccess(requestCode = 108)
    public void cameraSuscces() {
        if (this.isFlashlightOn) {
            this.flashLight.setBackground(getResources().getDrawable(R.drawable.shape_corner_one));
            this.camera.startPreview();
            this.camera.release();
            this.isFlashlightOn = false;
            return;
        }
        this.flashLight.setBackground(getResources().getDrawable(R.drawable.shape_corner_one_one));
        this.camera = Camera.open();
        Parameters parameters = this.camera.getParameters();
        parameters.setFlashMode("torch");
        this.camera.setParameters(parameters);
        this.camera.startPreview();
        this.isFlashlightOn = true;
    }

    @PermissionFail(requestCode = 108)
    public void cameraFail() {
        Toast.makeText(this, "相机权限获取失败！", 0).show();
    }

    @PermissionSuccess(requestCode = 105)
    public void doSomething() {
        startActivity(new Intent(this.context, ToolRange.class));
    }

    @PermissionFail(requestCode = 105)
    public void doFailSomething() {
        Toast.makeText(this, "相机权限打开失败！", 0).show();
    }

    @TargetApi(21)
    @OnClick({2131492956, 2131492959, 2131492963, 2131492957, 2131492962, 2131492958, 2131492961, 2131492960})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.sound_meter:
                PermissionGen.with((Activity) this).addRequestCode(106).permissions("android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE").request();
                return;
            case R.id.flashLight:
                if (VERSION.SDK_INT >= 21) {
                    this.mCameraManager = (CameraManager) getSystemService("camera");
                    try {
                        this.mCameraId = this.mCameraManager.getCameraIdList()[0];
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (this.isFlashlightOn) {
                            turnOffFlashLight();
                            this.isFlashlightOn = false;
                            return;
                        }
                        turnOnFlashLight();
                        this.isFlashlightOn = true;
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                PermissionGen.with((Activity) this).addRequestCode(108).permissions("android.permission.CAMERA").request();
                return;
            case R.id.levelSur:
                PermissionGen.with((Activity) this).addRequestCode(107).permissions("android.permission.CAMERA").request();
                return;
            case R.id.bRange:
                PermissionGen.with((Activity) this).addRequestCode(105).permissions("android.permission.CAMERA").request();
                return;
            case R.id.ruler:
                Intent id_BRuler = new Intent(this.context, ToolRuler.class);
                id_BRuler.putExtra("index", this.index + "");
                startActivity(id_BRuler);
                this.index++;
                return;
            case R.id.protractor:
                Intent id_Protractor = new Intent(this.context, ToolProtractor.class);
                id_Protractor.putExtra("indes", this.indes + "");
                startActivity(id_Protractor);
                this.indes++;
                return;
            case R.id.levelAir:
                if (((SensorManager) getSystemService("sensor")).getSensorList(3).size() == 0) {
                    Toast.makeText(this, "设备不支持，该功能无法使用！", 1).show();
                    return;
                } else {
                    startActivity(new Intent(this.context, ToolLevel.class));
                    return;
                }
            case R.id.compass:
                if (((SensorManager) getSystemService("sensor")).getSensorList(3).size() == 0) {
                    Toast.makeText(this, "设备不支持，该功能无法使用！", 1).show();
                    return;
                } else {
                    startActivity(new Intent(this.context, ToolCompass.class));
                    return;
                }
            default:
                return;
        }
    }

    public void turnOnFlashLight() {
        try {
            if (VERSION.SDK_INT >= 23) {
                this.mCameraManager.setTorchMode(this.mCameraId, true);
                this.flashLight.setBackground(getResources().getDrawable(R.drawable.shape_corner_one_one));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashLight() {
        try {
            if (VERSION.SDK_INT >= 23) {
                this.mCameraManager.setTorchMode(this.mCameraId, false);
                this.flashLight.setBackground(getResources().getDrawable(R.drawable.shape_corner_one));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.lsm.androidx.tool.activitys;

import android.annotation.TargetApi;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsm.androidx.tool.R;

public class ToolCompass extends BroadcastActivity implements OnClickListener, SensorListener {
    private float DegressQuondam = 0.0f;
    private ImageView ImgCompass = null;
    private TextView OrientText = null;
    private ImageButton bBackbt = null;
    private ImageButton baniro = null;
    private RotateAnimation myAni = null;
    private TextView oritation;
    private RelativeLayout relativeLayout;
    private SensorManager sm = null;
    private TextView title;

    @TargetApi(16)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        this.title = (TextView) findViewById(R.id.title);
        this.relativeLayout = (RelativeLayout) findViewById(R.id.opinion_title);
        this.title.setText("指南针");
        this.relativeLayout.setBackground(getResources().getDrawable(R.color.blue));
        this.OrientText = (TextView) findViewById(R.id.OrientText);
        this.ImgCompass = (ImageView) findViewById(R.id.ivCompass);
        getWindow().setFlags(128, 128);
        this.bBackbt = (ImageButton) findViewById(R.id.btnBack);
        this.bBackbt.setOnClickListener(this);
        this.oritation = (TextView) findViewById(R.id.orientation);
    }

    public void onPause() {
        super.onPause();
    }

    private void AniRotateImage(float fDegress) {
        this.myAni = new RotateAnimation(this.DegressQuondam, fDegress, 1, 0.5f, 1, 0.5f);
        this.myAni.setDuration(300);
        this.myAni.setFillAfter(true);
        this.ImgCompass.startAnimation(this.myAni);
        this.DegressQuondam = fDegress;
    }

    /* JADX WARNING: Missing block: B:38:?, code:
            return;
     */
    public void onSensorChanged(int r7, float[] r8) {
        /*
        r6 = this;
        r5 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        r4 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r3 = 90;
        monitor-enter(r6);
        r1 = 1;
        if (r7 != r1) goto L_0x0069;
    L_0x000a:
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x006b }
        r2 = r6.DegressQuondam;	 Catch:{ all -> 0x006b }
        r1 = r1 - r2;
        r1 = java.lang.Math.abs(r1);	 Catch:{ all -> 0x006b }
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r1 >= 0) goto L_0x001c;
    L_0x001a:
        monitor-exit(r6);	 Catch:{ all -> 0x006b }
    L_0x001b:
        return;
    L_0x001c:
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x006b }
        r1 = (int) r1;	 Catch:{ all -> 0x006b }
        switch(r1) {
            case 0: goto L_0x006e;
            case 90: goto L_0x0095;
            case 180: goto L_0x00bc;
            case 270: goto L_0x00e4;
            default: goto L_0x0023;
        };	 Catch:{ all -> 0x006b }
    L_0x0023:
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 0;
        r1.setVisibility(r2);	 Catch:{ all -> 0x006b }
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x006b }
        r0 = (int) r1;	 Catch:{ all -> 0x006b }
        if (r0 <= 0) goto L_0x010c;
    L_0x002f:
        if (r0 >= r3) goto L_0x010c;
    L_0x0031:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r2 = r2.append(r0);	 Catch:{ all -> 0x006b }
        r3 = "°";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 2131165229; // 0x7f07002d float:1.794467E38 double:1.0529355253E-314;
        r2 = r6.getString(r2);	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
    L_0x0055:
        r1 = r6.DegressQuondam;	 Catch:{ all -> 0x006b }
        r2 = 0;
        r2 = r8[r2];	 Catch:{ all -> 0x006b }
        r2 = -r2;
        r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r1 == 0) goto L_0x0069;
    L_0x005f:
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x006b }
        r1 = -r1;
        r2 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r1 = r1 + r2;
        r6.AniRotateImage(r1);	 Catch:{ all -> 0x006b }
    L_0x0069:
        monitor-exit(r6);	 Catch:{ all -> 0x006b }
        goto L_0x001b;
    L_0x006b:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x006b }
        throw r1;
    L_0x006e:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r3 = 2131165228; // 0x7f07002c float:1.7944667E38 double:1.052935525E-314;
        r3 = r6.getText(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 8;
        r1.setVisibility(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x0095:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r3 = 2131165226; // 0x7f07002a float:1.7944663E38 double:1.052935524E-314;
        r3 = r6.getText(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 8;
        r1.setVisibility(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x00bc:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r3 = 2131165230; // 0x7f07002e float:1.7944671E38 double:1.052935526E-314;
        r3 = r6.getText(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 8;
        r1.setVisibility(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x00e4:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r3 = 2131165232; // 0x7f070030 float:1.7944675E38 double:1.0529355267E-314;
        r3 = r6.getText(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 8;
        r1.setVisibility(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x010c:
        if (r0 <= r3) goto L_0x0136;
    L_0x010e:
        if (r0 >= r4) goto L_0x0136;
    L_0x0110:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r2 = r2.append(r0);	 Catch:{ all -> 0x006b }
        r3 = "°";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 2131165227; // 0x7f07002b float:1.7944665E38 double:1.0529355243E-314;
        r2 = r6.getString(r2);	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x0136:
        if (r0 <= r4) goto L_0x0160;
    L_0x0138:
        if (r0 >= r5) goto L_0x0160;
    L_0x013a:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r2 = r2.append(r0);	 Catch:{ all -> 0x006b }
        r3 = "°";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 2131165231; // 0x7f07002f float:1.7944673E38 double:1.0529355262E-314;
        r2 = r6.getString(r2);	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
    L_0x0160:
        if (r0 <= r5) goto L_0x0055;
    L_0x0162:
        r1 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        if (r0 >= r1) goto L_0x0055;
    L_0x0166:
        r1 = r6.OrientText;	 Catch:{ all -> 0x006b }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r2.<init>();	 Catch:{ all -> 0x006b }
        r2 = r2.append(r0);	 Catch:{ all -> 0x006b }
        r3 = "°";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006b }
        r2 = r2.toString();	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        r1 = r6.oritation;	 Catch:{ all -> 0x006b }
        r2 = 2131165233; // 0x7f070031 float:1.7944677E38 double:1.052935527E-314;
        r2 = r6.getString(r2);	 Catch:{ all -> 0x006b }
        r1.setText(r2);	 Catch:{ all -> 0x006b }
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.shenfei.tools.activitys.ToolCompass.onSensorChanged(int, float[]):void");
    }

    public void onAccuracyChanged(int sensor, int accuracy) {
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
        this.sm = (SensorManager) getSystemService("sensor");
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        this.sm.registerListener(this, 3, 0);
    }

    protected void onStop() {
        this.sm.unregisterListener(this);
        super.onStop();
    }
}

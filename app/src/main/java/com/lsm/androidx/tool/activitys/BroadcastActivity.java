package com.lsm.androidx.tool.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.lsm.androidx.tool.utils.AppManager;

import java.text.DecimalFormat;

public class BroadcastActivity extends Activity {
    public static final String BROADCAST_MSG = "com.minitools.SQ.BROADCAST_MESSAGE";
    public static final String BROADCAST_TYPE = "com.minitools.SQ.BroadcastActivity.BROADCAST_TYPE";
    public static final int TYPE_EXIT_APP = 1000;
    public static int iscreenHeight = 0;
    public static int iscreenWidth = 0;
    private static int sActivityCount = 0;
    public final DecimalFormat fnum = new DecimalFormat("#####0.0");
    private BroadcastReceiver mBroadcastRcv = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        this.mBroadcastRcv = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    BroadcastActivity.this.onRcvBroadcastMsg(context, intent);
                } catch (Exception e) {
                }
            }
        };
        iscreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        iscreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_MSG);
        filter.addAction("HomeKeyDispatch");
        registerReceiver(this.mBroadcastRcv, filter);
        sActivityCount++;
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= -67108865;
        }
        win.setAttributes(winParams);
    }

    protected void onRcvBroadcastMsg(Context context, Intent intent) {
        switch (intent.getIntExtra(BROADCAST_TYPE, 0)) {
            case 1000:
                finish();
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity((Activity) this);
        unregisterReceiver(this.mBroadcastRcv);
        sActivityCount--;
    }

    protected void onResume() {
        super.onResume();
        StatService.onResume((Context) this);
    }

    protected void onPause() {
        super.onPause();
        StatService.onPause((Context) this);
    }

    protected void closeAllActivity() {
        Intent intent = new Intent(BROADCAST_MSG);
        intent.putExtra(BROADCAST_TYPE, 1000);
        sendBroadcast(intent);
    }

    protected void messageBox(String title, String message, int iconResource, String btn1Text, String btn2Text, String btn3Text, OnClickListener hClick, OnCancelListener hCancel) {
        AlertDialog msgDlg = new Builder(this).create();
        msgDlg.setMessage(message);
        msgDlg.setCancelable(true);
        if (title != null) {
            msgDlg.setTitle(title);
        }
        if (iconResource != 0) {
            msgDlg.setIcon(iconResource);
        }
        if (hCancel != null) {
            msgDlg.setOnCancelListener(hCancel);
        }
        if (btn1Text != null) {
            msgDlg.setButton(-1, btn1Text, hClick);
        }
        if (btn2Text != null) {
            msgDlg.setButton(-2, btn2Text, hClick);
        }
        if (btn3Text != null) {
            msgDlg.setButton(-3, btn3Text, hClick);
        }
        if (!isFinishing()) {
            try {
                msgDlg.show();
            } catch (Exception e) {
            }
        }
    }

    public void showToastMsg(Toast mToast, Context mContext, String text, int duration) {
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(mContext, text, 0);
        }
        if (!isFinishing()) {
            try {
                mToast.show();
            } catch (Exception e) {
            }
        }
    }

    protected int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}

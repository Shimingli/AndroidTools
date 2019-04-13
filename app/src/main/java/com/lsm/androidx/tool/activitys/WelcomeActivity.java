package com.lsm.androidx.tool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.oda_tools.R;

public class WelcomeActivity extends BroadcastActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }
}

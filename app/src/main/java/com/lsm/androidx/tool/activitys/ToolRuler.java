package com.lsm.androidx.tool.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lsm.androidx.tool.view.RulerView;

public class ToolRuler extends BroadcastActivity {
    private RulerView rulerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(1024);
        View rulerView = new RulerView(this);
        this.rulerView = (RulerView) rulerView;
        setContentView(rulerView);
        init();
    }

    private void init() {
        if (Integer.parseInt(getIntent().getStringExtra("index")) == 0) {
            Toast.makeText(this, "点击返回键，返回上一级！", 0).show();
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (this.rulerView != null) {
            this.rulerView.setLineX(savedInstanceState.getFloat("lineX"));
            this.rulerView.setKedu(savedInstanceState.getInt("kedu"));
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (this.rulerView != null) {
            outState.putFloat("lineX", this.rulerView.getLineX());
            outState.putInt("kedu", this.rulerView.getKedu());
        }
        super.onSaveInstanceState(outState);
    }
}

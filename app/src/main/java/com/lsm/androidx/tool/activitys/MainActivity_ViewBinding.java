package com.lsm.androidx.tool.activitys;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.utils.Utils;
import com.lsm.androidx.tool.R;

import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;

public class MainActivity_ViewBinding implements Unbinder {
    private MainActivity target;
    private View view2131492956;
    private View view2131492957;
    private View view2131492958;
    private View view2131492959;
    private View view2131492960;
    private View view2131492961;
    private View view2131492962;
    private View view2131492963;

    @UiThread
    public MainActivity_ViewBinding(MainActivity target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public MainActivity_ViewBinding(final MainActivity target, View source) {
        this.target = target;
        View view = Utils.findRequiredView(source, R.id.bRange, "field 'bRange' and method 'OnClick'");
        target.bRange = (LinearLayout) Utils.castView(view, R.id.bRange, "field 'bRange'", LinearLayout.class);
        this.view2131492959 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.compass, "field 'compass' and method 'OnClick'");
        target.compass = (TextView) Utils.castView(view, R.id.compass, "field 'compass'", TextView.class);
        this.view2131492963 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.flashLight, "field 'flashLight' and method 'OnClick'");
        target.flashLight = (LinearLayout) Utils.castView(view, R.id.flashLight, "field 'flashLight'", LinearLayout.class);
        this.view2131492957 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.levelAir, "field 'levelAir' and method 'OnClick'");
        target.levelAir = (TextView) Utils.castView(view, R.id.levelAir, "field 'levelAir'", TextView.class);
        this.view2131492962 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.levelSur, "field 'levelSur' and method 'OnClick'");
        target.levelSur = (LinearLayout) Utils.castView(view, R.id.levelSur, "field 'levelSur'", LinearLayout.class);
        this.view2131492958 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.protractor, "field 'protractor' and method 'OnClick'");
        target.protractor = (TextView) Utils.castView(view, R.id.protractor, "field 'protractor'", TextView.class);
        this.view2131492961 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.ruler, "field 'ruler' and method 'OnClick'");
        target.ruler = (TextView) Utils.castView(view, R.id.ruler, "field 'ruler'", TextView.class);
        this.view2131492960 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
        view = Utils.findRequiredView(source, R.id.sound_meter, "field 'soundMeter' and method 'OnClick'");
        target.soundMeter = (ImageView) Utils.castView(view, R.id.sound_meter, "field 'soundMeter'", ImageView.class);
        this.view2131492956 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.OnClick(p0);
            }
        });
    }

    @CallSuper
    public void unbind() {
        MainActivity target = this.target;
        if (target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target.bRange = null;
        target.compass = null;
        target.flashLight = null;
        target.levelAir = null;
        target.levelSur = null;
        target.protractor = null;
        target.ruler = null;
        target.soundMeter = null;
        this.view2131492959.setOnClickListener(null);
        this.view2131492959 = null;
        this.view2131492963.setOnClickListener(null);
        this.view2131492963 = null;
        this.view2131492957.setOnClickListener(null);
        this.view2131492957 = null;
        this.view2131492962.setOnClickListener(null);
        this.view2131492962 = null;
        this.view2131492958.setOnClickListener(null);
        this.view2131492958 = null;
        this.view2131492961.setOnClickListener(null);
        this.view2131492961 = null;
        this.view2131492960.setOnClickListener(null);
        this.view2131492960 = null;
        this.view2131492956.setOnClickListener(null);
        this.view2131492956 = null;
    }
}

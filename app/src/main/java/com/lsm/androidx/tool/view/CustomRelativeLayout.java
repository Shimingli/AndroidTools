package com.lsm.androidx.tool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout {
    private KeyboardChangeListener listener;

    public interface KeyboardChangeListener {
        void onKeyboardChange(int i, int i2, int i3, int i4);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.listener != null) {
            this.listener.onKeyboardChange(w, h, oldw, oldh);
        }
    }

    public void setOnKeyboardChangeListener(KeyboardChangeListener listener) {
        this.listener = listener;
    }
}

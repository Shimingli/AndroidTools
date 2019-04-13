package com.lsm.androidx.tool.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import com.example.shenfei.tools.activitys.ToolSoundMeter;
import com.oda_tools.R;

public class ToolSoundMeterView extends View {
    private float _X;
    private float _Y;
    private Bitmap arrow;
    private float arrowX;
    private float arrowY;
    private Bitmap centerBackground;
    private Bitmap centerBitmap;
    private float centerX;
    private float centerY;
    private float degree;
    private float degrees;
    private Paint paint;
    private String showDegrees;

    public ToolSoundMeterView(Context context) {
        super(context);
        this.paint = new Paint();
        this.paint.setStyle(Style.STROKE);
        this.paint.setAntiAlias(true);
        this.centerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.soundmeter_center);
    }

    public ToolSoundMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.degree = -120.0f;
        this.degrees = 60.0f;
        this.paint = new Paint();
        this.paint.setStyle(Style.STROKE);
        this.paint.setAntiAlias(true);
        this.centerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.soundmeter_center);
        this.centerBackground = BitmapFactory.decodeResource(getResources(), R.drawable.center_background);
        this.arrow = BitmapFactory.decodeResource(getResources(), R.drawable.soundmeter_arrow1);
        this.centerX = (float) ((ToolSoundMeter.iscreenWidth - this.centerBackground.getWidth()) / 2);
        this.centerY = (float) ((ToolSoundMeter.iscreenHeight / 5) - (this.centerBackground.getHeight() / 2));
        this._X = (float) (((ToolSoundMeter.iscreenWidth - this.centerBitmap.getWidth()) / 2) + 5);
        this._Y = (float) ((ToolSoundMeter.iscreenHeight / 5) - (this.centerBitmap.getHeight() / 2));
        if (ToolSoundMeter.iscreenWidth == 480) {
            this.arrowX = (float) (((ToolSoundMeter.iscreenWidth - this.arrow.getWidth()) / 2) + 3);
            this.arrowY = (this._Y - ((float) this.arrow.getHeight())) + 11.0f;
        } else if (ToolSoundMeter.iscreenWidth == 720) {
            this.arrowX = (float) (((ToolSoundMeter.iscreenWidth - this.arrow.getWidth()) / 2) + 2);
            this.arrowY = (this._Y - ((float) this.arrow.getHeight())) + 13.0f;
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.centerBackground, this.centerX, this.centerY, this.paint);
        canvas.drawBitmap(this.centerBitmap, this._X, this._Y, this.paint);
        if (ToolSoundMeter.iscreenWidth == 480) {
            this.paint.setTextSize(30.0f);
        } else if (ToolSoundMeter.iscreenWidth == 720) {
            this.paint.setTextSize(60.0f);
        }
        this.paint.setColor(-1);
        this.showDegrees = this.degrees + "dB";
        canvas.drawText(this.showDegrees, (((float) ToolSoundMeter.iscreenWidth) - this.paint.measureText(this.showDegrees)) / 2.0f, (float) ((ToolSoundMeter.iscreenHeight / 5) + this.centerBitmap.getHeight()), this.paint);
        canvas.rotate(this.degree, (float) ((ToolSoundMeter.iscreenWidth / 2) + 5), (float) (ToolSoundMeter.iscreenHeight / 5));
        canvas.drawBitmap(this.arrow, this.arrowX, this.arrowY, this.paint);
    }

    public float getDegree() {
        return this.degree;
    }

    public float getDegrees() {
        return this.degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}

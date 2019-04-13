package com.lsm.androidx.tool.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lsm.androidx.tool.R;


public class SoundDiscView extends ImageView {
    static final long ANIMATION_INTERVAL = 20;
    private Bitmap indicatorBitmap;
    private Matrix mMatrix = new Matrix();
    private int newHeight;
    private int newWidth;
    private Paint paint = new Paint();
    private float scaleWidth;

    public SoundDiscView(Context context) {
        super(context);
    }

    public SoundDiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noise_index_11);
        int bitmapWidth = myBitmap.getWidth();
        int bitmapHeight = myBitmap.getHeight();
        this.newWidth = getWidth();
        this.newHeight = getHeight();
        this.newHeight = (int) (((float) this.newHeight) - (20.0f * ScreenUtil.getDensity(getContext())));
        this.scaleWidth = ((float) this.newWidth) / ((float) bitmapWidth);
        this.scaleWidth = ((float) this.newHeight) / ((float) bitmapHeight);
        this.indicatorBitmap = Bitmap.createBitmap(myBitmap, 0, 0, bitmapWidth, bitmapHeight, this.mMatrix, true);
        this.paint = new Paint();
        this.paint.setTextSize(16.0f * ScreenUtil.getDensity(getContext()));
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setTextAlign(Align.CENTER);
        this.paint.setColor(-1);
    }

    public void refresh() {
        postInvalidateDelayed(ANIMATION_INTERVAL);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.indicatorBitmap == null) {
            init();
        }
        this.mMatrix.setRotate(getAngle(World.dbCount), (float) (this.indicatorBitmap.getWidth() - (this.indicatorBitmap.getHeight() / 2)), (float) (this.indicatorBitmap.getHeight() / 2));
        this.mMatrix.postTranslate((float) (((this.newWidth / 2) - this.indicatorBitmap.getWidth()) + (this.indicatorBitmap.getHeight() / 2)), (float) (this.newHeight - (this.indicatorBitmap.getHeight() / 2)));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        canvas.drawBitmap(this.indicatorBitmap, this.mMatrix, this.paint);
    }

    private float getAngle(float db) {
        return (float) (((double) (5.0f * db)) / 3.141592653589793d);
    }
}

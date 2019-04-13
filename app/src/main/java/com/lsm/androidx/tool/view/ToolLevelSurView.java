package com.lsm.androidx.tool.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.activitys.ToolSurLevel;


public class ToolLevelSurView extends View {
    public static float degrees;
    private float centerX;
    private float centerY;
    private int dotWitdh;
    private Bitmap horLine;
    private Matrix matrix;
    private Paint paint;
    private PaintFlagsDrawFilter pfd;
    private Bitmap verLine;

    public ToolLevelSurView(Context context) {
        super(context);
        this.paint = null;
        this.matrix = new Matrix();
        this.dotWitdh = 64;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(10.0f);
        this.matrix.setTranslate((float) ((ToolSurLevel.iscreenWidth * 3) / 4), (float) (ToolSurLevel.iscreenHeight / 6));
        this.centerX = (float) (((ToolSurLevel.iscreenWidth * 3) / 4) + (this.dotWitdh / 2));
        this.centerY = (float) ((ToolSurLevel.iscreenHeight / 6) + (this.dotWitdh / 2));
        this.horLine = BitmapFactory.decodeResource(getResources(), R.drawable.horline);
        this.verLine = BitmapFactory.decodeResource(getResources(), R.drawable.verline);
        this.pfd = new PaintFlagsDrawFilter(0, 3);
    }

    public ToolLevelSurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = null;
        this.matrix = new Matrix();
        this.dotWitdh = 64;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(10.0f);
        this.matrix.setTranslate((float) ((ToolSurLevel.iscreenWidth * 3) / 4), (float) (ToolSurLevel.iscreenHeight / 6));
        this.centerX = (float) (((ToolSurLevel.iscreenWidth * 7) / 10) + (this.dotWitdh / 2));
        this.centerY = (float) ((ToolSurLevel.iscreenHeight / 6) + (this.dotWitdh / 2));
        this.horLine = BitmapFactory.decodeResource(getResources(), R.drawable.horline);
        this.verLine = BitmapFactory.decodeResource(getResources(), R.drawable.verline);
        this.pfd = new PaintFlagsDrawFilter(0, 3);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(this.pfd);
        canvas.rotate(degrees, this.centerX, this.centerY);
        canvas.drawBitmap(this.verLine, this.centerX - 11.0f, this.centerY - ((float) this.dotWitdh), this.paint);
        canvas.drawBitmap(this.horLine, ((float) ToolSurLevel.iscreenWidth) - this.centerX, this.centerY - 10.0f, this.paint);
    }

    public static float getDegrees() {
        return degrees;
    }

    public static void setDegrees(float degrees) {
        degrees = degrees;
    }
}

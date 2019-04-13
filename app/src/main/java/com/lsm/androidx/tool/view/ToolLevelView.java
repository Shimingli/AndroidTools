package com.lsm.androidx.tool.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.lsm.androidx.tool.R;
import com.lsm.androidx.tool.activitys.ToolLevel;


public class ToolLevelView extends View {
    public Paint paint = new Paint();
    public int shang1_X = 120;
    public int shang1_Y = 212;
    public int shang2_X;
    public int shang2_Y;
    public Bitmap shangBitmap1;
    public Bitmap shangBitmap2;
    public Bitmap xiaBitmap1;
    public Bitmap xiaBitmap2;
    public int zhong1_X = 125;
    public int zhong1_Y = 325;
    public int zhong2_X;
    public int zhong2_Y;
    public Bitmap zhongBitmap;
    public Bitmap zhongBitmap1;
    public Bitmap zhongBitmap2;
    public Bitmap zhongBitmap3;
    public int zuo1_X = 12;
    public int zuo1_Y = 320;
    public int zuo2_X;
    public int zuo2_Y;
    public Bitmap zuoBitmap1;
    public Bitmap zuoBitmap2;

    public ToolLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBitmap();
        initLocation();
    }

    private void initBitmap() {
        this.shangBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_shang11);
        this.zuoBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_zuo11);
        this.zhongBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_zhong11);
        this.zhongBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.level_zhong22);
        this.shangBitmap2 = this.zhongBitmap3;
        this.zuoBitmap2 = this.zhongBitmap3;
        this.zhongBitmap2 = this.zhongBitmap3;
        this.zhongBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.level_center);
    }

    private void initLocation() {
        this.shang1_X = ToolLevel.iscreenWidth / 4;
        this.zhong1_X = this.shang1_X;
        this.zuo1_Y = ToolLevel.iscreenHeight / 4;
        this.shang1_Y = ToolLevel.iscreenHeight / 6;
        this.zhong1_Y = this.zuo1_Y;
        this.shang2_X = (this.shang1_X + (this.shangBitmap1.getWidth() / 2)) - (this.zhongBitmap2.getWidth() / 2);
        this.shang2_Y = (this.shang1_Y + (this.shangBitmap1.getHeight() / 2)) - (this.zhongBitmap2.getHeight() / 2);
        this.zuo2_X = (this.zuo1_X + (this.zuoBitmap1.getWidth() / 2)) - (this.zhongBitmap2.getWidth() / 2);
        this.zuo2_Y = (this.zuo1_Y + (this.zuoBitmap1.getHeight() / 2)) - (this.zhongBitmap2.getHeight() / 2);
        this.zhong2_X = (this.zhong1_X + (this.zhongBitmap1.getWidth() / 2)) - (this.zhongBitmap2.getWidth() / 2);
        this.zhong2_Y = (this.zhong1_Y + (this.zhongBitmap1.getHeight() / 2)) - (this.zhongBitmap2.getHeight() / 2);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.tool_level_bg));
        this.paint.setColor(-16776961);
        this.paint.setStyle(Style.STROKE);
        canvas.drawBitmap(this.shangBitmap1, (float) this.shang1_X, (float) this.shang1_Y, this.paint);
        canvas.drawBitmap(this.zuoBitmap1, (float) this.zuo1_X, (float) this.zuo1_Y, this.paint);
        canvas.drawBitmap(this.zhongBitmap1, (float) this.zhong1_X, (float) this.zhong1_Y, this.paint);
        canvas.drawBitmap(this.shangBitmap2, (float) this.shang2_X, (float) this.shang2_Y, this.paint);
        canvas.drawBitmap(this.zuoBitmap2, (float) this.zuo2_X, (float) this.zuo2_Y, this.paint);
        canvas.drawBitmap(this.zhongBitmap2, (float) this.zhong2_X, (float) this.zhong2_Y, this.paint);
    }
}

package com.lsm.androidx.tool.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class RulerView extends SurfaceView implements Callback {
    public float CYCLE_WIDTH;
    public float DISPLAY_SIZE_BIG;
    public float DISPLAY_SIZE_SMALL;
    public float FONT_SIZE;
    public float PADDING;
    public float RADIUS_BIG;
    public float RADIUS_MEDIUM;
    public float RADIUS_SMALL;
    public float RULE_HEIGHT;
    public float RULE_SCALE;
    public int SCREEN_H;
    public int SCREEN_W;
    public float UNIT_MM;
    Paint fontPaint;
    private SurfaceHolder holder;
    int kedu;
    float lastX;
    float lineOffset;
    Paint linePaint;
    float lineX;
    Paint paint;
    float startX;
    boolean unlockLineCanvas = false;

    public int getKedu() {
        return this.kedu;
    }

    public void setKedu(int kedu) {
        this.kedu = kedu;
        draw();
    }

    public float getLineX() {
        return this.lineX;
    }

    public void setLineX(float lineX) {
        this.lineX = lineX;
        draw();
    }

    private void onTouchBegain(float x, float y) {
        this.lineOffset = Math.abs(x - this.lineX);
        if (this.lineOffset <= this.PADDING * 2.0f) {
            this.startX = x;
            this.unlockLineCanvas = true;
        }
    }

    private void onTouchMove(float x, float y) {
        if (this.unlockLineCanvas) {
            this.lineX += x - this.startX;
            if (this.lineX < this.PADDING) {
                this.lineX = this.PADDING;
            } else if (this.lineX > this.lastX) {
                this.lineX = this.lastX;
            }
            this.kedu = Math.round((this.lineX - this.PADDING) / this.UNIT_MM);
            this.startX = x;
            draw();
        }
    }

    private void onTouchDone(float x, float y) {
        this.unlockLineCanvas = false;
        this.startX = -1.0f;
        draw();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                onTouchBegain(event.getX(), event.getY());
                break;
            case 1:
            case 3:
                onTouchDone(event.getX(), event.getY());
                break;
            case 2:
                onTouchMove(event.getX(), event.getY());
                break;
        }
        return true;
    }

    private void init(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        this.RADIUS_BIG = TypedValue.applyDimension(1, 46.0f, dm);
        this.RADIUS_MEDIUM = TypedValue.applyDimension(1, 40.0f, dm);
        this.RADIUS_SMALL = TypedValue.applyDimension(1, 20.0f, dm);
        this.CYCLE_WIDTH = TypedValue.applyDimension(1, 4.0f, dm);
        this.DISPLAY_SIZE_BIG = TypedValue.applyDimension(1, 40.0f, dm);
        this.DISPLAY_SIZE_SMALL = TypedValue.applyDimension(1, 20.0f, dm);
        this.UNIT_MM = TypedValue.applyDimension(5, 1.0f, dm);
        this.RULE_HEIGHT = TypedValue.applyDimension(1, 30.0f, dm);
        this.FONT_SIZE = TypedValue.applyDimension(1, 20.0f, dm);
        this.PADDING = this.FONT_SIZE / 2.0f;
        this.SCREEN_W = dm.widthPixels;
        this.SCREEN_H = dm.heightPixels;
        this.holder = getHolder();
        this.holder.addCallback(this);
        this.paint = new Paint();
        this.paint.setColor(-14763784);
        this.linePaint = new Paint();
        this.linePaint.setColor(-14763784);
        this.linePaint.setStrokeWidth(4.0f);
        this.fontPaint = new Paint();
        this.fontPaint.setTextSize(this.FONT_SIZE);
        this.fontPaint.setAntiAlias(true);
        this.fontPaint.setColor(-14763784);
        this.lineX = this.PADDING;
        this.kedu = 0;
    }

    private void drawDisplay(Canvas canvas) {
        String cm = String.valueOf(this.kedu / 10);
        String mm = String.valueOf(this.kedu % 10);
        Paint displayPaint1 = new Paint();
        displayPaint1.setAntiAlias(true);
        displayPaint1.setColor(-14763784);
        displayPaint1.setTextSize(this.DISPLAY_SIZE_BIG);
        float cmWidth = displayPaint1.measureText(cm);
        Rect bounds1 = new Rect();
        displayPaint1.getTextBounds(cm, 0, cm.length(), bounds1);
        Paint displayPaint2 = new Paint();
        displayPaint2.setAntiAlias(true);
        displayPaint2.setColor(-10066330);
        displayPaint2.setTextSize(this.DISPLAY_SIZE_SMALL);
        float mmWidth = displayPaint2.measureText(mm);
        Rect bounds2 = new Rect();
        displayPaint2.getTextBounds(mm, 0, mm.length(), bounds2);
        canvas.drawLine(this.lineX, 0.0f, this.lineX, (float) this.SCREEN_H, this.linePaint);
        Paint cyclePaint = new Paint();
        cyclePaint.setColor(-1);
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Style.FILL);
        Paint strokPaint = new Paint();
        strokPaint.setAntiAlias(true);
        strokPaint.setColor(-6710887);
        strokPaint.setStyle(Style.STROKE);
        strokPaint.setStrokeWidth(this.CYCLE_WIDTH);
        canvas.drawCircle((float) (this.SCREEN_W / 2), (float) (this.SCREEN_H / 2), this.RADIUS_BIG, cyclePaint);
        canvas.drawCircle((float) (this.SCREEN_W / 2), (float) (this.SCREEN_H / 2), this.RADIUS_MEDIUM, cyclePaint);
        canvas.drawCircle((float) (this.SCREEN_W / 2), (float) (this.SCREEN_H / 2), this.RADIUS_BIG, strokPaint);
        strokPaint.setColor(-10066330);
        canvas.drawCircle((float) (this.SCREEN_W / 2), (float) (this.SCREEN_H / 2), this.RADIUS_MEDIUM, strokPaint);
        strokPaint.setColor(-6710887);
        canvas.drawCircle(((float) (this.SCREEN_W / 2)) + this.RADIUS_BIG, (float) (this.SCREEN_H / 2), this.RADIUS_SMALL, cyclePaint);
        canvas.drawCircle(((float) (this.SCREEN_W / 2)) + this.RADIUS_BIG, (float) (this.SCREEN_H / 2), this.RADIUS_SMALL, strokPaint);
        canvas.drawText(cm, ((float) (this.SCREEN_W / 2)) - (cmWidth / 2.0f), (float) ((this.SCREEN_H / 2) + (bounds1.height() / 2)), displayPaint1);
        canvas.drawText(mm, (((float) (this.SCREEN_W / 2)) + this.RADIUS_BIG) - (mmWidth / 2.0f), (float) ((this.SCREEN_H / 2) + (bounds2.height() / 2)), displayPaint2);
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    private void draw() {
        /*
        r13 = this;
        r12 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r11 = 0;
        r10 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r1 = 0;
        r7 = r13.holder;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r1 = r7.lockCanvas();	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = -1;
        r1.drawColor(r7);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r3 = r13.PADDING;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r2 = 0;
    L_0x0013:
        r7 = r13.SCREEN_W;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = (float) r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r8 = r13.PADDING;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r7 - r8;
        r7 = r7 - r3;
        r7 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r7 <= 0) goto L_0x0091;
    L_0x001e:
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r13.RULE_SCALE = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r2 % 5;
        if (r7 != 0) goto L_0x005e;
    L_0x0026:
        r7 = r2 & 1;
        if (r7 != 0) goto L_0x0083;
    L_0x002a:
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r13.RULE_SCALE = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r2 / 10;
        r5 = java.lang.String.valueOf(r7);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r0 = new android.graphics.Rect;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r0.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r13.fontPaint;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r6 = r7.measureText(r5);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r13.fontPaint;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r8 = 0;
        r9 = r5.length();	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7.getTextBounds(r5, r8, r9, r0);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r6 / r12;
        r7 = r3 - r7;
        r8 = r13.RULE_HEIGHT;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r9 = r13.FONT_SIZE;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r9 = r9 / r12;
        r8 = r8 + r9;
        r9 = r0.height();	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r9 = (float) r9;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r8 = r8 + r9;
        r9 = r13.fontPaint;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r1.drawText(r5, r7, r8, r9);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
    L_0x005e:
        r4 = new android.graphics.RectF;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r4.<init>();	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r3 - r10;
        r4.left = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = 0;
        r4.top = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r3 + r10;
        r4.right = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r4.top;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r8 = r13.RULE_HEIGHT;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r9 = r13.RULE_SCALE;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r8 = r8 * r9;
        r7 = r7 + r8;
        r4.bottom = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r13.paint;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r1.drawRect(r4, r7);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r13.UNIT_MM;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r3 = r3 + r7;
        r2 = r2 + 1;
        goto L_0x0013;
    L_0x0083:
        r7 = 1061158912; // 0x3f400000 float:0.75 double:5.24282163E-315;
        r13.RULE_SCALE = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        goto L_0x005e;
    L_0x0088:
        r7 = move-exception;
        if (r1 == 0) goto L_0x0090;
    L_0x008b:
        r7 = r13.holder;
        r7.unlockCanvasAndPost(r1);
    L_0x0090:
        return;
    L_0x0091:
        r7 = r13.UNIT_MM;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r7 = r3 - r7;
        r13.lastX = r7;	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        r13.drawDisplay(r1);	 Catch:{ Exception -> 0x0088, all -> 0x00a2 }
        if (r1 == 0) goto L_0x0090;
    L_0x009c:
        r7 = r13.holder;
        r7.unlockCanvasAndPost(r1);
        goto L_0x0090;
    L_0x00a2:
        r7 = move-exception;
        if (r1 == 0) goto L_0x00aa;
    L_0x00a5:
        r8 = r13.holder;
        r8.unlockCanvasAndPost(r1);
    L_0x00aa:
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.shenfei.tools.view.RulerView.draw():void");
    }

    public RulerView(Context context) {
        super(context);
        init(context);
    }

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        new Thread() {
            public void run() {
                RulerView.this.draw();
            }
        }.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}

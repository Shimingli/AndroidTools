package com.lsm.androidx.tool.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class CycleRulerView extends View {
    public float CYCLE_WIDTH;
    public float DISPLAY_SIZE_BIG;
    public float DISPLAY_SIZE_SMALL;
    public float RADIUS_BIG;
    public float RADIUS_MEDIUM;
    public float RADIUS_SMALL;
    private float fontSize;
    private int height;
    private int kedu;
    private float offset;
    private float padding;
    private Coordinate point;
    private float radius;
    private int width;

    class Coordinate {
        private float x;
        private float y;

        public Coordinate(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return this.x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return this.y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String toString() {
            return "[x:" + this.x + ", y:" + this.y + "]";
        }
    }

    private Path getTextPath(String text, Paint paint, double degree, float r) {
        double pathDegree = Math.abs(90.0d - degree);
        float textWidth = paint.measureText(text);
        float y = Math.abs((float) (((double) textWidth) * Math.sin((pathDegree / 180.0d) * 3.141592653589793d)));
        float x = Math.abs((float) (((double) textWidth) * Math.cos((pathDegree / 180.0d) * 3.141592653589793d)));
        Coordinate coordinate = getCoordinate(r, degree);
        Coordinate start = new Coordinate();
        Coordinate end = new Coordinate();
        if (degree < 90.0d) {
            end.setX((-coordinate.getX()) + (x / 2.0f));
            end.setY((-coordinate.getY()) - (y / 2.0f));
            start.setX((-coordinate.getX()) - (x / 2.0f));
            start.setY((-coordinate.getY()) + (y / 2.0f));
        } else {
            end.setX((-coordinate.getX()) + (x / 2.0f));
            end.setY((-coordinate.getY()) + (y / 2.0f));
            start.setX((-coordinate.getX()) - (x / 2.0f));
            start.setY((-coordinate.getY()) - (y / 2.0f));
        }
        Path path = new Path();
        path.moveTo(start.getX(), start.getY());
        path.lineTo(end.getX(), end.getY());
        return path;
    }

    private void drawDisplay(Canvas canvas) {
        String cm = String.valueOf(this.kedu);
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
        displayPaint2.getTextBounds(mm, 0, mm.length(), new Rect());
        Paint cyclePaint = new Paint();
        cyclePaint.setColor(-1);
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Style.FILL);
        Paint strokPaint = new Paint();
        strokPaint.setAntiAlias(true);
        strokPaint.setColor(-6710887);
        strokPaint.setStyle(Style.STROKE);
        strokPaint.setStrokeWidth(this.CYCLE_WIDTH);
        canvas.drawCircle((float) (this.width / 2), (float) ((this.height * 3) / 5), this.RADIUS_BIG, cyclePaint);
        canvas.drawCircle((float) (this.width / 2), (float) ((this.height * 3) / 5), this.RADIUS_MEDIUM, cyclePaint);
        canvas.drawCircle((float) (this.width / 2), (float) ((this.height * 3) / 5), this.RADIUS_BIG, strokPaint);
        strokPaint.setColor(-10066330);
        canvas.drawCircle((float) (this.width / 2), (float) ((this.height * 3) / 5), this.RADIUS_MEDIUM, strokPaint);
        strokPaint.setColor(-6710887);
        canvas.drawText(cm, ((float) (this.width / 2)) - (cmWidth / 2.0f), (float) (((this.height * 3) / 5) + (bounds1.height() / 2)), displayPaint1);
    }

    private Coordinate getCoordinate(float r, double degree) {
        return new Coordinate((float) (((double) r) * Math.cos((degree / 180.0d) * 3.141592653589793d)), (float) (((double) r) * Math.sin((degree / 180.0d) * 3.141592653589793d)));
    }

    private void onTouchBegain(Coordinate coordinate) {
        caculatePoint(coordinate);
    }

    private void onTouchMove(Coordinate coordinate) {
        caculatePoint(coordinate);
    }

    private void onTouchDone(Coordinate coordinate) {
    }

    private void caculatePoint(Coordinate coordinate) {
        float mx = ((float) this.width) / 2.0f;
        float my = ((float) this.height) - this.offset;
        if (coordinate.getY() > my) {
            coordinate.setY(my);
        }
        float dx = coordinate.getX() - mx;
        float dy = coordinate.getY() - my;
        double r = Math.sqrt((double) ((dx * dx) + (dy * dy)));
        this.point = new Coordinate((float) ((((double) dx) / r) * ((double) this.radius)), (float) ((((double) dy) / r) * ((double) this.radius)));
        this.kedu = (int) Math.round((Math.atan((double) (dy / dx)) / 3.141592653589793d) * 180.0d);
        if (dx >= 0.0f) {
            this.kedu += 180;
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                onTouchBegain(new Coordinate(event.getX(), event.getY()));
                break;
            case 1:
            case 3:
                onTouchDone(new Coordinate(event.getX(), event.getY()));
                break;
            case 2:
                onTouchMove(new Coordinate(event.getX(), event.getY()));
                break;
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        this.width = getWidth();
        this.height = getHeight();
        this.radius = ((float) this.width) / 2.0f;
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        this.offset = (((float) this.height) - (((float) this.width) / 2.0f)) / 2.0f;
        canvas.drawArc(new RectF(0.0f, this.offset, (float) this.width, ((float) this.width) + this.offset), 180.0f, 180.0f, true, paint);
        Paint xpaint = new Paint();
        xpaint.setAntiAlias(true);
        xpaint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        xpaint.setStyle(Style.FILL);
        xpaint.setColor(1610612735);
        canvas.drawRect(new RectF(0.0f, 0.0f, (float) this.width, (float) this.height), xpaint);
        canvas.save();
        canvas.translate((float) (this.width / 2), ((float) this.height) - this.offset);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(1879048191);
        paint2.setStrokeWidth(2.0f);
        Paint degreePaint = new Paint();
        degreePaint.setAntiAlias(true);
        degreePaint.setTextSize(this.fontSize);
        degreePaint.setColor(1879048191);
        for (int i = 1; i < 180; i++) {
            Coordinate coordinate = getCoordinate(this.radius, (double) i);
            float x = coordinate.getX();
            float y = coordinate.getY();
            float r = this.radius - (this.padding / 2.0f);
            if (i % 5 == 0) {
                if ((i & 1) == 0) {
                    r = this.radius - this.padding;
                    String text = String.valueOf(i);
                    canvas.drawTextOnPath(text, getTextPath(text, degreePaint, (double) i, (this.radius - this.padding) - ((this.fontSize * 5.0f) / 4.0f)), 0.0f, 0.0f, degreePaint);
                } else {
                    r = this.radius - ((this.padding * 3.0f) / 4.0f);
                }
            }
            Coordinate coordinate1 = getCoordinate(r, (double) i);
            Canvas canvas2 = canvas;
            canvas2.drawLine(-coordinate1.getX(), -coordinate1.getY(), -x, -y, paint2);
        }
        Paint arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(1879048191);
        arcPaint.setStrokeWidth(2.0f);
        arcPaint.setStyle(Style.STROKE);
        RectF oval1 = new RectF();
        oval1.left = ((float) (-this.width)) / 2.0f;
        oval1.top = (this.offset * 2.0f) - ((float) this.height);
        oval1.right = ((float) this.width) / 2.0f;
        oval1.bottom = ((float) this.height) - (this.offset * 2.0f);
        RectF oval2 = new RectF();
        oval2.left = (((float) (-this.width)) / 2.0f) / 4.0f;
        oval2.top = ((this.offset * 2.0f) - ((float) this.height)) / 4.0f;
        oval2.right = (((float) this.width) / 2.0f) / 4.0f;
        oval2.bottom = (((float) this.height) - (this.offset * 2.0f)) / 4.0f;
        RectF oval3 = new RectF();
        oval3.left = (((float) (-this.width)) / 2.0f) / 2.0f;
        oval3.top = ((this.offset * 2.0f) - ((float) this.height)) / 2.0f;
        oval3.right = (((float) this.width) / 2.0f) / 2.0f;
        oval3.bottom = (((float) this.height) - (this.offset * 2.0f)) / 2.0f;
        canvas.drawArc(oval1, 180.0f, 180.0f, true, arcPaint);
        canvas.drawLine(0.0f, 0.0f, 0.0f, -this.padding, paint2);
        if (this.point != null) {
            canvas.drawLine(0.0f, 0.0f, this.point.getX(), this.point.getY(), paint2);
        }
        canvas.restore();
        drawDisplay(canvas);
    }

    private void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        this.padding = TypedValue.applyDimension(1, 15.0f, dm);
        this.fontSize = TypedValue.applyDimension(1, 11.0f, dm);
        this.RADIUS_BIG = TypedValue.applyDimension(1, 46.0f, dm);
        this.RADIUS_MEDIUM = TypedValue.applyDimension(1, 40.0f, dm);
        this.RADIUS_SMALL = TypedValue.applyDimension(1, 20.0f, dm);
        this.CYCLE_WIDTH = TypedValue.applyDimension(1, 4.0f, dm);
        this.DISPLAY_SIZE_BIG = TypedValue.applyDimension(1, 40.0f, dm);
        this.DISPLAY_SIZE_SMALL = TypedValue.applyDimension(1, 20.0f, dm);
    }

    public CycleRulerView(Context context) {
        super(context);
        init(context);
    }

    public CycleRulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CycleRulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
}

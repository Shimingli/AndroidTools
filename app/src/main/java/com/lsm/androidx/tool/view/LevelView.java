package com.lsm.androidx.tool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.example.shenfei.tools.R;

public class LevelView extends View {
    private PointF bubblePoint;
    private PointF centerPnt = new PointF();
    private int mBubbleColor;
    private Paint mBubblePaint;
    private float mBubbleRadius;
    private int mBubbleRuleColor;
    private Paint mBubbleRulePaint;
    private float mBubbleRuleRadius;
    private float mBubbleRuleWidth;
    private int mHorizontalColor;
    private float mLimitCircleWidth;
    private int mLimitColor;
    private Paint mLimitPaint;
    private float mLimitRadius = 0.0f;
    private double pitchAngle = -90.0d;
    private double rollAngle = -90.0d;
    private Vibrator vibrator;

    public LevelView(Context context) {
        super(context);
        init(null, 0);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LevelView, defStyle, 0);
        this.mBubbleRuleColor = a.getColor(4, this.mBubbleRuleColor);
        this.mBubbleColor = a.getColor(7, this.mBubbleColor);
        this.mLimitColor = a.getColor(1, this.mLimitColor);
        this.mHorizontalColor = a.getColor(8, this.mHorizontalColor);
        this.mLimitRadius = a.getDimension(0, this.mLimitRadius);
        this.mBubbleRadius = a.getDimension(3, this.mBubbleRadius);
        this.mLimitCircleWidth = a.getDimension(2, this.mLimitCircleWidth);
        this.mBubbleRuleWidth = a.getDimension(5, this.mBubbleRuleWidth);
        this.mBubbleRuleRadius = a.getDimension(6, this.mBubbleRuleRadius);
        a.recycle();
        this.mBubblePaint = new Paint();
        this.mBubblePaint.setColor(this.mBubbleColor);
        this.mBubblePaint.setStyle(Style.FILL);
        this.mBubblePaint.setAntiAlias(true);
        this.mLimitPaint = new Paint();
        this.mLimitPaint.setStyle(Style.STROKE);
        this.mLimitPaint.setColor(this.mLimitColor);
        this.mLimitPaint.setStrokeWidth(this.mLimitCircleWidth);
        this.mLimitPaint.setAntiAlias(true);
        this.mBubbleRulePaint = new Paint();
        this.mBubbleRulePaint.setColor(this.mBubbleRuleColor);
        this.mBubbleRulePaint.setStyle(Style.STROKE);
        this.mBubbleRulePaint.setStrokeWidth(this.mBubbleRuleWidth);
        this.mBubbleRulePaint.setAntiAlias(true);
        this.vibrator = (Vibrator) getContext().getSystemService("vibrator");
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculateCenter(widthMeasureSpec, heightMeasureSpec);
    }

    private void calculateCenter(int widthMeasureSpec, int heightMeasureSpec) {
        int center = Math.min(MeasureSpec.makeMeasureSpec(widthMeasureSpec, 0), MeasureSpec.makeMeasureSpec(heightMeasureSpec, 0)) / 2;
        this.centerPnt.set((float) center, (float) center);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean isCenter = isCenter(this.bubblePoint);
        int limitCircleColor = isCenter ? this.mHorizontalColor : this.mLimitColor;
        int bubbleColor = isCenter ? this.mHorizontalColor : this.mBubbleColor;
        if (isCenter) {
            this.vibrator.vibrate(10);
        }
        this.mBubblePaint.setColor(bubbleColor);
        this.mLimitPaint.setColor(limitCircleColor);
        canvas.drawCircle(this.centerPnt.x, this.centerPnt.y, this.mBubbleRuleRadius, this.mBubbleRulePaint);
        canvas.drawCircle(this.centerPnt.x, this.centerPnt.y, this.mLimitRadius, this.mLimitPaint);
        drawBubble(canvas);
    }

    private boolean isCenter(PointF bubblePoint) {
        if (bubblePoint != null && Math.abs(bubblePoint.x - this.centerPnt.x) < 1.0f && Math.abs(bubblePoint.y - this.centerPnt.y) < 1.0f) {
            return true;
        }
        return false;
    }

    private void drawBubble(Canvas canvas) {
        if (this.bubblePoint != null) {
            canvas.drawCircle(this.bubblePoint.x, this.bubblePoint.y, this.mBubbleRadius, this.mBubblePaint);
        }
    }

    private PointF convertCoordinate(double rollAngle, double pitchAngle, double radius) {
        double scale = radius / Math.toRadians(90.0d);
        return new PointF((float) (((double) this.centerPnt.x) - (-(rollAngle * scale))), (float) (((double) this.centerPnt.y) - (-(pitchAngle * scale))));
    }

    public void setAngle(double rollAngle, double pitchAngle) {
        this.pitchAngle = pitchAngle;
        this.rollAngle = rollAngle;
        float limitRadius = this.mLimitRadius - this.mBubbleRadius;
        this.bubblePoint = convertCoordinate(rollAngle, pitchAngle, (double) this.mLimitRadius);
        outLimit(this.bubblePoint, limitRadius);
        if (outLimit(this.bubblePoint, limitRadius)) {
            onCirclePoint(this.bubblePoint, (double) limitRadius);
        }
        invalidate();
    }

    private boolean outLimit(PointF bubblePnt, float limitRadius) {
        if ((((bubblePnt.x - this.centerPnt.x) * (bubblePnt.x - this.centerPnt.x)) + ((this.centerPnt.y - bubblePnt.y) * (this.centerPnt.y - bubblePnt.y))) - (limitRadius * limitRadius) > 0.0f) {
            return true;
        }
        return false;
    }

    private PointF onCirclePoint(PointF bubblePnt, double limitRadius) {
        double azimuth = Math.atan2((double) (bubblePnt.y - this.centerPnt.y), (double) (bubblePnt.x - this.centerPnt.x));
        if (azimuth < 0.0d) {
            azimuth += 6.283185307179586d;
        }
        bubblePnt.set((float) (((double) this.centerPnt.x) + (Math.cos(azimuth) * limitRadius)), (float) (((double) this.centerPnt.y) + (Math.sin(azimuth) * limitRadius)));
        return bubblePnt;
    }

    public double getPitchAngle() {
        return this.pitchAngle;
    }

    public double getRollAngle() {
        return this.rollAngle;
    }
}

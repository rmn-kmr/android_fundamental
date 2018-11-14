package com.kmr.rmn.custombottomnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

public class CurvedNavigationView extends BottomNavigationView {

    Path mPath;
    Paint mPaint;
    float mNavigationBarWidth, mNavigationBarHeight;
    private final int CURVE_CIRCLE_RADIUS = 256 / 2;

    // the coordinates of the first curve
    private Point mFirstCurveStartPoint = new Point();
    private Point mFirstCurveEndPoint = new Point();
    private Point mFirstCurveControlPoint1 = new Point();
    private Point mFirstCurveControlPoint2 = new Point();

    @SuppressWarnings("FieldCanBeLocal")
    private Point mSecondCurveStartPoint = new Point();
    private Point mSecondCurveEndPoint = new Point();
    private Point mSecondCurveControlPoint1 = new Point();
    private Point mSecondCurveControlPoint2 = new Point();

    public CurvedNavigationView(Context context) {
        super(context);
        init();
    }

    public CurvedNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurvedNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mNavigationBarHeight =getHeight();
        mNavigationBarWidth =getWidth();

        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set((int) ((mNavigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3)), 0);
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set((int) (mNavigationBarWidth / 2), CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint;
        mSecondCurveEndPoint.set((int) ((mNavigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3)), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4), mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS, mFirstCurveEndPoint.y);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS, mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)), mSecondCurveEndPoint.y);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0,0);
        mPath.lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y);
        mPath.cubicTo(
                mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x,mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x,mFirstCurveEndPoint.y);

        mPath.cubicTo(mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y);

        mPath.lineTo(mNavigationBarWidth,0);
        mPath.lineTo(mNavigationBarWidth,  mNavigationBarHeight);
        mPath.lineTo(0,mNavigationBarHeight);
        mPath.close();

        canvas.drawPath(mPath,mPaint);

    }

    public void init(){

        mPath = new Path();
        mPaint =new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);
    }
}

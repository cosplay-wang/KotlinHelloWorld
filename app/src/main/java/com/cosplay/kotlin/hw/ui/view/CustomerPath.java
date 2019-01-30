package com.cosplay.kotlin.hw.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author:wangzhiwei on 2018/12/13.
 * Email :wangzhiwei@moyi365.com
 * Description: path的相关方法
 */
public class CustomerPath extends View {
    Path customerPath;
    Path partCustomerPath;
    Paint ustomerPaint;
    Paint partCustomerPaint;
    PathMeasure pathMeasure;
    float partLength;
    float percent;
    ValueAnimator valueAnimator;
    float[] points = new float[2];
    public CustomerPath(Context context) {
        this(context,null);
    }

    public CustomerPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        customerPath = new Path();
        customerPath.moveTo(200,200);
        customerPath.lineTo(300,400);
        customerPath.lineTo(400,200);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(customerPath,false);
        partLength = pathMeasure.getLength();

        partCustomerPath = new Path();

        ustomerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ustomerPaint.setStyle(Paint.Style.STROKE);
        ustomerPaint.setStrokeWidth(20);
        ustomerPaint.setColor(Color.RED);

        partCustomerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        partCustomerPaint.setStyle(Paint.Style.STROKE);
        partCustomerPaint.setStrokeWidth(20);
        partCustomerPaint.setColor(Color.BLACK);

        valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    public CustomerPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        partCustomerPath.reset();
        // 硬件加速的BUG
        partCustomerPath.lineTo(0,0);
        float stop = partLength * percent;

        pathMeasure.getSegment(0, stop, partCustomerPath, true);//得到的是路径，可以直接画
     //   canvas.drawPath(customerPath,ustomerPaint);
        canvas.drawPath(partCustomerPath, partCustomerPaint);//绘制跟住跑的圆
       // canvas.drawCircle(points[0],points[1],30, partCirclePaint);

    }
}

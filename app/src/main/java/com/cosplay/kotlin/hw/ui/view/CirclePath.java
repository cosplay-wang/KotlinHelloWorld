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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Author:wangzhiwei on 2018/12/13.
 * Email :wangzhiwei@moyi365.com
 * Description: path的相关方法
 */
public class CirclePath extends View {
    Path circlePath;
    Path partCirclePath;
    Paint circlePaint;
    Paint partCirclePaint;
    PathMeasure pathMeasure;
    float partCircleLength;
    float percent;
    ValueAnimator valueAnimator;
    float[] points = new float[2];
    public CirclePath(Context context) {
        this(context,null);
    }

    public CirclePath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circlePath = new Path();
        circlePath.addCircle(200,200,100,Path.Direction.CW);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(circlePath,false);
        partCircleLength = pathMeasure.getLength();

        partCirclePath = new Path();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20);
        circlePaint.setColor(Color.RED);

        partCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        partCirclePaint.setStyle(Paint.Style.STROKE);
        partCirclePaint.setStrokeWidth(20);
        partCirclePaint.setColor(Color.BLACK);

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

    public CirclePath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        partCirclePath.reset();
        // 硬件加速的BUG
        partCirclePath.lineTo(0,0);
        float stop = partCircleLength * percent;

        pathMeasure.getPosTan(stop,points,null);//得到的是点的坐标
        float start = (float) (stop - ((0.5 - Math.abs(percent - 0.5)) * partCircleLength));
        pathMeasure.getSegment(start, stop, partCirclePath, true);//得到的是路径，可以直接画
        //canvas.drawPath(circlePath,circlePaint);
        canvas.drawPath(partCirclePath, partCirclePaint);//绘制跟住跑的圆
       // canvas.drawCircle(points[0],points[1],30, partCirclePaint);

    }
}

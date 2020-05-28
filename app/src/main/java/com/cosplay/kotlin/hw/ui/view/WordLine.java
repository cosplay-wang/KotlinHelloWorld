package com.cosplay.kotlin.hw.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cosplay.kotlin.hw.util.pkslide.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author:wangzhiwei on 2019/8/28.
 * Email :wangzhiwei@moyi365.com
 * Description: 调研音标学习的列表 曲线
 */
public class WordLine extends View {
    List<String> wordList;
    Path circlePath;
    Paint circlePaint;
    Paint textPaint;
    int height = 100;
    List<int[]> pointList = new ArrayList<>();

    public WordLine(Context context) {
        super(context);

        init();
    }

    private void init() {
        // circlePaint = new Paint();
        circlePath = new Path();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.RED);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setColor(Color.BLACK);
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);


    }

    public WordLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WordLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setDatalist(List<String> wordList) {
        this.wordList = wordList;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (wordList != null) {
            setMeasuredDimension(SlidingUpPanelLayout.LayoutParams.MATCH_PARENT, 300 * (wordList.size() - 1));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (wordList != null) {

            for (int i = 0; i < wordList.size(); i++) {
                circlePath.reset();
                circlePath.moveTo(400, height);
                height = height + 500;
                int X = i % 2 == 0 ? -200 : 1000;
                circlePath.quadTo(X, height - 250, 400, height);
                PathMeasure measure = new PathMeasure(circlePath, false);

                int arg = 0;
                int j = 0;
                while (arg >= -180) {
                    float[] coords = new float[]{0f, 0f};
                    float[] tan = new float[]{0f, 0f};
                    measure.getPosTan((measure.getLength() / 5) * j, coords, tan);
                    pointList.add(new int[]{(int) coords[0], (int) coords[1]});
                    canvas.drawCircle(coords[0], coords[1], 4, circlePaint);
                    if ((coords[0] + 50f) >= 600) {
                        canvas.drawText(wordList.get(i), coords[0] + 50, (coords[1] + 20f), textPaint);
                    } else {
                        if ((coords[0] + 50f) >= 200) {
                            canvas.drawText(wordList.get(i), coords[0], (coords[1] + 50f), textPaint);
                        }else {
                            canvas.drawText(wordList.get(i), coords[0]-50f, (coords[1] ), textPaint);
                        }
                    }
                    arg = arg - 36;
                    j++;
                }
                canvas.drawCircle(400, height, 4, circlePaint);
                canvas.drawPath(circlePath, circlePaint);

            }

        }
    }
  public  interface  onClickListener{
        void onClick(int position,String text);
  }
  private onClickListener onClickListener;

    public void setOnClickListener(WordLine.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (x + getLeft() < getRight() && y + getTop() < getBottom()) {
                  //  onClickListener.onClick(this);
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}

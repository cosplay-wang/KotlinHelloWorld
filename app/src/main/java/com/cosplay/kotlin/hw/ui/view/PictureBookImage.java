package com.cosplay.kotlin.hw.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cosplay.kotlin.hw.R;

/**
 * Author:wangzhiwei on 2018/10/8.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class PictureBookImage extends ImageView {
    Paint paint1, paint2, paint3;
    Bitmap bitmap1, bitmap2, bitmap3;
    RectF rectF3,rectF2,rectF1;
    public PictureBookImage(Context context) {
        this(context, null);

    }

    public PictureBookImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureBookImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);

    }

    public void setBitmap(Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3) {
        this.bitmap1 = bitmap1;
        this.bitmap2 = bitmap2;
        this.bitmap3 = bitmap3;
        invalidate();
    }

    void init() {
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(R.color.paint1_color));
        paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.paint2_color));
        paint3 = new Paint();
        paint3.setColor(getResources().getColor(R.color.paint3_color));
        rectF3 = new RectF((float)(300*0.2),0,(float)(300*0.8),300);
        rectF2 = new RectF((float)(300*0.1),0,(float)(300*0.9),(float)(300*0.9));
        rectF1 = new RectF(0,0,300,(float)(300*0.8));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap1 != null) {
            canvas.drawBitmap(bitmap1,null,rectF3,paint3);
        }
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2,null,rectF2,paint2);
        }
        if (bitmap3 != null) {
            canvas.drawBitmap(bitmap1,null,rectF1,paint1);
        }
    }
}

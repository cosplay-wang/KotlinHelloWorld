package com.cosplay.kotlin.hw.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author:wangzhiwei on 2018/8/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */

public class FontTextview extends TextView {
    public FontTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontTextview(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        AssetManager assets = context.getAssets();
        Typeface createFromAsset = Typeface.createFromAsset(assets, "fonts/fangzhen.ttf");
        setTypeface(createFromAsset);
    }
}

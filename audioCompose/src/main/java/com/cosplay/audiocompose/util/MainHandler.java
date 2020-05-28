package com.cosplay.audiocompose.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Author:wangzhiwei on 2019/6/13.
 * Email :wangzhiwei@moyi365.com
 * Description:工具类，主线程的切换
 */
public class MainHandler extends Handler {
    private static volatile MainHandler mInstance;

    private MainHandler() {
        super(Looper.getMainLooper());
    }

    public static MainHandler getInstance() {
        if (mInstance == null) {
            synchronized (MainHandler.class) {
                if (mInstance == null) {
                    mInstance = new MainHandler();
                }
            }
        }
        return mInstance;
    }

}

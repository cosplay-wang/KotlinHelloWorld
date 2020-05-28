package com.cosplay.audiocompose;

import android.app.Application;
import android.content.Context;

/**
 * Author:wangzhiwei on 2020/1/3.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AudioAPP extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}

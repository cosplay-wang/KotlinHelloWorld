package com.cosplay.kotlin.hw.util;

import android.content.Context;

/**
 * Author:wangzhiwei on 2018/11/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class SPUtils {
    public static final String SP_FINGER = "FINGER";
    public static final String SP_DENSITY = "DENSITY";
    public static String getString(Context context, String id) {
        return context.getSharedPreferences(SP_FINGER, 0).getString(id, "");
    }


    public static void setString(Context context, String id, String json) {
        context.getSharedPreferences(SP_FINGER, 0).edit().putString(id, json).commit();
    }
    public static float getFloat(Context context, String id) {
        return context.getSharedPreferences(SP_DENSITY, 0).getFloat(id, -100f);
    }


    public static void setFloat(Context context, String id, Float json) {
        context.getSharedPreferences(SP_DENSITY, 0).edit().putFloat(id, json).commit();
    }
}

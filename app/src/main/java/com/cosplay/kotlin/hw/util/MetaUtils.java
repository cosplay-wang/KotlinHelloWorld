package com.cosplay.kotlin.hw.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.cosplay.kotlin.hw.BuildConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * Author:wangzhiwei on 2018/11/23.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class MetaUtils {
    public static String getMetaDataValue(Context context, String channelKey) {
        String ret ="";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            ret = appInfo.metaData.getString(channelKey);
            Set<String> hhSet =  appInfo.metaData.keySet();
            Iterator<String> it = hhSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                Log.e("aaaaaaa",str + BuildConfig.url);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

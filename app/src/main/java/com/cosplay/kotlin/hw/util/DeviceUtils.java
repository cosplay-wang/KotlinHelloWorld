package com.cosplay.kotlin.hw.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Author:wangzhiwei on 2018/11/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class DeviceUtils {
    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        Log.d("try", result ? "有SIM卡" : "无SIM卡");
        return result;

    }
    public static boolean isPad(Context context) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            Log.d("try", "paddddd");
            return true;
        }else {
            Log.d("try", "phoneeee");
            return false;
        }

    }
//    @Deprecated
//    public boolean test(Context context) throws Throwable {
//        TelephonyManager service = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return !TextUtils.isEmpty(service.getDeviceId()) || !TextUtils.isEmpty(service.getSubscriberId());
//        if (service.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
//            return true;
//        } else {
//            return !TextUtils.isEmpty(service.getDeviceId())
//                    || !TextUtils.isEmpty(service.getSubscriberId());
//        }
//    }
}

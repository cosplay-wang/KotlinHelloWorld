package com.cosplay.kotlin.hw.util

import android.content.Context
import com.zhuge.analysis.stat.ZhugeSDK
import org.json.JSONObject

/**
 * Author:wangzhiwei on 2018/9/14.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
 fun track( context:Context, key:String,value:JSONObject){
    ZhugeSDK.getInstance().track(context, key, value)
}

fun startTrack( key:String){

    ZhugeSDK.getInstance().startTrack(key)

}
fun endTrack( key:String,value:JSONObject){

    ZhugeSDK.getInstance().endTrack( key, value)

}
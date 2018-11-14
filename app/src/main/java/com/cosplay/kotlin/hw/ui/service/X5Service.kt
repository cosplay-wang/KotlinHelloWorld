package com.cosplay.kotlin.hw.ui.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.tencent.smtt.sdk.QbSdk

class X5Service : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        initX5Web()
    }

    var cb:QbSdk.PreInitCallback  = object:QbSdk.PreInitCallback{
        override fun  onViewInitFinished(arg0:Boolean) {
              Log.e("x55555555",arg0.toString())
        }
        override fun  onCoreInitFinished() {
            Log.e("x55555555","yyyyyyyyyyyy")
        }
    }
     fun initX5Web() {
        if (!QbSdk.isTbsCoreInited()) {
            // 设置X5初始化完成的回调接口
            QbSdk.preInit(getApplicationContext(), null)
        }
        QbSdk.initX5Environment(getApplicationContext(), cb)
    }




}

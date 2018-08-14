package com.cosplay.kotlin.hw

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient

/**
 * Author:wangzhiwei on 2018/8/14.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class APP :Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        okhttpClient = OkHttpClient.Builder().build()
    }
    companion object {
        private lateinit var okhttpClient : OkHttpClient
        private lateinit  var instance : Context
        fun getContext(): Context {
            return instance
        }
        fun getOkHttpClient() : OkHttpClient {
            return okhttpClient
        }
    }



}
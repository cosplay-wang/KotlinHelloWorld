package com.cosplay.kotlin.hw.util

import com.cosplay.kotlin.hw.APP
import okhttp3.Call
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException


/**
 * Author:wangzhiwei on 2018/8/14.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class HttpUtils {
    companion object {
        @Throws(IOException::class)
        fun okHttpRequestCall(url: String, body: RequestBody?): Call {
            val client = APP.getOkHttpClient()
            val builder = Request.Builder()
            if (body != null) {
                builder.method("POST", body)
            }
            val call = client.newCall(builder.url(url).build())
            return call

        }
        @Throws(IOException::class)
        fun okHttpRequest(url: String, body: RequestBody?): Response {
            val client = APP.getOkHttpClient()
            val builder = Request.Builder()
            if (body != null) {
                builder.method("POST", body)
            }
            val call = client.newCall(builder.url(url).build())
            return call.execute()

        }

    }


}
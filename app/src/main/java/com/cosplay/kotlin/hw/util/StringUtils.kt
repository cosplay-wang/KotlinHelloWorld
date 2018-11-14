package com.cosplay.kotlin.hw.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * Author:wangzhiwei on 2018/9/13.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
fun String.toMD5():String{
//    try {
//        var  md5: MessageDigest
//        val bs = md5.digest(this.getBytes("UTF-8"))
//        val sb = StringBuilder(40)
//        for (x in bs) {
//            if (x and 0xff shr 4 == 0) {
//                sb.append("0").append(Integer.toHexString(x and 0xff))
//            } else {
//                sb.append(Integer.toHexString(x and 0xff))
//            }
//        }
//        return sb.toString()
//    } catch (e: UnsupportedEncodingException) {
//        throw RuntimeException(e)
//    }


    return this
}
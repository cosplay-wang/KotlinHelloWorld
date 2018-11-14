package com.cosplay.kotlin.hw.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * Author:wangzhiwei on 2018/9/13.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
fun Context.showToast(message: String) : Toast {
    var toast : Toast = Toast.makeText(this,message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
    return toast
}
package com.cosplay.kotlin.hw

import android.app.Activity
import android.os.Bundle

/**
 * Author:wangzhiwei on 2018/8/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class Main2Activity : Activity() {
    var  sHello : String = "qwe"
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sHello = "hgda"
    }

}
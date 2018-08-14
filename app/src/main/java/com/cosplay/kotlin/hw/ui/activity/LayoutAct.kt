package com.cosplay.kotlin.hw.ui.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.layout_act.*

/**
 * Author:wangzhiwei on 2018/8/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class LayoutAct : Activity() {
    lateinit var handler :Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_act)
        tv_layout.setText(R.string.abc_action_bar_home_description)
        handler = object :Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
            }
        }
        handler.postDelayed(Runnable() {
            kotlin.run {
                iv_layout.setImageResource(R.drawable.rv_item_line);
                tv_layout.setTextColor(Color.RED)
                tv_layout.setTextSize(25f);
            }
        },2000)

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
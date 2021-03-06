package com.cosplay.kotlin.hw.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.cosplay.kotlin.hw.R

/**
 * Author:wangzhiwei on 2018/8/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class Handler2ThreadAct :Activity() {
    lateinit var handler :Handler
    lateinit var thread:Thread
    val context : Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        thread.start()

    }
    fun initData(){
        handler = object : Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                Toast.makeText(context,"thread.sleep==3000",Toast.LENGTH_SHORT).show()
            }
        }
        thread = Thread(Runnable {
            kotlin.run {
                Thread.sleep(3000)
                handler.sendEmptyMessage(1)
            }
        })
    }
}
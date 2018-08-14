package com.cosplay.kotlin.hw.ui.fragment

import android.os.Handler
import android.os.Message
import android.util.Log
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.second_fragment_layout.*

/**
 * Author:wangzhiwei on 2018/8/13.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class SecondFragment : BaseFragment() {

    var handler : Handler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            tv_frag_second.text = getString(R.string.frag_loading_finish)
        }
    }
    override fun onInVisiable() {
        Log.e("BaseFragment","SecondFragment"+"onInVisiable")
    }

    override fun onVisiable() {
        tv_frag_second.text = getString(R.string.second_frag_name)
        Log.e("BaseFragment","SecondFragment"+"onVisiable")
      //  tv_frag_second.text = getString(R.string.second_frag_name)
    }

    override fun onFirstInVisiable() {
        Log.e("BaseFragment","SecondFragment"+"onFirstInVisiable")

    }

    override fun onFirstUserVisible() {
        Log.e("BaseFragment","SecondFragment"+"onFirstUserVisible")
        loadData()
    }

    override fun loadData() {
        handler.sendEmptyMessageDelayed(1,3000)
    }

    override fun getLayoutID(): Int {
        return R.layout.second_fragment_layout
    }
}
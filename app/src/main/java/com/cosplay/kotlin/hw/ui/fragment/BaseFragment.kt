package com.cosplay.kotlin.hw.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author:wangzhiwei on 2018/8/13.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
abstract class BaseFragment : Fragment() {


    /**
     * 只适用于viewpager的fragment
     */
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        isUserVisible = isVisibleToUser
//        if(isUserVisible){
//            if(!isViewCreate){
//                loadData()
//            }
//        }else{
//            onInVisiable()
//        }
//        if(isUserVisible && isViewCreate && !isLoadUI){
//            loadUi()
//            isLoadUI = true
//        }
//    }

    private var isUserVisiable: Boolean = false
    private var isFirstVisiable: Boolean = true
    private var isFirstInVisiable: Boolean = true
    private var isPrepared: Boolean = false
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isUserVisiable = !hidden
        if (isUserVisiable) {
            if (isFirstVisiable) {
                initPrepare()
                isFirstVisiable = false
            } else {
                onVisiable()
            }
        } else {
            if (isFirstInVisiable) {
                onFirstInVisiable()
                isFirstInVisiable = false
            } else {
                onInVisiable()
            }
        }

    }

    /**
     * 初始化布局和事件
     */
    @Synchronized private fun initPrepare() {

        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    /**
     * 第一次不可见，加载完 commit后
     */
    abstract fun onFirstInVisiable()

    /**
     * 正常不可见
     */
    abstract fun onInVisiable()
    /**
     * 正常可见
     */
    abstract fun onVisiable()

    /**
     * 请求数据
     */
    abstract fun loadData()
    /**
     * 第一次可见，加载完 commit后
     */
    abstract fun onFirstUserVisible()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(getLayoutID(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    abstract fun getLayoutID(): Int
}
package com.cosplay.kotlin.hw.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cosplay.kotlin.hw.R

/**
 * Author:wangzhiwei on 2018/8/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class FragmentV4 :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var  imageview = ImageView(context)
        Glide.with(context!!).load(R.drawable.nav_1).into(imageview)
        return imageview
    }
}
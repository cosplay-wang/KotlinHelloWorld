package com.cosplay.kotlin.hw.ui.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cosplay.kotlin.hw.R

/**
 * Author:wangzhiwei on 2018/8/13.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class FragmentZQ :Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.e("FragmentZQ","onCreateView")
        return inflater!!.inflate(R.layout.first_fragment_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("FragmentZQ","onActivityCreated")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e("FragmentZQ","onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.e("FragmentZQ","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("FragmentZQ","onPause")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("FragmentZQ","onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("FragmentZQ","onDestroyView")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("FragmentZQ","onHiddenChanged")
    }
}
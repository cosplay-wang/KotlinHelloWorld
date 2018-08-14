package com.cosplay.kotlin.hw.ui.activity

import android.app.Activity
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.ui.fragment.BaseFragment
import com.cosplay.kotlin.hw.ui.fragment.FirstFragment
import com.cosplay.kotlin.hw.ui.fragment.FragmentZQ
import com.cosplay.kotlin.hw.ui.fragment.SecondFragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentAct : Activity(),View.OnClickListener{
    lateinit var fragManager:FragmentManager
    lateinit var fragTrans : FragmentTransaction
    lateinit var containerView : View
    lateinit var firstFragment:FirstFragment
    lateinit var secondFragment:SecondFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        initVar()
        addFrag()
    }

    private fun addFrag() {
        fragTrans.add(R.id.fl_fragcontainer,firstFragment,getString(R.string.first_frag_name))
        fragTrans.add(R.id.fl_fragcontainer,secondFragment,getString(R.string.second_frag_name))
        fragTrans.show(firstFragment)
        fragTrans.hide(secondFragment)
        rg_first.check(R.id.rb_first)
        firstFragment.onHiddenChanged(false)
        fragTrans.commit()

    }

    private fun initVar() {
        fragManager = fragmentManager
        fragTrans = fragManager.beginTransaction()
        firstFragment = FirstFragment()
        secondFragment = SecondFragment()
        rb_first.setOnClickListener(this)
        rb_second.setOnClickListener(this)
    }

    override fun onClick(v: View?){
        fragTrans = fragManager.beginTransaction()
        when(v!!.getId()){
            R.id.rb_first ->{
                fragTrans.show(firstFragment)
                fragTrans.hide(secondFragment)
            }
            R.id.rb_second ->{
                fragTrans.show(secondFragment)
                fragTrans.hide(firstFragment)
            }
        }
        fragTrans.commit()
    }
}

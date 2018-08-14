package com.cosplay.kotlin.hw.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.ui.fragment.FragmentV4
import kotlinx.android.synthetic.main.act_viewpager.*

/**
 * Author:wangzhiwei on 2018/8/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class ViewPagerAct : AppCompatActivity() {
    private lateinit var viewList:MutableList<View>
    private lateinit var fragmentList:MutableList<Fragment>
    private var context : Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_viewpager)
        setViews()
        vp_view.adapter = ViewPagerAdapter(viewList)
        vp_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                  Toast.makeText(context,"您选择了"+position,Toast.LENGTH_SHORT).show()
            }
        })
        setFragments()
        vp_frag.adapter = FragmentViewPager(supportFragmentManager,fragmentList)
    }
   private fun setViews(){
        var i = 0;
        viewList = ArrayList()
        while (i<4){
           var  imageview = ImageView(context)
            Glide.with(context).load(R.drawable.nav_1).into(imageview)
         //   imageview.setImageResource(R.drawable.nav_1)
            viewList.add(imageview)
            i++
        }
    }
    private fun setFragments(){
        var i = 0;
        fragmentList = ArrayList()
        while (i<4){
            fragmentList.add(FragmentV4())
            i++
        }
    }
    inner class ViewPagerAdapter(private var dataList:MutableList<View>):PagerAdapter(){
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
           container.addView(dataList[position])
            return dataList[position]
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //    super.destroyItem(container, position, `object`)
            container.removeView(dataList[position])
        }

        override fun getCount(): Int {
            return dataList?.size
        }
    }
    inner  class FragmentViewPager(fragmentManager:FragmentManager,private var dataList:MutableList<Fragment>) : FragmentPagerAdapter(fragmentManager){
        override fun getItem(position: Int): Fragment {
            return dataList[position]
        }

        override fun getCount(): Int {
           return  dataList?.size
        }
    }

}
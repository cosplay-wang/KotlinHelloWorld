package com.cosplay.kotlin.hw.ui.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.util.FrameAnimation
import com.cosplay.kotlin.hw.util.GlideRoundTransform
import kotlinx.android.synthetic.main.activity_pkanimation.*
import com.bumptech.glide.request.RequestOptions
import android.widget.RelativeLayout
import android.view.ViewGroup
import android.widget.Toast
import com.cosplay.kotlin.hw.ui.adapter.PkDetailRvAp
import com.cosplay.kotlin.hw.util.pkslide.SlidingUpPanelLayout


class PKAnimationActivity : AppCompatActivity() {
    val innvalTime = 30;
    private lateinit var myOptions: RequestOptions
    private var context: Context = this
    private lateinit var handler: Handler
    private var successORfailure: Boolean = false //false是pk失败，true是pk成功
    private lateinit var leftFrameAnima: FrameAnimation
    private lateinit var topFrameAnima: FrameAnimation
    private var leftObjectAnimator: ObjectAnimator? = null
    private var topObjectAnimator: ObjectAnimator? = null
    private var bottomObjectAnimator: ObjectAnimator? = null

    lateinit var dataList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pkanimation)
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg!!.what) {
                    1 -> {
                        leftObjectAnimator!!.cancel()
                        startTopObjectAnima()
                    }
                    2 -> {
                        startLeftBackObjectAnima()
                        topObjectAnimator!!.cancel()
                    }
                    3 -> {

                        Glide.with(context).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534766758181&di=781c37d97b05a5b565f98e565aec8f74&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170821%2F0225131936-0.jpg").apply(myOptions).into(iv_my_circle);

                        Glide.with(context).load("http://img.duoziwang.com/2017/08/080920129356.jpg").apply(myOptions).into(iv_opponent_circle);
                        startBottomObjectAnima()
                        topFrameStart()
                    }
                    4 -> {
                        releaseLeftFrame()
                        bottomObjectAnimator!!.cancel()

                    }
                    5 -> {
                    }
                    6 -> {
                        leftFrameStart()
                    }
                }
            }
        }
        val simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                rl_pk_success_back.setBackground(resource)
            }
        }
        if (successORfailure) {
            Glide.with(this).load(R.drawable.success_back)
                    .into(simpleTarget)
        } else {
            Glide.with(this).load(R.drawable.failure_back)
                    .into(simpleTarget)
        }
        myOptions = RequestOptions()
                .centerCrop()
                .transform(GlideRoundTransform(this, 50))
        startLeftObjectAnima()
        sliding_layout.panelHeight = 100
        ll_pk_detail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                sliding_layout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
        })
        getDataList()
        rv_bottom_pk_detail.layoutManager = LinearLayoutManager(this)
        rv_bottom_pk_detail.adapter = PkDetailRvAp(dataList, this, rvItemClick(), rvItemInnerClick())

        var resultFrameAnima = FrameAnimation(iv_pk_bottom_detail, getResultDetailRes(), 200, true)
        Glide.with(context).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534766758181&di=781c37d97b05a5b565f98e565aec8f74&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170821%2F0225131936-0.jpg").apply(myOptions).into(iv_bottom_my_circle)

        Glide.with(context).load("http://img.duoziwang.com/2017/08/080920129356.jpg").apply(myOptions).into(iv_bottom_opponent_circle);

    }


    /**
     * 得到左侧的图片列表frame
     */
    private fun getLeftRes(): IntArray {
        var typedArray: TypedArray
        if (successORfailure) {
            typedArray = resources.obtainTypedArray(R.array.an_left_laugh)
        } else {
            typedArray = resources.obtainTypedArray(R.array.an_left_cry)
        }

        val len = typedArray.length()
        val resId = IntArray(len)
        for (i in 0 until len) {
            resId[i] = typedArray.getResourceId(i, -1)
        }
        typedArray.recycle()
        return resId
    }

    fun getDataList() {
        dataList = ArrayList<String>()
        dataList.add("第一项----简单的布局")
        dataList.add("第二题----Handler+ Thread")
        dataList.add("第三题-----viewpager+ view+ fragment")
        dataList.add("第四题-----fragment")
        dataList.add("第五题-----网络请求")
        dataList.add("第六题-----H5+webview原生")
        dataList.add("第七题-----H5+xwalkview自己的webview")
        dataList.add("第八题-----H5+x5自己的webview")
        dataList.add("第九题-----pk动画")
        dataList.add("第一项----简单的布局")
        dataList.add("第二题----Handler+ Thread")
        dataList.add("第三题-----viewpager+ view+ fragment")
        dataList.add("第四题-----fragment")
        dataList.add("第五题-----网络请求")
        dataList.add("第六题-----H5+webview原生")
        dataList.add("第七题-----H5+xwalkview自己的webview")
        dataList.add("第八题-----H5+x5自己的webview")
        dataList.add("第九题-----pk动画")

    }

    inner class rvItemClick : PkDetailRvAp.ItemClick {
        override fun onItemClick(v: View, position: Int) {
            Toast.makeText(context, "d点击了" + position, Toast.LENGTH_SHORT).show()
            // skipControlCenter(position)
        }
    }

    inner class rvItemInnerClick : PkDetailRvAp.ItemInnerClick {
        override fun onItemInnerClick(v: View, position: Int) {
            Toast.makeText(context, "d点击了" + dataList[position], Toast.LENGTH_SHORT).show()
            //skipControlCenter(position)
            //  startActivity<Main2Activity>("id" to 1,"name" to "kotlin")
        }
    }


    /**
     * 得到上部的图片列表frame
     */
    private fun getTopRes(): IntArray {
        var typedArray: TypedArray
        if (successORfailure) {
            typedArray = resources.obtainTypedArray(R.array.an_top_laugh)
        } else {
            typedArray = resources.obtainTypedArray(R.array.an_top_cry)
        }
        val len = typedArray.length()
        val resId = IntArray(len)
        for (i in 0 until len) {
            resId[i] = typedArray.getResourceId(i, -1)
        }
        typedArray.recycle()
        return resId
    }

    /**
     * 得到详情列表frame
     */
    private fun getResultDetailRes(): IntArray {
        var typedArray = resources.obtainTypedArray(R.array.result_detail)

        val len = typedArray.length()
        val resId = IntArray(len)
        for (i in 0 until len) {
            resId[i] = typedArray.getResourceId(i, -1)
        }
        typedArray.recycle()
        return resId
    }

    /**
     * 左侧frame动画开始
     */
    private fun leftFrameStart() {
        if (successORfailure) {
            leftFrameAnima = FrameAnimation(iv_pk_success_pk_left_top, getLeftRes(), innvalTime, true)
            handler.sendEmptyMessageDelayed(1, 1000)

        } else {
            leftFrameAnima = FrameAnimation(iv_pk_failure_pk_left_top, getLeftRes(), innvalTime, false)
            handler.sendEmptyMessageDelayed(1, 2000)
        }

    }

    /**
     * 释放左侧frame动画
     */
    private fun releaseLeftFrame() {
        if (leftFrameAnima != null) {
            leftFrameAnima.release()
        }
    }

    /**
     * 上侧frame动画开始
     */
    private fun topFrameStart() {
        if (successORfailure) {
            iv_pk_success_pk_laugh.visibility = View.VISIBLE
            iv_pk_failure_pk_cry.visibility = View.GONE
            topFrameAnima = FrameAnimation(iv_pk_success_pk_laugh, getTopRes(), innvalTime, true)
        } else {
            iv_pk_failure_pk_cry.visibility = View.VISIBLE
            iv_pk_success_pk_laugh.visibility = View.GONE
            topFrameAnima = FrameAnimation(iv_pk_failure_pk_cry, getTopRes(), innvalTime, true)
        }
    }

    /**
     * 释放上侧frame动画
     */
    private fun releaseTopFrame() {
        if (topFrameAnima != null) {
            topFrameAnima.release()
        }
    }


    /**
     * 左侧位移动画开始
     */
    private fun startLeftObjectAnima() {
        val width = resources.getDimension(R.dimen.rl_pk_success_pk_left_width) + 0f
        if (successORfailure) {
            rl_pk_failure_pk_left.visibility = View.GONE
            rl_pk_success_pk_left.visibility = View.VISIBLE
            leftObjectAnimator = ObjectAnimator.ofFloat(rl_pk_success_pk_left, "translationX", -width, 0f)
        } else {
            rl_pk_failure_pk_left.visibility = View.VISIBLE
            rl_pk_success_pk_left.visibility = View.GONE
            leftObjectAnimator = ObjectAnimator.ofFloat(rl_pk_failure_pk_left, "translationX", -width, 0f)
        }
        //      设置移动时间
        leftObjectAnimator!!.duration = 1000
        //      开始动画
        leftObjectAnimator!!.start()
        handler.sendEmptyMessageDelayed(6, leftObjectAnimator!!.duration)

    }

    /**
     * 上部位移动画开始
     */
    private fun startTopObjectAnima() {
        val topMove = resources.getDimension(R.dimen.rl_pk_success_pk_height) + resources.getDimension(R.dimen.rl_pk_success_pk_top_margin) + 0f
        if (successORfailure) {
            rl_pk_success_pk.visibility = View.VISIBLE
            topObjectAnimator = ObjectAnimator.ofFloat(rl_pk_success_pk, "translationY", -topMove, 0f)
        } else {
            rl_pk_failure_pk.visibility = View.VISIBLE
            topObjectAnimator = ObjectAnimator.ofFloat(rl_pk_failure_pk, "alpha", 0f, 1f)
        }

        //      设置移动时间
        topObjectAnimator!!.duration = 500
        //      开始动画
        topObjectAnimator!!.start()
        handler.sendEmptyMessageDelayed(2, topObjectAnimator!!.duration)


    }


    /**
     * 左侧动画退出屏外
     */
    private fun startLeftBackObjectAnima() {

        val width = resources.getDimension(R.dimen.rl_pk_success_pk_left_width) + 0f
        if (successORfailure) {
            leftObjectAnimator = ObjectAnimator.ofFloat(rl_pk_success_pk_left, "translationX", 0f, -width)
        } else {
            leftObjectAnimator = ObjectAnimator.ofFloat(rl_pk_failure_pk_left, "translationX", 0f, -width)
        }
        //      设置移动时间
        leftObjectAnimator!!.duration = 500
        //      开始动画
        leftObjectAnimator!!.start()
        handler.sendEmptyMessageDelayed(3, leftObjectAnimator!!.duration)
    }


    /**
     * 下方的结果 位移动画开始
     */
    private fun startBottomObjectAnima() {
        val height = rl_pk_success_pk_bottom.height + 0f
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        bottomObjectAnimator = ObjectAnimator.ofFloat(rl_pk_success_pk_bottom, "translationY", -height, 0f)
        if (successORfailure) {
            iv_pk_success_center_back.visibility = View.VISIBLE
            iv_pk_failure_center_back.visibility = View.GONE
            ll_exp_dou.visibility = View.VISIBLE
            iv_success_crown.visibility = View.VISIBLE
            iv_faiure_crown.visibility = View.INVISIBLE
            tv_share_against.text = "炫耀一下"
            params.addRule(RelativeLayout.BELOW, R.id.rl_pk_success_pk)
        } else {
            iv_pk_success_center_back.visibility = View.GONE
            iv_pk_failure_center_back.visibility = View.VISIBLE
            ll_exp_dou.visibility = View.GONE
            tv_share_against.text = "立即反击"
            iv_success_crown.visibility = View.INVISIBLE
            iv_faiure_crown.visibility = View.VISIBLE
            params.addRule(RelativeLayout.BELOW, R.id.rl_pk_failure_pk)
        }
        rl_pk_success_pk_bottom.layoutParams = params
        var resultFrameAnima = FrameAnimation(iv_pk_detail, getResultDetailRes(), 200, true)
        rl_pk_success_pk_bottom.visibility = View.VISIBLE

        //      设置移动时间
        bottomObjectAnimator!!.duration = 500
        //      开始动画
        bottomObjectAnimator!!.start()
        handler.sendEmptyMessageDelayed(4, bottomObjectAnimator!!.duration)
    }

}

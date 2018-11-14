package com.cosplay.kotlin.hw.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.util.DoudongAnima
import com.cosplay.kotlin.hw.util.GlideRoundTransform
import kotlinx.android.synthetic.main.activity_before_pk.*


class PKingActivity : AppCompatActivity() {
    val context: Context = this
    var heigth: Int = -1;
    var width: Int = -1
    var index = 0;
    private lateinit var handler: Handler
    private var exerciseNumInAnima: ObjectAnimator? = null
    private var exerciseNumOutAnima: ObjectAnimator? = null
    private var exerciseContentAnima: ObjectAnimator? = null
    private var exerciseContentDissAnima: ObjectAnimator? = null

    private var fireAnima: ObjectAnimator? = null
    private var scroeInMyAnimaSet: AnimatorSet? = null
    private var scroeInOppoAnimaSet: AnimatorSet? = null
    private var scroeOutMyAnimaSet: AnimatorSet? = null
    private var scroeOutOppoAnimaSet: AnimatorSet? = null
    private var fireShakeAnima: ObjectAnimator? = null
    private lateinit var myOptions: RequestOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_pk)
        val resources = this.resources
        val dm = resources.displayMetrics
        width = dm.widthPixels
        heigth = dm.heightPixels
        myOptions = RequestOptions()
                .centerCrop()
                .transform(GlideRoundTransform(this, 50))
        Glide.with(context).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534766758181&di=781c37d97b05a5b565f98e565aec8f74&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170821%2F0225131936-0.jpg").apply(myOptions).into(iv_my_circle)

        Glide.with(context).load("http://img.duoziwang.com/2017/08/080920129356.jpg").apply(myOptions).into(iv_oppo_circle)

        tv_oppo_scroe.visibility = View.INVISIBLE
        tv_my_scroe.visibility = View.INVISIBLE
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg!!.what) {
                    1 -> {
                        handler.postDelayed(Runnable { kotlin.run { exerciseNumOutAnimaStart() } }, 500)

                    }
                    2 -> {
                        exerciseContentAnimaStart()
                    }
                    3 -> {
                        scroeInAnimaStart()
                    }
                    4 -> {

                        fireAnimaStart(true)
                    }
                    5 -> {
                        pk_ing_my_fire.visibility = View.GONE
                        pk_ing_oppo_fire.visibility = View.GONE
                        fireShakeAnimaStart(true);
                    }
                    6 -> {
                        exerciseContentDissAnimaStart()
                        scroeOutAnimaStart()
                    }
                    7->{
                        index = index+ 1
                        when(index){
                            8->{
                                startActivity(Intent(context,PKAnimationActivity::class.java))
                                finish()
                            }
                        }
                        exerciseNumInAnimaStart()
                    }
                }
            }
        }
//        tv_oppo_scroe.visibility = View.VISIBLE
//        tv_my_scroe.visibility = View.VISIBLE
        exerciseNumInAnimaStart()
    }

    private fun exerciseNumInAnimaStart() {
        if(exerciseNumInAnima!=null){
            exerciseNumInAnima!!.cancel()
        }
        rl_test_content.visibility = View.GONE
        rl_num_question.visibility = View.VISIBLE
        exerciseNumInAnima = ObjectAnimator.ofFloat(rl_num_question, "translationX", width /1f, 0f)
        //      设置移动时间
        exerciseNumInAnima!!.duration = 1000
        exerciseNumInAnima!!.interpolator = AnticipateOvershootInterpolator()
        //      开始动画
        exerciseNumInAnima!!.start()
        handler.sendEmptyMessageDelayed(1, exerciseNumInAnima!!.duration)
    }

    private fun exerciseNumOutAnimaStart() {
        rl_test_content.visibility = View.GONE
        if(exerciseNumOutAnima!=null){
            exerciseNumOutAnima!!.cancel()
        }
        exerciseNumOutAnima = ObjectAnimator.ofFloat(rl_num_question, "translationX", 0f, -width / 1f)
        //      设置移动时间
        exerciseNumOutAnima!!.duration = 1000
        exerciseNumOutAnima!!.interpolator = AnticipateInterpolator()
        //      开始动画
        exerciseNumOutAnima!!.start()
        handler.sendEmptyMessageDelayed(2, exerciseNumOutAnima!!.duration)
    }

    private fun exerciseContentAnimaStart() {
        if(exerciseContentAnima!=null){
            exerciseContentAnima!!.cancel()
        }
        exerciseContentAnima
        rl_num_question.visibility = View.GONE
        rl_test_content.visibility = View.VISIBLE
        exerciseContentAnima = ObjectAnimator.ofFloat(rl_test_content, "alpha", 0f, 1f)
        //      设置移动时间
        exerciseContentAnima!!.duration = 500
        //      开始动画
        exerciseContentAnima!!.start()
        handler.sendEmptyMessageDelayed(3, exerciseContentAnima!!.duration)
    }
    private fun exerciseContentDissAnimaStart() {
        if(exerciseContentDissAnima!=null){
            exerciseContentDissAnima!!.cancel()
        }
        exerciseContentDissAnima = ObjectAnimator.ofFloat(rl_test_content, "alpha", 1f, 0f)
        //      设置移动时间
        exerciseContentDissAnima!!.duration = 500
        //      开始动画
        exerciseContentDissAnima!!.start()
    //    handler.sendEmptyMessageDelayed(7, exerciseContentDissAnima!!.duration)
    }
    private fun fireShakeAnimaStart(isFromLeft:Boolean) {
        if(fireShakeAnima!=null){
            fireShakeAnima!!.cancel()
        }
        when(isFromLeft){
            true->{
                fireShakeAnima= DoudongAnima.tada(rl_oppo_head,5f)
            }
            false->{
                fireShakeAnima= DoudongAnima.tada(rl_my_head,5f)
            }
        }
        //      设置移动时间
        fireShakeAnima!!.duration = 800
        fireShakeAnima!!.interpolator =  AnticipateInterpolator()
        //      开始动画
        fireShakeAnima!!.start()

        handler.sendEmptyMessageDelayed(6, fireShakeAnima!!.duration)
    }
    private fun fireAnimaStart(isFromLeft:Boolean) {
        if(fireAnima!=null){
            fireAnima!!.cancel()
        }
        when(isFromLeft){
            true->{
                pk_ing_my_fire.visibility = View.VISIBLE
                pk_ing_oppo_fire.visibility = View.GONE
                fireAnima = ObjectAnimator.ofFloat(pk_ing_my_fire, "translationX", 0f, 210f)
            }
            false->{
                pk_ing_my_fire.visibility = View.GONE
                pk_ing_oppo_fire.visibility = View.VISIBLE
                fireAnima = ObjectAnimator.ofFloat(pk_ing_oppo_fire, "translationX", 0f, -210f)
            }
        }

        //      设置移动时间
        fireAnima!!.duration = 300
        fireAnima!!.interpolator =  AccelerateInterpolator()
        //      开始动画
        fireAnima!!.start()
        handler.sendEmptyMessageDelayed(5, exerciseContentAnima!!.duration)
    }
    private fun scroeInAnimaStart() {
        tv_oppo_scroe.visibility = View.VISIBLE
        tv_my_scroe.visibility = View.VISIBLE
        scroeOutAgainstAnimaStart()
        if(scroeInOppoAnimaSet!=null){
            scroeInOppoAnimaSet!!.cancel()
        }
        if(scroeInMyAnimaSet!=null){
            scroeInMyAnimaSet!!.cancel()
        }

        scroeInMyAnimaSet = AnimatorSet()//组合动画
        val scaleXMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleX", 0f, 1f)
        val scaleYMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleY", 0f, 1f)
        val alphaMy = ObjectAnimator.ofFloat(tv_my_scroe, "alpha", 0f,1f)
        alphaMy.duration = 10
        alphaMy.start()
        scroeInMyAnimaSet!!.duration = 800
        scroeInMyAnimaSet!!.interpolator = AnticipateOvershootInterpolator()
        scroeInMyAnimaSet!!.play(scaleXMy).with(scaleYMy)//两个动画同时开始
        scroeInOppoAnimaSet = AnimatorSet()//组合动画

        val scaleXOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleX", 0f, 1f)
        val scaleYOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleY", 0f, 1f)

        scroeInOppoAnimaSet!!.duration = 800
        scroeInOppoAnimaSet!!.interpolator = AnticipateOvershootInterpolator()
        scroeInOppoAnimaSet!!.play(scaleXOppo).with(scaleYOppo)//两个动画同时开始

        scroeInOppoAnimaSet!!.start()
        scroeInMyAnimaSet!!.start()

        handler.sendEmptyMessageDelayed(4, scroeInOppoAnimaSet!!.duration)
    }


    private fun scroeOutAnimaStart() {
        if(scroeOutMyAnimaSet!=null){
            scroeOutMyAnimaSet!!.cancel()
        }
        if(scroeOutOppoAnimaSet!=null){
            scroeOutOppoAnimaSet!!.cancel()
        }
        scroeOutMyAnimaSet = AnimatorSet()//组合动画
        val scaleXMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleX", 1f, 1.3f)
        val scaleYMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleY", 1f, 1.3f)
        val  scroeOutMyTran = ObjectAnimator.ofFloat(tv_my_scroe, "translationY", 0f,-15f)
        val  scroeOutMyAlpha = ObjectAnimator.ofFloat(tv_my_scroe, "alpha", 1f,0f)
        scroeOutMyAnimaSet!!.duration = 800
        scroeOutMyAnimaSet!!.play(scaleXMy).with(scaleYMy).with(scroeOutMyTran).with(scroeOutMyAlpha)//两个动画同时开始
        scroeOutOppoAnimaSet = AnimatorSet()//组合动画

        val scaleXOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleX", 1f, 1.3f)
        val scaleYOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleY", 1f, 1.3f)
        val  scroeOutOppoTran = ObjectAnimator.ofFloat(tv_oppo_scroe, "translationY", 0f,-15f)
        val  scroeOutOppoAlpha = ObjectAnimator.ofFloat(tv_oppo_scroe, "alpha", 1f,0f)
        scroeOutOppoAnimaSet!!.duration =800
        scroeOutOppoAnimaSet!!.play(scaleXOppo).with(scaleYOppo).with(scroeOutOppoTran).with(scroeOutOppoAlpha)//两个动画同时开始

        scroeOutOppoAnimaSet!!.start()
        scroeOutMyAnimaSet!!.start()


        handler.sendEmptyMessageDelayed(7, scroeOutOppoAnimaSet!!.duration)
    }
    private fun scroeOutAgainstAnimaStart() {
        if(scroeOutMyAnimaSet!=null){
            scroeOutMyAnimaSet!!.cancel()
        }
        if(scroeOutOppoAnimaSet!=null){
            scroeOutOppoAnimaSet!!.cancel()
        }
        scroeOutMyAnimaSet = AnimatorSet()//组合动画
//        val scaleXMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleX", 1.3f, 1.3f)
//        val scaleYMy = ObjectAnimator.ofFloat(tv_my_scroe, "scaleY", 1f, 1.3f)
        val  scroeOutMyTran = ObjectAnimator.ofFloat(tv_my_scroe, "translationY", -15f,0f)
        val  scroeOutMyAlpha = ObjectAnimator.ofFloat(tv_my_scroe, "alpha", 0f,1f)
        scroeOutMyAnimaSet!!.duration = 1
        scroeOutMyAnimaSet!!.play(scroeOutMyTran).with(scroeOutMyAlpha)//两个动画同时开始
        scroeOutOppoAnimaSet = AnimatorSet()//组合动画

//        val scaleXOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleX", 1f, 1.3f)
//        val scaleYOppo = ObjectAnimator.ofFloat(tv_oppo_scroe, "scaleY", 1f, 1.3f)
        val  scroeOutOppoTran = ObjectAnimator.ofFloat(tv_oppo_scroe, "translationY", -15f,0f)
        val  scroeOutOppoAlpha = ObjectAnimator.ofFloat(tv_oppo_scroe, "alpha", 0f,1f)
        scroeOutOppoAnimaSet!!.duration =1
        scroeOutOppoAnimaSet!!.play(scroeOutOppoTran).with(scroeOutOppoAlpha)//两个动画同时开始

        scroeOutOppoAnimaSet!!.start()
        scroeOutMyAnimaSet!!.start()


      //  handler.sendEmptyMessageDelayed(7, scroeOutOppoAnimaSet!!.duration)
    }

}

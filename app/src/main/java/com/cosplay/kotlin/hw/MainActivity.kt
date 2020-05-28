package com.cosplay.kotlin.hw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.cosplay.kotlin.hw.R.id.kotlin_rv
import com.cosplay.kotlin.hw.mvvm.view.MVVM1Activity
import com.cosplay.kotlin.hw.mvvm2.Mvvm2Activity
import com.cosplay.kotlin.hw.ui.activity.*
import com.cosplay.kotlin.hw.ui.adapter.MainRvAp
import com.cosplay.kotlin.hw.util.track
import org.json.JSONObject


class MainActivity : Activity() {
    lateinit var dataList: MutableList<String>
    var context: Context = this;
    //  val jjj : int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  getExtras()
      //  getDataList()
      //  kotlin_rv.layoutManager = LinearLayoutManager(this)
     ////   kotlin_rv.adapter = MainRvAp(dataList, this, rvItemClick(), rvItemInnerClick())
        //   var animation = AnimationUtils.loadAnimation(context,R.anim.simple_1)//原有的动画加载方式
      //  var animation = AnimatorInflater.loadAnimator(context, R.animator.simple_1)//属性动画的加载方式
      //  animation.setTarget(tv_anim)
     //   animation.start()
//        if(NotificationManagerCompat.from(this).areNotificationsEnabled()){
//            Log.e("aaa","1111");
//        }else{
//            Log.e("aaa","2222");
//        }
//        Log.e("metaData",MetaUtils.getMetaDataValue(context,"city")+"=---"+resources.getString(R.string.ura));
//
//        var handler:Handler= object :Handler(){
//            override fun handleMessage(msg: Message?) {
//                super.handleMessage(msg)
//            }
//        }
//        DeviceUtils.hasSimCard(context)
//        DeviceUtils.isPad(context)
//        handler.postDelayed(Runnable {
//              startActivity(Intent(context,DialogAActivity::class.java));
//        },5000)

    }


    inner class rvItemClick : MainRvAp.ItemClick {
        override fun onItemClick(v: View, position: Int) {
            Toast.makeText(context, "d点击了" + position, Toast.LENGTH_SHORT).show()
            skipControlCenter(position)
        }
    }

    inner class rvItemInnerClick : MainRvAp.ItemInnerClick {
        override fun onItemInnerClick(v: View, position: Int) {
            Toast.makeText(context, "d点击了" + dataList[position], Toast.LENGTH_SHORT).show()
            skipControlCenter(position)
            //  startActivity<NavigationViewActivity>("id" to 1,"name" to "kotlin")
        }
    }

    fun skipControlCenter(position: Int) {
        var intent = Intent()
        when (position) {
            0 -> {
                intent.setClass(context, LayoutAct::class.java)
            }
            1 -> {
                intent.setClass(context, Handler2ThreadAct::class.java)
            }
            2 -> {
                intent.setClass(context, ViewPagerAct::class.java)
            }
            3 -> {
                intent.setClass(context, FragmentAct::class.java)
            }
            4 -> {
                intent.setClass(context, NetAct::class.java)
            }
            5 -> {
                intent.setClass(context, H5Activity::class.java)
            }
            6 -> {//crosswalk和tbs不能同时使用，否则tbs崩溃  在小米上
                intent.setClass(context, XwalkViewActivity::class.java)
            }
            7 -> {
                intent.setClass(context, X5Activity::class.java)
            }
            8 -> {
                intent.setClass(context, PKAnimationActivity::class.java)
            }
            9 -> {
                intent.setClass(context, PKingActivity::class.java)
            }
            10 -> {
                intent.setClass(context, BeforePKActivity::class.java)
            }
            11 -> {
                intent.setClass(context, AccelerometerActivity::class.java)
            }
            12 -> {
                intent.setClass(context, NotificaTionActivity::class.java)
            }
            13 -> {
                intent.setClass(context, ManifestMetaActivity::class.java)
            }
            14 -> {
                intent.setClass(context, AndpermisstionActivity::class.java)
            }
            15 -> {
                intent.setClass(context, FooterRecordActivity::class.java)
            }
            16 -> {
                intent.setClass(context, LottieActivity::class.java)
            }
            17 -> {
                intent.setClass(context, FingerPrintActivity::class.java)
            }
            18 -> {
                intent.setClass(context, FingerPrintCompatActivity::class.java)
            }
            19 -> {
                intent.setClass(context, BiometricPromptActivity::class.java)
            }
            20 -> {
                intent.setClass(context, WechatFingerPrintActivity::class.java)
            }
            21 -> {
                intent.setClass(context, SpannableStringActivity::class.java)
            }
            22 -> {
                intent.setClass(context, TTdensityActivity::class.java)
            }
            23 -> {
                intent.setClass(context, LoadingAnimationActivity::class.java)
            }
            24 -> {
                intent.setClass(context, TextviewActivity::class.java)
            }
            25 -> {
                intent.setClass(context, ImmersionActivity::class.java)
            }
            26 -> {
                intent.setClass(context, ObserverableMode::class.java)
            }
            27 -> {
                intent.setClass(context, MVVM1Activity::class.java)
            }
            28 -> {
                intent.setClass(context, SetlayoutparamsActivity::class.java)
            }
            29 -> {
                intent.setClass(context, Mvvm2Activity::class.java)
            }
            30 -> {
                intent.setClass(context, ExecutorSchedulerActivity::class.java)
            }
            31 -> {
                intent.setClass(context, ChangeIconActivity::class.java)
            }
            32 -> {
                intent.setClass(context, SmartRefreshActivity::class.java)
            }
            33 -> {
                intent.setClass(context, CoordinatorActivity::class.java)
            }
            else -> {
                intent.setClass(context, MainActivity::class.java)
            }
            //88888888888888888888888888888
        }
        startActivity(intent)
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
        dataList.add("第九题-----pk结果动画")
        dataList.add("第10题-----pk进行中")
        dataList.add("第11题-----pk匹配")
        dataList.add("第12题-----摇一摇的实现")
        dataList.add("第13题-----通知的部分")
        dataList.add("第14题-----读取menifest的meta标签")
        dataList.add("第15题-----andPermisstion 动态权限")
        dataList.add("第16题-----用户足迹")
        dataList.add("第17题-----lottie  Json动画")
        dataList.add("第18题-----指纹识别")
        dataList.add("第19题-----指纹识别support" +
                "V4")
        dataList.add("第20题-----9.0指纹识别BiometricPrompt" +
                "生物识别 为之后的面部识别 虹膜识别之类的")
        dataList.add("第21题-----微信的指纹识别库 ")
        dataList.add("第22题-----部分文字实现点击事件 ")
        dataList.add("第23题-----头条适配 ")
        dataList.add("第24题-----加载动画的库loadingdrawable")
        dataList.add("第25题-----textview的截断")
        dataList.add("第26题-----immersionbar")
        dataList.add("第27题-----观察者模式传值")
        dataList.add("第28题-----mvvm")
        dataList.add("第29题-----setlayoutparams")
        dataList.add("第30题-----mvvm2")
        dataList.add("第31题-----线程切换")
        dataList.add("第32题-----更换图标")
        dataList.add("第33题-----上下拉刷新")
        dataList.add("第34题-----折叠顶部效果")
        //定义与事件相关的属性信息
        val eventObject = JSONObject()
        eventObject.put("分类", "手机")
        eventObject.put("名称", "iPhone6 plus 64g")
        track(this,"key1",eventObject)
    }


    private fun getExtras() {
        var intentContent: String = "getintent:"
        if (intent.getStringExtra("notificationID") != null) {
            intentContent = intentContent + intent.getStringExtra("notificationID")
        }
        if (intent.getStringExtra("name") != null) {
            intentContent = intentContent + intent.getStringExtra("name")
        }
        Toast.makeText(this, intentContent, Toast.LENGTH_SHORT).show()
    }
}

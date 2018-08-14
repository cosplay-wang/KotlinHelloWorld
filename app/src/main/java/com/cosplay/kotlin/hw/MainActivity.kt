package com.cosplay.kotlin.hw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.cosplay.kotlin.hw.ui.activity.*
import com.cosplay.kotlin.hw.ui.adapter.MainRvAp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
//    var sHello: String = "hh"
//    var mess: Int = 1
//
//    private lateinit var handler: Handler
//    lateinit var thread: Thread
    lateinit var dataList: MutableList<String>
    var context:Context = this;
    //  val jjj : int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataList()
        kotlin_rv.layoutManager = LinearLayoutManager(this)
        kotlin_rv.adapter = MainRvAp(dataList,this,rvItemClick(),rvItemInnerClick())
//        sHello = "jjjjj"
//        //    jjj = 2
//        Log.e(sHello, sHello)
//        onMyFirstMethod("jjjsass")
//        thread = Thread(Runnable {
//            kotlin.run {
//                var message = Message()
//                message.what = 1000;
//                message.obj = "11"
//                handler.sendMessageDelayed(message, 3000 + 5000)
//            }
//        })
//        thread.start()
//        handler = object : Handler() {
//            override fun handleMessage(msg: Message?) {
//                super.handleMessage(msg)
//                when (msg?.what) {
//                    1 -> {
//                        Log.e("hh", msg?.what.toString() + "===");
//                        mess++
//                        handler.sendEmptyMessageDelayed(mess, 1000);
//                    }
//                    2 -> {
//                        Log.e("hh", msg.what.toString() + "==");
//                        mess++
//                        handler.sendEmptyMessageDelayed(mess, 1000);
//                    }
//                    else -> {
//                        Log.e("hh", msg?.what.toString() + "==");
//                    }
//
//                }
//            }
//        };
//        handler.sendEmptyMessageDelayed(mess, 2000);
    }

//    fun onMyFirstMethod(hh: String) {
//        Log.e(sHello, hh + onMyFirstBackMethod())
//    }
//
//    fun onMyFirstBackMethod(): String {
//        return "acdr"
//    }
    inner class rvItemClick : MainRvAp.ItemClick{
    override fun onItemClick(v: View, position: Int) {
        Toast.makeText(context,"d点击了"+position,Toast.LENGTH_SHORT).show()
        skipControlCenter(position)
    }
}
    inner class rvItemInnerClick : MainRvAp.ItemInnerClick{
        override fun onItemInnerClick(v: View, position: Int) {
            Toast.makeText(context,"d点击了"+dataList[position],Toast.LENGTH_SHORT).show()
            skipControlCenter(position)
        }
    }
    fun skipControlCenter(position :Int){
         var intent =  Intent()
        when(position)
        {
            0-> {
                intent.setClass(context,LayoutAct::class.java)
            }
            1->{
                intent.setClass(context, Handler2ThreadAct::class.java)
            }
            2->{
                intent.setClass(context, ViewPagerAct::class.java)
            }
            3->{
                intent.setClass(context, FragmentAct::class.java)
            }
            4->{
                intent.setClass(context, NetAct::class.java)
            }
            else ->{
                intent.setClass(context,MainActivity::class.java)
            }
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
    }
}

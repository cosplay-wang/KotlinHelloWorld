package com.cosplay.kotlin.hw.ui.activity

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.util.HttpUtils
import kotlinx.android.synthetic.main.activity_net.*
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import pub.devrel.easypermissions.EasyPermissions
import java.net.URL
import java.nio.charset.Charset
import kotlin.concurrent.thread


class NetAct : AppCompatActivity(),View.OnClickListener, EasyPermissions.PermissionCallbacks {
    lateinit var handler: Handler
    var context : Context = this
    val url : String = "https://api.douban.com/v2/book/search?q=%E4%B9%A6%E7%B1%8D"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        handler = object :Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when(msg?.what){
                    1->{
                        tv_click.text = msg!!.obj.toString()
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click.text = getString(R.string.click_net)+"kitlin自带的url+java的handler异步"
                            }
                        },3000)
                    }
                    2->{
                        tv_click_2.text = msg!!.obj.toString()
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click_2.text = getString(R.string.click_net)+"kitlin自带的url+kotlin系的Anko异步"
                            }
                        },3000)
                    }
                    3->{
                        tv_click_3.text = msg!!.obj.toString()
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click_3.text = getString(R.string.click_net)+"okhttp+kotlin系的Anko异步"
                            }
                        },3000)
                    }
                    4->{
                        tv_click_4.text = msg!!.obj.toString()
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click_4.text = getString(R.string.click_net)+"kitlin自带的url+kotlin系的thread异步"
                            }
                        },3000)
                    }
                }

            }
        }
        tv_click.setOnClickListener(this)
        tv_click_2.setOnClickListener(this)
        tv_click_3.setOnClickListener(this)
        tv_click_4.setOnClickListener(this)
        //tv_click.setOnClickListener { toast("ssssssssssssssss") }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tv_click -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    //打电话的权限
                    var mPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                    if (EasyPermissions.hasPermissions(context, mPermission)) {
                        //已经同意过
                        requestData()
                    } else {
                        //未同意过,或者说是拒绝了，再次申请权限
                        EasyPermissions.requestPermissions(
                                this,  //上下文
                                "需要网络的权限", //提示文言
                                100, //请求码
                                mPermission//权限列表
                        )
                    }
                } else {
                    //6.0以下，不需要授权
                    requestData()
                }

            }
            R.id.tv_click_2 -> {
                doAsync {
                    var data: String = getUrlContent(url)
                    uiThread {
                        tv_click_2.text = data
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click_2.text = getString(R.string.click_net)+"kitlin自带的url+kotlin系的Anko异步"
                            }
                        },3000)
                    }

                }
            }
            R.id.tv_click_3 -> {
                doAsync {
                    var response:Response = HttpUtils.okHttpRequest(url,null)
                    var data = response.body()!!.string()
                    uiThread {
                        tv_click_3.text = data
                        handler.postDelayed(Runnable {
                            kotlin.run {
                                tv_click_3.text = getString(R.string.click_net)+"okhttp+kotlin系的Anko异步"
                            }
                        },3000)
                    }

                }
            }
            R.id.tv_click_4 -> {

                thread(start = true, isDaemon = false, name = "DThread", priority = 3) {
                    var json = getUrlContent(url)
                    var  message :Message  = handler.obtainMessage()
                    message.what = 4
                    message.obj = json
                    handler.sendMessageDelayed(message,1000)
                }
                }

        }
    }

    private fun requestData() {

        oneThread(url).start()

    }
    fun getUrlContent(url: String): String {
        return URL(url).readText(Charset.defaultCharset())
    }
    inner class thirdThread(var reUrl:String):Thread(){
        override fun run() {
            var response:Response = HttpUtils.okHttpRequest(url,null)
            var  message :Message  = handler.obtainMessage()
            message.what = 3
            message.obj = response.body()!!.string()
            handler.sendMessageDelayed(message,1000)
        }

    }
    inner class oneThread(var reUrl:String):Thread(){
        override fun run() {
            var json = getUrlContent(reUrl)
            var  message :Message  = handler.obtainMessage()
            message.what = 1
            message.obj = json
            handler.sendMessageDelayed(message,1000)
        }

    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}

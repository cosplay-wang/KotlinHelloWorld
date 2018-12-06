package com.cosplay.kotlin.hw.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.*
import android.widget.FrameLayout
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.activity_h5.*
import org.jetbrains.anko.toast

class H5Activity : AppCompatActivity() {
    private lateinit var h5View: WebView
    var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)
        initH5()
    }

    private fun initH5() {
        h5View = WebView(context)
        fl_layout.addView(h5View, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))
        var setting = h5View.settings
        setting.javaScriptEnabled = true
        //设置允许JS中的弹窗
        setting.javaScriptCanOpenWindowsAutomatically = true
        //设置背景颜色 透明
        h5View.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //设置本地调用对象及其接口
        h5View.addJavascriptInterface(JavaScriptObject(this), "myObj")
        //支持javascript
        setting.setJavaScriptEnabled(true);
        //支持javascript
        setting.setJavaScriptEnabled(true);
        setting.setUserAgentString("android")
        // 设置可以支持缩放
        setting.setSupportZoom(true);

        // 设置出现缩放工具
        setting.setBuiltInZoomControls(true);

        //设定支持viewport
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        //载入网页

        h5View.loadUrl("file:///android_asset/tokeninfo.html")
//        h5View.postDelayed(Runnable { kotlin.run {
//            h5View.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
//        } },1000)
        h5View.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                var builder = AlertDialog.Builder(context)
                builder.setTitle("Alert")
                builder.setMessage(message)
                builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                    result!!.confirm()
                })
                builder.setCancelable(false)
                builder.create().show()
                return true
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }
        }
        h5View.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                h5View.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                h5View.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
            }
        }

       // cl_content.addView(h5View)
        bt_na2js.setOnClickListener {
            /**
             * 4.4之后的该方法有返回值
             */
//            h5View!!.evaluateJavascript("javascript:callJS()",object : ValueCallback<String> {
//                override fun onReceiveValue(value: String?) {
////                            这里返回JS的结果
//                    toast(value+"====")
//                }
//            })
         //   h5View.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
            h5View.loadUrl("javascript:callJS('aaaaaaaaasesasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')")

        }

    }

    inner class JavaScriptObject(var context: Context) {
        @JavascriptInterface
        fun funFormAndroid(name: String): String {
            toast("funFormAndroid")
            return "funFormAndroid"
        }

        @JavascriptInterface
        fun dataFormAndroid(name: String) {
            toast(name)
        }
    }
    /**
     * Android调用Js代码有两种方式
    1）通过WebView的loadUrl ()调用
    2）通过WebView的evaluateJavascript ()调用
    Js调用Android代码：
    Js调用Android代码有三种方式
    1）通过WebView的addJavascriptInterface ()进行对象映射
    2）通过WebViewClient的shouldOverrideUrlLoading（）来拦截Url调用代
    码
    3）通过WebChromeClient 的onJsAlert()、onJsConfirm()、
    onJsPrompt（）拦截JS中的对话框alert() / confirm() / prompt()
     */


}

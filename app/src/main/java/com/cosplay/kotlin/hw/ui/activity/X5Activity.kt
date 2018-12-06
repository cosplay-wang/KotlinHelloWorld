package com.cosplay.kotlin.hw.ui.activity

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.widget.FrameLayout
import com.cosplay.kotlin.hw.R
import com.cosplay.kotlin.hw.util.x5.X5WebView
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_x5.*
import kotlinx.android.synthetic.main.activity_xwalk_view.*
import org.jetbrains.anko.toast

class X5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5)
        var mWebView = X5WebView(this, null)
       mWebView.loadUrl("file:///android_asset/tokeninfo.html")
        mWebView.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
     //   mWebView.loadUrl("https://mapi.ekwing.com/student/race/getracelist?product=student&os=Android&driverCode=3.4.8&v=2.8&token=FMTM1MTkwNiMjMTAyNzgzMzEjIzA3YzMwZjljNzExZmM3NGJmOGQyMWU1Y2IyMzgzOGRjIyMzY2MzYTZjNTAxMzAyMjgzMzMyOTgyNmE2YmM3YjM4ZSMjMTU0NTgxNjYzMiMjMiMjMiMjc3R1w&uid=1351906&is_http=1&author_id=1351906&type=0")
        fl_layout.addView(mWebView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))
        val webSetting = mWebView.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.userAgentString = "android"
        webSetting.setSupportMultipleWindows(false)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);

        webSetting.domStorageEnabled = true
        webSetting.javaScriptEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.useWideViewPort = true
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.setAppCachePath(this.getDir("appcache", 0).path)
        webSetting.databasePath = this.getDir("databases", 0).path
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .path)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
//        mWebView.postDelayed(Runnable { kotlin.run {
//            mWebView.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
//        } },1000)
        mWebView.addJavascriptInterface(JavaScriptObject(this), "myObj")
        mWebView.webViewClient = object:WebViewClient(){
            override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
                super.onReceivedSslError(p0, p1, p2)
                p1!!.proceed()
            }
            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                super.onPageStarted(p0, p1, p2)
             //   if(!p1!!.contains("javascript")){
                    mWebView.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
               // }

            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                mWebView.loadUrl("javascript:window.tokenInfo={os:'Android',driverCode:'3.4.8',token:'0MTMzMDQ2MSMjMTAyNzgzMTYjI2NmZWQ5NWFhMDVkNmFiZjdkZDE3NTg0YjQwNGFlY2VhIyMzNWE4NjZlOTljY2UwOGYzMGU4MGYzYjZkNGZlMGY3YiMjMTU0NTkwODkxNCMjMiMjMiMjc3R1e',is_http:'0',v:'2.8',uid:'1330461'}")
            }

        }
        mWebView.webChromeClient = object:WebChromeClient(){
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
            }
        }
        tv_layout.setOnClickListener {
            mWebView.loadUrl("javascript:callJS('aaaaaaaaasesasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')")
        }


    }
    inner class JavaScriptObject(var context: Context) {
        @JavascriptInterface
        fun funFormAndroid(name: String): String {
            toast(name)
            return name
        }

        @JavascriptInterface
        fun dataFormAndroid(name: String) {
            toast(name)
        }
    }
}

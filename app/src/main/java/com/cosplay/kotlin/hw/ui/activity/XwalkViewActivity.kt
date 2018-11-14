package com.cosplay.kotlin.hw.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.ValueCallback
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.activity_xwalk_view.*
import org.jetbrains.anko.toast
import org.xwalk.core.JavascriptInterface
import org.xwalk.core.XWalkResourceClient
import org.xwalk.core.XWalkUIClient
import org.xwalk.core.XWalkView


class XwalkViewActivity : AppCompatActivity() {

    lateinit var animationDrawable:AnimationDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xwalk_view)
        xwv_webview.setResourceClient(MyResourceClient(xwv_webview))
        xwv_webview.setUIClient(MyUIClient(xwv_webview))
        xwv_webview.addJavascriptInterface(JavaScriptObject(this),"myObj")
        xwv_webview.settings.useWideViewPort = true
        xwv_webview.settings.loadWithOverviewMode = true
       // xwv_webview.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=http://moqi-git.github.io/ppt%20(1).pptx")
        xwv_webview.loadUrl("http://o.officeweb365.com/p/pv.aspx?PowerPointView=ReadingView&WOPISrc=http%3A%2F%2Fow365%2Fwopi%2Ffiles%2F%40%2Fwopi%3FvId%3DZlxSBXe6aF7MIHeDp01c0A%3D%3D&bs=cHB0LjFwcHQuY29tLjgwXDgxODA4MTYyMDUzNDU2NjUwMDEyMDM2NDlfMjM1NTU4OFzlpKfmsJTllYbliqHono3otYTorqHliJLkuaZQUFTmqKHmnb8yLnBwdHg=&token=S98e7hTQp9yVbUHTYXcgDe2DaoJb11M8&cancopy=")
      //  xwv_webview.loadUrl("file:///android_asset/html.html")
        bt_na2js.setOnClickListener {
            xwv_webview.loadUrl("javascript:callJS('aaaaaaaaasesasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa')")

        }
        if (iv_loading != null) {
            iv_loading.setImageResource(R.drawable.load_anim)
            animationDrawable = iv_loading.drawable as AnimationDrawable

        }
    }
     var loadUrl:Boolean = true
    internal inner class MyResourceClient(view: XWalkView) : XWalkResourceClient(view){
        override fun onLoadStarted(view: XWalkView?, url: String?) {//被触发了多次---------------------------
            super.onLoadStarted(view, url)
            if(loadUrl){
                animationDrawable.start()
                iv_loading.visibility = View.VISIBLE
            }



        }

        override fun onLoadFinished(view: XWalkView?, url: String?) {
            super.onLoadFinished(view, url)
            animationDrawable.stop()
            iv_loading.visibility = View.GONE
            loadUrl = false
        }

        override fun onReceivedSslError(view: XWalkView?, callback: ValueCallback<Boolean>?, error: SslError?) {
            super.onReceivedSslError(view, callback, error)
        }

        override fun onProgressChanged(view: XWalkView?, progressInPercent: Int) {
            super.onProgressChanged(view, progressInPercent)
            bt_na2js.text = progressInPercent.toString()+"==="
        }
    }

    internal inner class MyUIClient(view: XWalkView) : XWalkUIClient(view)

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
    override fun onPause() {
        super.onPause()
        if (xwv_webview != null) {
            xwv_webview.pauseTimers()
            xwv_webview.onHide()
        }
    }

    override fun onResume() {
        super.onResume()
        if (xwv_webview != null) {
            xwv_webview.resumeTimers()
            xwv_webview.onShow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (xwv_webview != null) {
            xwv_webview.onDestroy()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (xwv_webview != null) {
            xwv_webview.onActivityResult(requestCode, resultCode, data)
        }
    }

    public override fun onNewIntent(intent: Intent) {
        if (xwv_webview != null) {
            xwv_webview.onNewIntent(intent)
        }
    }
}
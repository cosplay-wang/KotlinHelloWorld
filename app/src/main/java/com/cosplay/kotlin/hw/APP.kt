package com.cosplay.kotlin.hw

import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.soter.wrapper.SoterWrapperApi
import com.tencent.soter.wrapper.wrap_callback.SoterProcessCallback
import com.tencent.soter.wrapper.wrap_callback.SoterProcessNoExtResult
import com.tencent.soter.wrapper.wrap_task.InitializeParam
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.zhuge.analysis.stat.ZhugeSDK
import com.zhuge.analysis.stat.ZhugeParam
import org.json.JSONObject
import android.support.annotation.NonNull
import android.support.multidex.MultiDex
import android.util.DisplayMetrics
import android.widget.Toast
import com.meituan.android.walle.WalleChannelReader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import java.util.*


/**
 * Author:wangzhiwei on 2018/8/14.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        okhttpClient = OkHttpClient.Builder()
                .addInterceptor(StethoInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS).build()
        initX5Web()
        Stetho.initializeWithDefaults(this)
        initZhuge()
        initWeichat()
        initBuglyTinker()

    }

    companion object {
        private lateinit var okhttpClient: OkHttpClient
        private lateinit var instance: Context
        fun getContext(): Context {
            return instance
        }

        fun getOkHttpClient(): OkHttpClient {
            return okhttpClient
        }
    }
    fun initZhuge(){
        val param = ZhugeParam.Builder()
                .did("我的id")
                .build()

        ZhugeSDK.getInstance().initWithParam(this, param)
        ZhugeSDK.getInstance().openDebug()
        //视频观看结束
        val pro = JSONObject()
        pro.put("名称", "非诚勿扰")
        pro.put("期数", "2016-11-02")
        ZhugeSDK.getInstance().setSuperProperty(pro)
    }

    fun initX5Web() {
        var cb: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                Log.e("x55555555", arg0.toString())
            }

            override fun onCoreInitFinished() {
                Log.e("x55555555", "yyyyyyyyyyyy")
            }
        }
        var map = hashMapOf(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER to true as Any)
        QbSdk.initTbsSettings(map)

        QbSdk.initX5Environment(getApplicationContext(), cb)

    }
    fun initWeichat(){
         var param  = InitializeParam.InitializeParamBuilder()
                .setScenes(0) // 场景值常量，后续使用该常量进行密钥生成或指纹认证
                .build()
        SoterWrapperApi.init(this,
                mGetIsSupportCallback,
                param)
    }

    private val mGetIsSupportCallback = SoterProcessCallback<SoterProcessNoExtResult> { result ->
       // DemoLogger.d(TAG, "soterdemo: get is support soter done. result: %s", result.toString())
        // 建议尽早准备ASK。主要有两个时机：1. 进程初始化时 2. 第一次使用业务任何一个业务时。这里在程序进程初始化的时候准备 ASK
        //            if(result.errCode == SoterProcessErrCode.ERR_OK && SoterWrapperApi.isSupportSoter()) {
        //                prepareASK();
        //            }
        // Edit 2017.11.27
        // 不再建议提前生成ASK，可能会拖慢启动。同时极少量机型有兼容性问题，提前生成ASK可能会导致不可预见错误
    }

    override fun onTerminate() {
        super.onTerminate()
        SoterWrapperApi.tryStopAllSoterTask()
    }
    val mDebug = true
    private fun initBuglyTinker() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = object : BetaPatchListener {
            override
            fun onPatchReceived(patchFileUrl: String) {
                if (mDebug) {

                    Toast.makeText(applicationContext, patchFileUrl, Toast.LENGTH_SHORT).show()
                }
            }
            override
            fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                if (mDebug) {

                    Toast.makeText(applicationContext, String.format(Locale.getDefault(),
                            "%s %d%%",
                            Beta.strNotificationDownloading,
                            (if (totalLength == 0L) 0 else savedLength * 100 / totalLength).toInt()), Toast.LENGTH_SHORT).show()
                }
            }
            override
            fun onDownloadSuccess(patchFilePath: String) {
                if (mDebug) {

                    Toast.makeText(applicationContext, patchFilePath, Toast.LENGTH_SHORT).show()
                    //                Beta.applyDownloadedPatch();
                }
            }
            override
            fun onDownloadFailure(msg: String) {
                if (mDebug) {

                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
            override
            fun onApplySuccess(msg: String) {

                if (mDebug) {

                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
            override
            fun onApplyFailure(msg: String) {
                if (mDebug) {

                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
            override
            fun onPatchRollback() {
                if (mDebug) {

                    Toast.makeText(applicationContext, "onPatchRollback", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //        long start = System.currentTimeMillis();
        val channel = WalleChannelReader.getChannel(this.applicationContext)
        Bugly.setAppChannel(this, channel)
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
        Bugly.init(applicationContext,
               "397e413db6e057d7", mDebug)
    }

    public override fun attachBaseContext(ctx: Context) {
        super.attachBaseContext(ctx)
       // MultiDex.install(ctx)

        // 安装bugly-tinker
        Beta.installTinker()
    }
}





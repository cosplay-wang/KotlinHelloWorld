package com.cosplay.kotlin.hw.ui.activity

import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.activity_manifest_meta.*


class ManifestMetaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manifest_meta)
        tv_value.text = "application标签的：" + getManifestApplicationMeta("name")
        tv_value_2.text = "activity标签的：" + getManifestActivtyMeta("activityname")
        tv_value_3.text = "版本信息：" + getVersion()
    }

    private fun getManifestApplicationMeta(key: String): String {
        var info: ApplicationInfo
        try {
            info = applicationContext.packageManager.getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA)
            return info.metaData.getString(key)

        } catch (e: Exception) {

        }
        return "没有" + key
    }

    private fun getManifestActivtyMeta(key: String): String {
        var info: ActivityInfo
        try {
            info = applicationContext.packageManager.getActivityInfo(
                    componentName, PackageManager.GET_META_DATA)
            return info.metaData.getString(key)

        } catch (e: Exception) {

        }
        return "没有" + key
    }

    /**
     * 读取BroadcastReceiver 节点  meta-data 信息
     */
    private fun readMetaDataFromBroadCast() {
        //  try {
        // val cn = ComponentName(this, DemoReceiver::class.java!!)
        val info = this.packageManager.getReceiverInfo(componentName,
                PackageManager.GET_META_DATA)
//            val mTag = info.metaData.getString("mTag")
//            Log.e(FragmentActivity.TAG, "mTag=" + mTag!!)
//        } catch (e: NameNotFoundException) {
//            e.printStackTrace()
//        }

    }

    /**
     * 读取Service 节点  meta-data 信息
     */
    private fun readMetaDataFromService() {
//        try {
//            val cn = ComponentName(this, DemoService::class.java!!)
        val info = this.packageManager.getServiceInfo(componentName,
                PackageManager.GET_META_DATA)
//            val mTag = info.metaData.getString("mTag")
//            Log.e(FragmentActivity.TAG, "mTag=" + mTag!!)
//        } catch (e: NameNotFoundException) {
//            e.printStackTrace()
//        }

    }


    /**
     * 测试版本信息
     */
    private fun getVersion(): String {//现在的版本都是以gradle文件中的为准了，不在manifest中配置了
        val pkg: PackageInfo
        try {
            pkg = packageManager.getPackageInfo(application.packageName, 0)
            val appName = pkg.applicationInfo.loadLabel(packageManager).toString()
            val versionName = pkg.versionName
            var versionCode = pkg.versionCode
            return "name：" + appName + "\n" + "versionName：" + versionName + "\n" + "versionCode:" + versionCode
//            println("appName:$appName")
//            println("versionName:$versionName")
        } catch (e: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return ""

    }
}

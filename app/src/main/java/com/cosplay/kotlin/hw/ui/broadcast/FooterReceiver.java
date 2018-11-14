package com.cosplay.kotlin.hw.ui.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Author:wangzhiwei on 2018/9/25.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class FooterReceiver {


    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;//内部类广播
    private ScreenStateListener mScreenStateListener;//对外接口

    public FooterReceiver(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }
    //注册广播与获取状态方法----
    public void startObserver(ScreenStateListener listener) {
        mScreenStateListener = listener;
        registerListener();//注册广播
        getScreenState();//获取状态
    }
    //注销广播方法----
    public void endObserver() {
        unregisterListener();
    }

    //获取screen状态
    @SuppressLint("NewApi")
    private void getScreenState() {
        if (mContext == null) {
            return;
        }

        PowerManager manager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOn();//屏幕开启
            }
        }
        else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();//屏幕关闭
            }
        }
    }
    //动态广播注册
    private void registerListener() {
        if (mContext != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);//home键
            mContext.registerReceiver(mScreenReceiver, filter);
        }
    }
    //销毁广播
    private void unregisterListener() {
        if (mContext != null)
            mContext.unregisterReceiver(mScreenReceiver);
    }

    //---------------------广播-----------------------------
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            // ----------------开屏时调用---------------
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.onScreenOn();
            }
            // ----------------锁屏时调用---------------
            else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.onScreenOff();
            }
            // ----------------解锁时调用---------------
            else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mScreenStateListener.onUserPresent();
            }
            // ----------------按home键时调用---------------
            else if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                String reason = intent.getStringExtra("reason");
                if (reason !=null && mScreenStateListener !=null) {
                    //短按home键
                    if (reason.equals("homekey")) {
                        // 短按home键
                        mScreenStateListener.onHomePressed();
                    }
                    // 长按home键
                    else if(reason.equals("recentapps")){
                        //mScreenStateListener.onHomePresseLong();//自己去写长按方法与短按类似
                    }
                }

            }
        }
    }

    public interface ScreenStateListener {// 返回给调用者屏幕状态信息
        public void onScreenOn();// 开屏

        public void onScreenOff();// 锁屏

        public void onUserPresent();// 解锁

        public void onHomePressed();// 解锁
    }
}

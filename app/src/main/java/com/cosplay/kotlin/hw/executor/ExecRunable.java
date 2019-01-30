package com.cosplay.kotlin.hw.executor;

import android.os.Looper;
import android.os.Message;

import com.cosplay.kotlin.hw.mvvm2.entity.Book;
import com.cosplay.kotlin.hw.mvvm2.util.JsonUtil;
import com.cosplay.kotlin.hw.mvvm2.util.StringContans;

import java.util.logging.Handler;

/**
 * Author:wangzhiwei on 2019/1/11.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public  class ExecRunable implements Runnable,ResultCallBack {
    android.os.Handler mainHandler = new android.os.Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StringContans.MainHandlerStart:
                    start();
                    break;
                case StringContans.MainHandlerComplete:
                    complete();
                    break;
            }
        }
    };
    @Override
    public void run() {
        mainHandler.obtainMessage(StringContans.MainHandlerStart).sendToTarget();
        doTimetTak();
        mainHandler.obtainMessage(StringContans.MainHandlerComplete).sendToTarget();
    }
    @Override
    public void start() {
    }

    @Override
    public void doTimetTak() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void error() {

    }

    @Override
    public void progress(int pro) {

    }
}

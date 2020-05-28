package com.cosplay.kotlin.hw.datacache;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Author:wangzhiwei on 2020/4/27.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AnswerController implements LifecycleObserver {
   private final  String TAG = getClass().getSimpleName();
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.e(TAG,"ONRESUME");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.e(TAG,"onPause");
    }

}

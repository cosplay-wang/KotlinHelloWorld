package com.cosplay.kotlin.hw.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;

/**
 * Author:wangzhiwei on 2020/4/30.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class EventBus implements Observable{
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static HashMap<String,Object> eventObserverMaps = new HashMap<>();
    private EventBus() {
    }

    public static EventBus getInstance() {
        return EvevtBusHolder.instance;
    }

    static class EvevtBusHolder {
        private static final EventBus instance = new EventBus();
    }

    @Override
    public void post(Object object) {
        //mHandler.post()
    }
    private void get(String name,Class c){
        if(eventObserverMaps.containsKey(name)){

        }else{
            eventObserverMaps.put(name,c);
        }
    }
}

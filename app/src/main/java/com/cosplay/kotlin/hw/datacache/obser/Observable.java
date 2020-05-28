package com.cosplay.kotlin.hw.datacache.obser;

import android.support.v7.app.AppCompatActivity;

import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2020/5/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public abstract class Observable extends AppCompatActivity {

    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer cacheObserver) {
        observers.add(cacheObserver);
    }

    public void detach(Observer cacheObserver) {
        observers.remove(cacheObserver);
    }


    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyObservers(){

        for(Observer observer : observers){
            observer.update(this);
        }
    }
}

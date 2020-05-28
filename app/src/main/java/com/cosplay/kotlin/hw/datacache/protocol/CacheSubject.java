package com.cosplay.kotlin.hw.datacache.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class CacheSubject implements CacheObservable {
    private List<CacheObserver> observers = new ArrayList<CacheObserver>();

    public void attach(CacheObserver cacheObserver) {
        observers.add(cacheObserver);
    }

    public void detach(CacheObserver cacheObserver) {
        observers.remove(cacheObserver);
    }

    @Override
    public void cacheData() {
        for (CacheObserver cacheObserver : observers) {
            cacheObserver.notifyCacheData();
        }
    }

    @Override
    public void cacheCountData() {
        for (CacheObserver cacheObserver : observers) {
            cacheObserver.notifyCacheCountData();
        }
    }
}

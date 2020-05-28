package com.cosplay.kotlin.hw.datacache.bean;

import android.arch.lifecycle.MutableLiveData;

import com.cosplay.kotlin.hw.datacache.protocol.CacheObservable;
import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;
import com.cosplay.kotlin.hw.datacache.protocol.CacheSubject;

import java.util.ArrayList;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class CacheDataList<T> extends ArrayList<T> implements CacheObservable {
    CacheObserver cacheObserver;
    public void attchCacheObserver(CacheObserver cacheObserver) {
        this.cacheObserver = cacheObserver;
    }

    @Override
    public void cacheData() {
        if (cacheObserver != null) {
            cacheObserver.notifyCacheData();
        }
    }

    @Override
    public void cacheCountData() {
        if (cacheObserver != null) {
            cacheObserver.notifyCacheCountData();
        }
    }

}

package com.cosplay.kotlin.hw.datacache.protocol;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface CacheObserver {
    void notifyCacheData();
    void notifyCacheCountData();
}
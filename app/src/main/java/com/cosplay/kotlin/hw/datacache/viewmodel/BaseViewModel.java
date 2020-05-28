package com.cosplay.kotlin.hw.datacache.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;
import com.cosplay.kotlin.hw.mixAudio.util.ToastUtil;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BaseViewModel extends ViewModel implements CacheObserver {
    private int cacheCount = 0;
    private final static int cacheSaveCount = 5;

    @Override
    public void notifyCacheData() {
        ToastUtil.showToast("更新==存储");
    }

    @Override
    public void notifyCacheCountData() {
        cacheCount++;
        if (cacheCount >= cacheSaveCount) {
            notifyCacheData();
            cacheCount = 0;
        } else {
            ToastUtil.showToast("更新==计数存储，计数累计：" + cacheCount);
        }
    }
}

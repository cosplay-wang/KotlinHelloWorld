package com.cosplay.kotlin.hw.mvvm2.base;

import android.arch.lifecycle.MutableLiveData;
import android.os.MessageQueue;

import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface BaseLoadListener<T> {
    void loadListSuccess(List<T> dataList);
    void loadSuccess(T data);
    void loadFailure(String errorMessage);
    void loadComplete();


}

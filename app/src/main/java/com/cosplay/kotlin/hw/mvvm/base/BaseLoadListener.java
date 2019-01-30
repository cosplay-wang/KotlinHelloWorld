package com.cosplay.kotlin.hw.mvvm.base;

import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface BaseLoadListener<T> {
    void loadSuccess(List<T> dataList);
    void loadFailure(String errorMessage);
    void loadComplete();
}

package com.cosplay.kotlin.hw.mvvm2.base;

import android.view.View;

/**
 * Author:wangzhiwei on 2019/1/9.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface OnItemClickListener<T> {
    void itemClick(T entity);
}

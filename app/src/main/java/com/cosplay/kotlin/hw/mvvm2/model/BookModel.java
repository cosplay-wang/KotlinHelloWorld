package com.cosplay.kotlin.hw.mvvm2.model;


import android.arch.lifecycle.MutableLiveData;

import com.cosplay.kotlin.hw.mvvm2.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm2.entity.Book;

/**
 * Author:wangzhiwei on 2019/1/8.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface BookModel {
    void loadBookData(int page, BaseLoadListener<Book> loadListener);
}

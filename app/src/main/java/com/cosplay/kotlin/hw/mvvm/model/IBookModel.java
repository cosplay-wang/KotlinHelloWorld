package com.cosplay.kotlin.hw.mvvm.model;

import com.cosplay.kotlin.hw.mvvm.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm.entity.BookEntity;

import java.io.Serializable;

/**
 * Author:wangzhiwei on 2018/12/27.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface IBookModel {
    void loadBookData(int page, BaseLoadListener<BookEntity> loadListener);

}

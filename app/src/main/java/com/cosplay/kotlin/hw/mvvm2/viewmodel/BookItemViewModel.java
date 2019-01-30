package com.cosplay.kotlin.hw.mvvm2.viewmodel;

import android.util.Log;

import com.cosplay.kotlin.hw.mvvm2.entity.Book;

/**
 * Author:wangzhiwei on 2019/1/9.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookItemViewModel {

    public void itemClick(Book.ListBean listBean) {
        Log.e("adad", "dddddi" + listBean.getSelf_id());
    }
}

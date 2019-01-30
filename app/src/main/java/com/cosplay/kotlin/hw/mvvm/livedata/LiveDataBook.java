package com.cosplay.kotlin.hw.mvvm.livedata;

import android.arch.lifecycle.LiveData;

import com.cosplay.kotlin.hw.mvvm.entity.BookEntity;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class LiveDataBook extends LiveData<BookEntity> {
    private BookEntity bookEntity;

    public LiveDataBook(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
        setValue(bookEntity);
    }
}

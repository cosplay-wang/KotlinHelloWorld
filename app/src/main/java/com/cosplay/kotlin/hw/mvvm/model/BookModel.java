package com.cosplay.kotlin.hw.mvvm.model;

import android.os.Handler;
import android.os.Message;

import com.cosplay.kotlin.hw.mvvm.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookModel implements IBookModel {
    @Override
    public void loadBookData(int page, final BaseLoadListener<BookEntity> loadListener) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<BookEntity> bookEntities = new ArrayList<>();
                for(int i =0;i<100;i++){
                    bookEntities.add(new BookEntity("NNNNAME---"+i));
                }

                loadListener.loadSuccess(bookEntities);
            }
        }, 3000);
    }
}

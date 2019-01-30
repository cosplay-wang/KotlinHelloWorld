package com.cosplay.kotlin.hw.mvvm.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.cosplay.kotlin.hw.BR;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookEntity extends BaseObservable{
    String name;
    String title;
    int page;
    int totlePage;
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotlePage() {
        return totlePage;
    }

    public void setTotlePage(int totlePage) {
        this.totlePage = totlePage;

    }

    public BookEntity(String name) {
        this.name = name;
    }

    public BookEntity() {
    }

}

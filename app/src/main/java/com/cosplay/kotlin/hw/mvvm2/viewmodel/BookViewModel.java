package com.cosplay.kotlin.hw.mvvm2.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.cosplay.kotlin.hw.mvvm2.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm2.entity.Book;
import com.cosplay.kotlin.hw.mvvm2.model.BookModelImpl;

import java.util.List;

/**
 * Author:wangzhiwei on 2019/1/8.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookViewModel extends ViewModel implements BaseLoadListener<Book> {
    public BookModelImpl bookModel;
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public MutableLiveData<List<Book.ListBean>> bookMutableLiveData = new MutableLiveData<>();
    int page = 1;
    public ListStatus status = ListStatus.Nromal;

    public enum ListStatus {
        Refreshing,
        LoadingMore,
        Nromal,
    }

    public BookViewModel() {
        title.set("nnnnnnnnn");
        name.set("namename");
        bookModel = new BookModelImpl();
    }

    public void refreshBookData() {
        status = ListStatus.Refreshing;
        page = 1;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        bookModel.loadBookData(page, BookViewModel.this);
//            }
//        }).start();

    }

    public void getMoreBookData() {
        status = ListStatus.LoadingMore;
        page = page + 1;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        bookModel.loadBookData(page, BookViewModel.this);
//            }
//        }).start();

    }


    @Override
    public void loadListSuccess(List<Book> dataList) {
        status = ListStatus.Nromal;
    }

    @Override
    public void loadSuccess(Book data) {
        name.set("--加载完成--"+status);

        if (status == ListStatus.Refreshing) {
            bookMutableLiveData.setValue(data.getList());
        } else {
            bookMutableLiveData.getValue().addAll(data.getList());
            bookMutableLiveData.setValue(bookMutableLiveData.getValue());
        }

        status = ListStatus.Nromal;
    }

    @Override
    public void loadFailure(String errorMessage) {
        status = ListStatus.Nromal;
    }

    @Override
    public void loadComplete() {
        status = ListStatus.Nromal;
    }
}

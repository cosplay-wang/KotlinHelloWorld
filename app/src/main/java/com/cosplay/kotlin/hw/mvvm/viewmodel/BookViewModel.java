package com.cosplay.kotlin.hw.mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.databinding.ActivityMvvm1Binding;
import com.cosplay.kotlin.hw.mvvm.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm.entity.BookEntity;
import com.cosplay.kotlin.hw.mvvm.model.BookModel;

import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/27.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookViewModel implements BaseLoadListener<BookEntity> {
    private ActivityMvvm1Binding activityViewDataBinding;
    public BookModel bookModel;




    public ObservableField<String> name = new ObservableField<>();
    public ObservableBoolean beanll = new ObservableBoolean();
    public LiveData<BookEntity> bookEntityLiveData = new LiveData<BookEntity>() {
    } ;
    public MutableLiveData<BookEntity> bookEntityMutableLiveData = new MutableLiveData<>();
    public BookViewModel(ActivityMvvm1Binding activityViewDataBinding) {
        this.activityViewDataBinding = activityViewDataBinding;
        bookModel = new BookModel();
    }
//    private  void hhh(){
//        beanll.s
//    }

    public void getBookData(){
        bookModel.loadBookData(1,this);
    }

    @Override
    public void loadSuccess(List<BookEntity> dataList) {
       // activityViewDataBinding.updateRy(dataList);
    }


    @Override
    public void loadFailure(String errorMessage) {

    }

    @Override
    public void loadComplete() {

    }
    public void click(View view){
        ((TextView)view).setText("点击了");
    }
}

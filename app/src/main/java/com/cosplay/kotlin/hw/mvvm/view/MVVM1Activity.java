package com.cosplay.kotlin.hw.mvvm.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;

import com.cosplay.kotlin.hw.BR;
import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.databinding.ActivityMvvm1Binding;
import com.cosplay.kotlin.hw.mvvm.adapter.RvAdapter;
import com.cosplay.kotlin.hw.mvvm.entity.BookEntity;
import com.cosplay.kotlin.hw.mvvm.livedata.LiveDataBook;
import com.cosplay.kotlin.hw.mvvm.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

import static android.os.Looper.getMainLooper;

public class MVVM1Activity extends AppCompatActivity{
    RvAdapter rvAdapter;
    ActivityMvvm1Binding dataBinding;
    List<BookEntity> bookEntityList = new ArrayList<>();
    BookViewModel bookViewModel;
    BookEntity bookEntity;
    LiveDataBook liveDataBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvAdapter = new RvAdapter(bookEntityList, BR.book,this,R.layout.rv_item_mvvm_layout);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm1);
        bookEntity = new BookEntity();
        bookEntity.setName("11");
        dataBinding.setBookmodel(bookEntity);
        dataBinding.myrv.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.setAdapter(rvAdapter);

        bookViewModel = new BookViewModel(dataBinding);
        dataBinding.setViewModel(bookViewModel);
        bookViewModel.getBookData();
        liveDataBook = new LiveDataBook(bookEntity);
        liveDataBook.observe(this, new Observer<BookEntity>() {
            @Override
            public void onChanged(@Nullable BookEntity bookEntity1) {
                bookEntity.setName("sdadadad");
                dataBinding.setBookmodel(bookEntity);
            }
        });


    }


    public void updateRy(List<BookEntity> bookEntities) {
        bookEntity.setName("sdadadad");
//        dataBinding.setBookmodel(bookEntities.get(0));
        bookEntityList.addAll(bookEntities);
        dataBinding.setAdapter(rvAdapter);
    }



}

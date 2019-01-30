package com.cosplay.kotlin.hw.mvvm2;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.cosplay.kotlin.hw.BR;
import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.databinding.ActivityMvvm2Binding;
import com.cosplay.kotlin.hw.mvvm2.adapter.RvAdapter;
import com.cosplay.kotlin.hw.mvvm2.base.OnItemClickListener;
import com.cosplay.kotlin.hw.mvvm2.entity.Book;
import com.cosplay.kotlin.hw.mvvm2.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

public class Mvvm2Activity extends AppCompatActivity implements OnItemClickListener {
    BookViewModel bookViewModel;
    RvAdapter rvAdapter;
    ActivityMvvm2Binding mvvm2DataBinding;
    List<Book.ListBean> listBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvvm2DataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm2);
        bookViewModel = new BookViewModel();
        rvAdapter = new RvAdapter(listBeanList, BR.book, this, R.layout.rv_item_mvvm2_layout);
        mvvm2DataBinding.setBook2Model(bookViewModel);
        mvvm2DataBinding.rvTitle.setLayoutManager(new LinearLayoutManager(this));
        mvvm2DataBinding.rvTitle.setAdapter(rvAdapter);
        bookViewModel.bookMutableLiveData.observe(this, new Observer<List<Book.ListBean>>() {
            @Override
            public void onChanged(@Nullable List<Book.ListBean> listBeans) {
                mvvm2DataBinding.idSwipe.setRefreshing(false);
                listBeanList.clear();
                listBeanList.addAll(listBeans);
                Log.e("nchangeData",  "--" + listBeans.size());
                rvAdapter.notifyDataSetChanged();

            }
        });
        mvvm2DataBinding.idSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mvvm2DataBinding.idSwipe.setRefreshing(true);
                bookViewModel.refreshBookData();

            }
        });
        mvvm2DataBinding.rvTitle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastPosition >= layoutManager.getItemCount() - 2 && bookViewModel.status == BookViewModel.ListStatus.Nromal) {//容错处理，保证滑到最后一条时一定可以加载更多
                            bookViewModel.getMoreBookData();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        bookViewModel.refreshBookData();
    }

    @Override
    public void itemClick(Object entity) {
        Toast.makeText(getApplicationContext(), ((Book.ListBean) entity).getTeacher_uid(), Toast.LENGTH_SHORT).show();
    }
}

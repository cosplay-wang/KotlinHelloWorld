package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.mvvm2.adapter.RvAdapter;
import com.cosplay.kotlin.hw.ui.adapter.MainRvAp;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SmartRefreshActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> dataList;
    MainRvAp mainRvAp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_refresh);
        recyclerView = findViewById(R.id.rv_fresh);
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRvAp = new MainRvAp(dataList, this, new clickItem(), new clickInner());
        recyclerView.setAdapter(mainRvAp);

        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.smart_fresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    class clickItem implements MainRvAp.ItemClick {
        @Override
        public void onItemClick(@NotNull View v, int position) {

        }
    }

    class clickInner implements MainRvAp.ItemInnerClick {
        @Override
        public void onItemInnerClick(@NotNull View v, int position) {

        }
    }

    private void getData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("posotion" + i);
        }
    }
}
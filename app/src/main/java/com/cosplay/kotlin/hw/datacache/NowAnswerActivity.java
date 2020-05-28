package com.cosplay.kotlin.hw.datacache;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.datacache.bean.AnswerBean;
import com.cosplay.kotlin.hw.datacache.bean.CacheDataList;
import com.cosplay.kotlin.hw.datacache.bean.NewAnswerBean;
import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;
import com.cosplay.kotlin.hw.eventbus.EventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class NowAnswerActivity extends BaseNowAnswerActivity {
    TextView tvStstus;
    RecyclerView rvList;
    AnswerRvAdapter answerRvAdapter;
    CacheDataList<NewAnswerBean> cacheDataList = new CacheDataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_answer);
        initViews();
        initData();
        initAdapter();
        EventBus.getDefault().register(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventDeal(EventBean eventBean){
        Log.e("eventDeal", "eventBean:"+eventBean.getName()+"--"+eventBean.getObject());
    }

    private void initViews() {
        tvStstus = findViewById(R.id.tv_show_status);
        rvList = findViewById(R.id.rv_list);
    }

    private void initData() {
        cacheDataList.attchCacheObserver(this);
        for (int po = 0; po < 20; po++) {
            cacheDataList.add(new NewAnswerBean(po, "time" + po, "data:" + po));
        }
    }

    private void initAdapter() {
        answerRvAdapter = new AnswerRvAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(answerRvAdapter);
    }

    class AnswerRvAdapter extends RecyclerView.Adapter<AnswerRvAdapter.ViewHolder> {
        @NonNull
        @Override
        public AnswerRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            TextView textView = new TextView(NowAnswerActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 25;
            layoutParams.topMargin = 20;
            layoutParams.bottomMargin = 20;
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setBackgroundColor(NowAnswerActivity.this.getResources().getColor(R.color.base_blue));
            return new AnswerRvAdapter.ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull NowAnswerActivity.AnswerRvAdapter.ViewHolder viewHolder, int i) {
            NewAnswerBean answerBean = cacheDataList.get(i);
            viewHolder.tvShow.setText(answerBean.getId() + ":" + answerBean.getAnswerJson());
        }

        @Override
        public int getItemCount() {
            return cacheDataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.tvShow = (TextView) itemView;
                tvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new EventBean("name",1));
                        Log.e("AnswerActivty", "onClick");
                        NewAnswerBean answerBean = cacheDataList.get(getAdapterPosition());
                        String json = answerBean.getAnswerJson();
                        if (getAdapterPosition() % 2 == 0) {
                            answerBean.setAnswerJson(json + ":-change");
                            cacheDataList.cacheData();
                        } else if (getAdapterPosition() % 2 == 1) {
                            answerBean.setDuration(json + ":-change");
                            cacheDataList.cacheCountData();
                        }
                        answerRvAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new EventBean("change",answerBean));
                    }
                });
            }

            TextView tvShow;

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean event) {
        Log.e("onMessageEvent",event.getName()+"---"+event.getObject().toString());
    }
}

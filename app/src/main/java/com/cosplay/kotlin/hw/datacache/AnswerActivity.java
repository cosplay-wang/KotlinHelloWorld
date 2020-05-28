package com.cosplay.kotlin.hw.datacache;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.cosplay.kotlin.hw.datacache.viewmodel.AnswerViewModel;

public class AnswerActivity extends AppCompatActivity {
    AnswerViewModel answerViewModel;
    TextView tvStstus;
    RecyclerView rvList;
    AnswerRvAdapter answerRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initBasic();
        initViews();
        initData();
        initAdapter();
    }

    private void initViews() {
        tvStstus = findViewById(R.id.tv_show_status);
        rvList = findViewById(R.id.rv_list);
    }

    private void initData() {
        answerViewModel.obserAnswerList.setValue(new CacheDataList<AnswerBean>());
        for (int po = 0; po < 20; po++) {
            answerViewModel.obserAnswerList.getValue().add(new AnswerBean(po, "time" + po, "data:" + po));
        }
    }

    private void initAdapter() {
        answerRvAdapter = new AnswerRvAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(AnswerActivity.this));
        rvList.setAdapter(answerRvAdapter);
    }

    private void initBasic() {
        answerViewModel = ViewModelProviders.of(AnswerActivity.this).get(AnswerViewModel.class);
        answerViewModel.obserAnswerList.observe(this, new Observer<CacheDataList<AnswerBean>>() {
            @Override
            public void onChanged(@Nullable CacheDataList<AnswerBean> answerBeans) {
                Log.e("AnswerActivty", "change");
                answerViewModel.notifyCacheData();
            }
        });
    }

    class AnswerRvAdapter extends RecyclerView.Adapter<AnswerRvAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            TextView textView = new TextView(AnswerActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 25;
            layoutParams.topMargin = 20;
            layoutParams.bottomMargin = 20;
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setBackgroundColor(AnswerActivity.this.getResources().getColor(R.color.base_blue));
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            AnswerBean answerBean = answerViewModel.obserAnswerList.getValue().get(i);
            viewHolder.tvShow.setText(answerBean.getId() + ":" + answerBean.getAnswerJson());
        }

        @Override
        public int getItemCount() {
            return answerViewModel.obserAnswerList.getValue().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.tvShow = (TextView) itemView;
                tvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("AnswerActivty", "onClick");
                        AnswerBean answerBean = answerViewModel.obserAnswerList.getValue().get(getAdapterPosition());
                        String json = answerBean.getAnswerJson();
                        answerBean.setAnswerJson(json+"--change");
                        answerViewModel.obserAnswerList.postValue(answerViewModel.obserAnswerList.getValue());
                        answerRvAdapter.notifyDataSetChanged();
                    }
                });
            }

            TextView tvShow;

        }
    }
}

package com.cosplay.kotlin.hw.mvvm2.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cosplay.kotlin.hw.databinding.RvItemMvvm2LayoutBinding;
import com.cosplay.kotlin.hw.mvvm2.Mvvm2Activity;
import com.cosplay.kotlin.hw.mvvm2.base.OnItemClickListener;
import com.cosplay.kotlin.hw.mvvm2.entity.Book;
import com.cosplay.kotlin.hw.mvvm2.viewmodel.BookItemViewModel;

import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CommonHolder> {
    protected Context mContext;
    //所有 item 的数据集合
    protected List mDatas;
    //item 布局文件 id
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    // mvvm绑定的viewModel引用
    private int mVariableId;


    //构造方法
    public RvAdapter(List datas, int variableId, Context context, int layoutId) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
        mVariableId = variableId;
    }


    public List getmDatas() {
        return mDatas;
    }

    public void setmDatas(List mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), mLayoutId, viewGroup, false);
        ((RvItemMvvm2LayoutBinding)binding).setBookitemmodel((OnItemClickListener)((Mvvm2Activity)mContext));
        CommonHolder myHolder = new CommonHolder(binding.getRoot());
        myHolder.setBinding(binding);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder commonHolder, int position) {
        commonHolder.binding.setVariable(mVariableId, mDatas.get(position));
        commonHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    class CommonHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public CommonHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }


}

package com.cosplay.kotlin.hw.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.layout.view.*

/**
 * Author:wangzhiwei on 2018/8/9.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
class MainRvAp(val items: List<String>, val context: Context, var itemclick: ItemClick, var itemInnrclick: ItemInnerClick) : RecyclerView.Adapter<MainRvAp.MainRvApViewHolder>() {

    class MainRvApViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(data: String, click: OnClick, innrclick: OnInnerClick) {
            with(data) {
                itemView.main_rv_item_tv.text = data
                itemView.setOnClickListener(click)
                itemView.main_rv_item_tv.setOnClickListener(innrclick)
            }
        }
    }
    override fun onBindViewHolder(holder: MainRvApViewHolder, position: Int) {
        holder.bindData(items[position], OnClick(position), OnInnerClick(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvApViewHolder {
        return MainRvApViewHolder(LayoutInflater.from(context).inflate(R.layout.layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items?.size
    }

    inner class OnClick(var position: Int) : View.OnClickListener {
        override fun onClick(v: View) {
                itemclick?.onItemClick(v, position)
        }
    }

    inner class OnInnerClick(var position: Int) : View.OnClickListener {
        override fun onClick(v: View) {
                itemInnrclick?.onItemInnerClick(v, position)
        }
    }

    interface ItemClick {
        fun onItemClick(v: View, position: Int)
    }

    interface ItemInnerClick {
        fun onItemInnerClick(v: View, position: Int)
    }
}
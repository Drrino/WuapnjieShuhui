package com.drrino.wuapnjieshuhui.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.News
import kotlinx.android.synthetic.main.item_child_news.view.*
import java.util.*

class NewsAdapter(var data: List<News> = ArrayList())
    : RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): NewsAdapterViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_child_news, parent, false)
        return NewsAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder?, position: Int) {
        bindView(holder?.itemView, position)
    }

    private fun bindView(itemView: View?, position: Int) {
        val news = data[position]

        itemView?.tv_title!!.text = news.title
        itemView.tv_time.text = news.createdTime
        if (position % 2 == 0) itemView.container.setBackgroundResource(R.color.alpha_grey)

        itemView.container.setOnClickListener {
//            WebDetailDialog(itemView.context, news, NewsDetailSource())
        }
    }

    override fun getItemCount(): Int  = data.size

    class NewsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

package com.drrino.wuapnjieshuhui.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.Cover
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cover.view.*

class CoverAdapter(var data: List<Cover> = ArrayList(), var itemClick: (View, Int) -> Unit) : RecyclerView.Adapter<CoverAdapter.CoverViewHolder>() {
    override fun onBindViewHolder(p0: CoverViewHolder, p1: Int) {
        bindView(p0.itemView, p1)
    }

    private fun bindView(itemView: View, p1: Int) {
        val cover = data[p1]
        itemView.tv_cover.text = cover.title
        Picasso.with(itemView.context).load(cover.coverUrl).into(itemView.iv_cover)
        itemView.coverContainer.setOnClickListener {
            itemClick(itemView, p1)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): CoverViewHolder {
        val itemView = LayoutInflater.from(p0?.context).inflate(R.layout.item_cover, p0, false)
        return CoverViewHolder(itemView)
    }

    class CoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun refreshData(newData: ArrayList<Cover>) {
        data = newData
        notifyDataSetChanged()
    }
}

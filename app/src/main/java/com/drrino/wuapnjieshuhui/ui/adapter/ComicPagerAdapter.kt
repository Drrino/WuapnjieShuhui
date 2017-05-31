package com.drrino.wuapnjieshuhui.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.drrino.wuapnjieshuhui.domain.Comic
import com.drrino.wuapnjieshuhui.ui.fragment.ComicFragment

class ComicPagerAdapter(var data: ArrayList<Comic> = ArrayList<Comic>(), fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment? = newInstance(data[p0].comicUrl)

    override fun getCount(): Int = data.size

    fun newInstance(url: String) = ComicFragment(url)

    fun refreshData(newData: ArrayList<Comic>) {
        data = newData
        notifyDataSetChanged()
    }
}

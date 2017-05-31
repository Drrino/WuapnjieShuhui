package com.drrino.wuapnjieshuhui.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.Cover
import com.drrino.wuapnjieshuhui.ui.adapter.CoverAdapter
import java.util.ArrayList

class BookFragment:Fragment(){
    companion object{
        val COMIC_LIST_URL = "http://ishuhui.net/ComicBookList/"
    }
    var mData = ArrayList<Cover>()
    lateinit var adapter: CoverAdapter
    lateinit var bookList: RecyclerView
    lateinit var bookRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View?) {
        bookRefresh = view?.findViewById(R.id.homeRefresh) as SwipeRefreshLayout
        bookList = view?.findViewById(R.id.homeList) as RecyclerView

        bookList.layoutManager = GridLayoutManager(context, 2)
    }
}

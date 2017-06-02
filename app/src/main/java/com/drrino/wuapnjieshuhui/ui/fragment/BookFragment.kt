package com.drrino.wuapnjieshuhui.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.Cover
import com.drrino.wuapnjieshuhui.domain.network.BookSource
import com.drrino.wuapnjieshuhui.ui.activity.BookDetailActivity
import com.drrino.wuapnjieshuhui.ui.adapter.CoverAdapter
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.ArrayList

class BookFragment:Fragment(){
    companion object{
        val BOOK_LIST_URL = "http://ishuhui.net/ComicBookList/"
    }
    var mData = ArrayList<Cover>()
    lateinit var adapter: CoverAdapter
    lateinit var bookList: RecyclerView
    lateinit var bookRefresh: SwipeRefreshLayout
    val mHandler : Handler = Handler()

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
        adapter = CoverAdapter{_:View,position:Int->jumpDetail(position)}
        bookList.adapter = adapter

        bookRefresh.setOnRefreshListener {
            loadData()
        }
        bookRefresh.post { bookRefresh.isRefreshing = true }
    }

    private fun jumpDetail(position: Int) {
        var intent = Intent(context, BookDetailActivity().javaClass)
        intent.putExtra("cover", mData[position].coverUrl)
        intent.putExtra("url", mData[position].link)
        intent.putExtra("title", mData[position].title)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {
            loadData()
        }
    }

    private fun loadData() {
        async {
            val data = BookSource().obtain(BOOK_LIST_URL)
            uiThread {
                mData = data
                adapter.refreshData(data)
                mHandler.postDelayed({
                    bookRefresh.isRefreshing = false
                },500)
            }
        }
    }
}

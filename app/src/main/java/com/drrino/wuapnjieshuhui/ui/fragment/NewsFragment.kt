package com.drrino.wuapnjieshuhui.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.NewsContainer
import com.drrino.wuapnjieshuhui.domain.network.NewsSource
import com.drrino.wuapnjieshuhui.ui.adapter.NewsContainerAdapter
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class NewsFragment : Fragment() {
    companion object {
        val AIM_URL = "http://ishuhui.net/CMS/"
    }

    var mData = ArrayList<NewsContainer>()
    lateinit var newsList: RecyclerView
    lateinit var newsRefresh: SwipeRefreshLayout
    lateinit var adapter: NewsContainerAdapter
    val mHandler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRefresh = view?.findViewById(R.id.homeRefresh) as SwipeRefreshLayout
        newsList = view.findViewById(R.id.homeList) as RecyclerView

        newsList.layoutManager = LinearLayoutManager(context)
        adapter = NewsContainerAdapter()
        newsList.adapter = adapter

        newsRefresh.setOnRefreshListener {
            loadData()
        }
        newsRefresh.post { newsRefresh.isRefreshing = true }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {
            loadData()
        }

    }

    private fun loadData() {
        async {
            var data = NewsSource().obtain(AIM_URL)
            uiThread {
                mData = data
                adapter.refreshData(data)
                mHandler.postDelayed({
                    newsRefresh.isRefreshing = false
                },500)
            }
        }
    }
}

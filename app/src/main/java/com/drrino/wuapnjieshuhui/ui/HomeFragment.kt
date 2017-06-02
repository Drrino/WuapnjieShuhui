package com.drrino.wuapnjieshuhui.ui

import android.content.Intent
import android.database.Observable
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
import com.drrino.wuapnjieshuhui.domain.network.CoverSource
import com.drrino.wuapnjieshuhui.ui.activity.ComicActivity
import com.drrino.wuapnjieshuhui.ui.adapter.CoverAdapter
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class HomeFragment : Fragment() {

    companion object {
        val AIM_URL = "http://ishuhui.net/?PageIndex="
    }

    var mData = ArrayList<Cover>()

    lateinit var coverList: RecyclerView

    lateinit var homeRefresh: SwipeRefreshLayout

    lateinit var adapter: CoverAdapter

    var pageSize: Int = 1

    var isRefresh: Boolean = false

    val mHandler :Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View?) {
        homeRefresh = view?.findViewById(R.id.homeRefresh) as SwipeRefreshLayout
        coverList = view?.findViewById(R.id.homeList) as RecyclerView

        coverList.layoutManager = GridLayoutManager(context, 2)

        adapter = CoverAdapter { _: View, position: Int -> jump2Comic(position) }
        coverList.adapter = adapter

        homeRefresh.setOnRefreshListener {
            pageSize = 1
            isRefresh = true
            loadView(pageSize, isRefresh)
        }

        homeRefresh.post { homeRefresh.isRefreshing = true }
        attachToScroll(coverList, coverList.layoutManager as GridLayoutManager, adapter)
    }

    fun attachToScroll(recyclerView: RecyclerView, layoutManager: GridLayoutManager, mAdapter: CoverAdapter) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.itemCount - 3
                    isRefresh = false
                    if (isBottom) {
                        pageSize += 1
                        homeRefresh.isRefreshing = true
                        loadView(pageSize, isRefresh)
                    }
                }
            }
        })
    }


    private fun jump2Comic(position: Int) {
        var intent = Intent(context, ComicActivity().javaClass)
        intent.putExtra("url", mData[position].link)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {
            isRefresh = true
            loadView(pageSize, isRefresh)
        }
    }


    private fun loadView(page: Int, refresh: Boolean) {
        async {
            val data = CoverSource().obtain(AIM_URL + page)
            uiThread {
                mData = data
                if (refresh) {
                    adapter.refreshData(data)
                } else {
                    adapter.notifyData(data)
                }
                adapter.notifyDataSetChanged()
                mHandler.postDelayed({
                    homeRefresh.isRefreshing = false
                },500)
            }
        }
    }
}
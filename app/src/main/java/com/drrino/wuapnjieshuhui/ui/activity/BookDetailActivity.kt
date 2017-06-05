package com.drrino.wuapnjieshuhui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.BookDetail
import com.drrino.wuapnjieshuhui.domain.Page
import com.drrino.wuapnjieshuhui.domain.network.BookDetailSource
import com.drrino.wuapnjieshuhui.domain.network.snackbar
import com.drrino.wuapnjieshuhui.ui.adapter.PageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class BookDetailActivity : AppCompatActivity() {
    lateinit var url: String
    lateinit var pageList: RecyclerView
    lateinit var adapter: PageAdapter
    lateinit var pageRefresh: SwipeRefreshLayout
    lateinit var bookDetail: BookDetail
    val mHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setSupportActionBar(tool_bar)

        initView()
    }

    private fun initView() {
        val coverUrl = intent.getStringExtra("cover")
        var title = intent.getStringExtra("title")
        url = intent.getStringExtra("url")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = title
        Picasso.with(this).load(coverUrl).into(backgroundImage)

        pageRefresh = find(R.id.pageRefresh)
        pageRefresh.setOnRefreshListener { loadData() }

        pageList = find(R.id.pageList)
        pageList.layoutManager = GridLayoutManager(this, 4)

        adapter = PageAdapter { _, position ->
            //            if (title.contains("SBS")) {
//                val news = News(bookDetail[position].title, "", bookDetail[position].link)
//                WebDetailDialog(this, news, SBSSource())
//            } else
            jump2Read(position)
        }
        pageList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        pageRefresh.post { pageRefresh.isRefreshing = true }
        loadData()
    }

    private fun loadData() {
        async {
            bookDetail = BookDetailSource().obtain(url)
            val data = bookDetail.pages as ArrayList<Page>

            uiThread {
                adapter.refreshData(data)

                if (bookDetail.size() == 0) {
                    pageList.snackbar(R.string.page_load_error)
                }
                mHandler.postDelayed({
                    pageRefresh.isRefreshing = false
                }, 500)
            }
        }
    }

    private fun jump2Read(position: Int) {
        var intent = Intent(this, ComicActivity().javaClass)
        intent.putExtra("url", bookDetail[position].link)
        startActivity(intent)
    }

}


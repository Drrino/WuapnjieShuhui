package com.drrino.wuapnjieshuhui.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.Comic
import com.drrino.wuapnjieshuhui.domain.network.ComicSoure
import com.drrino.wuapnjieshuhui.ui.adapter.ComicPagerAdapter
import kotlinx.android.synthetic.main.activity_comic.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class ComicActivity : AppCompatActivity() {

    var mData = ArrayList<Comic>()
    lateinit var url: String
    lateinit var adapter: ComicPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        url = intent.getStringExtra("url")

        adapter = ComicPagerAdapter(mData, supportFragmentManager)
        comicPager.adapter = adapter
        comicPager.offscreenPageLimit = 2
    }

    override fun onResume() {
        super.onResume()
        async {
            val data = ComicSoure().obtain(url)
            mData = data

            uiThread {
                adapter.refreshData(data)
            }
        }
    }
}


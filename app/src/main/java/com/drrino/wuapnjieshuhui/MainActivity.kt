package com.drrino.wuapnjieshuhui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.drrino.wuapnjieshuhui.ui.HomeFragment
import com.drrino.wuapnjieshuhui.ui.adapter.ContentPagerAdapter
import com.drrino.wuapnjieshuhui.ui.fragment.BookFragment
import com.drrino.wuapnjieshuhui.ui.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val nameResList: ArrayList<Int> = arrayListOf(R.string.tab_one, R.string.tab_two, R.string.tab_three)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        val fragments = ArrayList<Fragment>()

        fragments.add(HomeFragment())
        fragments.add(BookFragment())
        fragments.add(NewsFragment())

        val nameList = nameResList.map(this::getString)

        viewPager.adapter = ContentPagerAdapter(fragments,nameList,supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
    }
}

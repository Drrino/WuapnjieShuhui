package com.drrino.wuapnjieshuhui.domain.network

import com.drrino.wuapnjieshuhui.domain.BookDetail
import com.drrino.wuapnjieshuhui.domain.BookInfo
import com.drrino.wuapnjieshuhui.domain.Page
import org.jsoup.Jsoup
import java.util.*

class BookDetailSource : Source<BookDetail> {
    override fun obtain(url: String): BookDetail {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val pages = ArrayList<Page>()
        val elements = doc.select("div.volumeControl").select("a")

        for (element in elements) {
            val title = element.text()
            val link = "http://ishuhui.net/" + element.attr("href")
            val page = Page(title, link)
            pages.add(page)
        }

        val updateTime = doc.select("div.mangaInfoDate").text()
        val detail = doc.select("div.mangaInfoTextare").text()
        val info = BookInfo(updateTime, detail)

        return BookDetail(pages, info)
    }
}

package com.drrino.wuapnjieshuhui.domain.network

import android.support.design.widget.Snackbar
import android.view.View
import com.squareup.okhttp.Request


fun getHtml(url: String): String {
    val client = OkClient.instance()
    val request = Request.Builder().url(url).build()
    val response = client.newCall(request).execute()
    return response.body().string()
}

fun View.snackbar(messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,messageRes,duration).show()
}

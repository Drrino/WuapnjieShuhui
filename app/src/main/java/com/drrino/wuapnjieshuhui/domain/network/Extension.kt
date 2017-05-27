package com.drrino.wuapnjieshuhui.domain.network

import com.squareup.okhttp.Request


fun getHtml(url: String): String {
    val client = OkClient.instance()
    val request = Request.Builder().url(url).build()
    val response = client.newCall(request).execute()

    return response.body().string()
}

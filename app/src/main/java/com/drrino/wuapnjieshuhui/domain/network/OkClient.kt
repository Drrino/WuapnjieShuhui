package com.drrino.wuapnjieshuhui.domain.network

import com.squareup.okhttp.OkHttpClient

object OkClient {
    val client = OkHttpClient()

    fun instance() = client
}
package com.drrino.wuapnjieshuhui.domain.network

interface Source<out T> {
    fun obtain(url: String): T
}

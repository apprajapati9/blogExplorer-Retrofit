package com.apprajapati.blogexplorer.api

import okhttp3.Interceptor
import okhttp3.Response


class HeaderIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().
                            newBuilder().
                            addHeader("User-Agent", "Blog-Explorer").
                            build())
    }
}
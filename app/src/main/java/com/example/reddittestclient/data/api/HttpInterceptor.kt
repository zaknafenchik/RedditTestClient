package com.example.reddittestclient.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HttpInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Accept", "application/json")
        request = builder.build()
        return chain.proceed(request)
    }
}

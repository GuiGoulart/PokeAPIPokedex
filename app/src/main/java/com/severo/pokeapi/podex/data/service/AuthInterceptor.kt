package com.severo.pokeapi.podex.data.service

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("APPID", "your_key_here").build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
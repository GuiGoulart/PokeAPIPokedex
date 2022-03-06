package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.BuildConfig
import com.severo.pokeapi.podex.data.service.BeeceptorApi
import com.severo.pokeapi.podex.data.service.PokemonApi
import com.severo.pokeapi.podex.util.*
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBaseUrl(val baseUrl: String)

fun providesOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(cacheInterceptor)
    if (BuildConfig.DEBUG) okHttpClient.addInterceptor(loggingInterceptor)
    return okHttpClient.build()
}

fun providePokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)

fun provideBeeceptorApi(retrofit: Retrofit): BeeceptorApi = retrofit.create(BeeceptorApi::class.java)

fun provideRetrofit(okHttpClient: OkHttpClient, retrofitBaseUrl: RetrofitBaseUrl): Retrofit =  Retrofit.Builder()
    .baseUrl(retrofitBaseUrl.baseUrl)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val cacheInterceptor = Interceptor { chain ->
    val response: Response = chain.proceed(chain.request())
    val cacheControl = CacheControl.Builder()
        .maxAge(MAX_AGE, TimeUnit.DAYS)
        .build()
    response.newBuilder()
        .header("Cache-Control", cacheControl.toString())
        .build()
}

private val loggingInterceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
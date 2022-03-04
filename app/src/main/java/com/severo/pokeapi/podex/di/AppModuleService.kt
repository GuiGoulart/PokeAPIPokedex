package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.BuildConfig
import com.severo.pokeapi.podex.data.repository.ApiRepository
import com.severo.pokeapi.podex.data.service.AuthInterceptor
import com.severo.pokeapi.podex.data.service.PokemonApi
import com.severo.pokeapi.podex.util.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

private val loadFeatureService by lazy { loadKoinModules(appModuleService) }
internal fun injectFeatureService() = loadFeatureService

val appModuleService = module {

    factory { AuthInterceptor() }
    factory { providesOkHttpClient() }
    factory { providePokemonApi(get()) }
    single { provideRetrofit(get()) }

}

fun providesOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(cacheInterceptor)
    if (BuildConfig.DEBUG) okHttpClient.addInterceptor(loggingInterceptor)
    return okHttpClient.build()
}

fun providePokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =  Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val cacheInterceptor = Interceptor { chain ->
    val response: Response = chain.proceed(chain.request())
    val cacheControl = CacheControl.Builder()
        .maxAge(30, TimeUnit.DAYS)
        .build()
    response.newBuilder()
        .header("Cache-Control", cacheControl.toString())
        .build()
}

private val loggingInterceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
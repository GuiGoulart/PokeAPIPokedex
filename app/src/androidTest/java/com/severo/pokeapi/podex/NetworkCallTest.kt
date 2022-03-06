package com.severo.pokeapi.podex

import android.content.Context
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.severo.pokeapi.podex.data.service.PokemonApi
import com.severo.pokeapi.podex.util.CONNECT_TIMEOUT
import com.severo.pokeapi.podex.util.READ_TIMEOUT
import com.severo.pokeapi.podex.util.WRITE_TIMEOUT
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NetworkCallTest {
    private var context: Context? = null
    private var mockWebServer = MockWebServer()
    private lateinit var apiService: PokemonApi

    @Before
    fun setup() {
        mockWebServer.start()

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        context = InstrumentationRegistry.getInstrumentation().targetContext

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonApi::class.java)

        val jsonStream: InputStream = context!!.resources.assets.open("response.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(String(jsonBytes))
        mockWebServer.enqueue(response)


    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_response_name() = runBlocking {
        val data = apiService.getPokemons(1, 0)
        ViewMatchers.assertThat(data.resultResponses[0].name, CoreMatchers.equalTo("bulbasaur"))
    }

    @Test
    fun test_id_extraction() = runBlocking {
        val data = apiService.getPokemons(1, 0)
        val id = data.resultResponses[0].url?.substringAfter("pokemon")?.replace("/", "")?.toInt()
        ViewMatchers.assertThat(id, CoreMatchers.equalTo(1))
    }
}
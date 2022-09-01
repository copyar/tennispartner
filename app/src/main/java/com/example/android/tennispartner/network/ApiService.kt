package com.example.android.tennispartner.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL = "https://tennis-live-data.p.rapidapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply{ level = HttpLoggingInterceptor.Level.BASIC }

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()
//Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ApiService{

    @Headers(
        "X-RapidAPI-Key: a5ef88a05emsh47a5156a83e2455p11ff42jsneffc3af46e47",
        "X-RapidAPI-Host: tennis-live-data.p.rapidapi.com",
        "Content-Type: application/json")
    @GET("players/{tour}")
    fun getPlayersByTour(@Path("tour") tour: String): Deferred<PlayerResponse>

    @Headers(
        "X-RapidAPI-Key: a5ef88a05emsh47a5156a83e2455p11ff42jsneffc3af46e47",
        "X-RapidAPI-Host: tennis-live-data.p.rapidapi.com",
        "Content-Type: application/json")
    @GET("player/{id}")
    fun getPlayerById(@Path("id") id: Long): Deferred<PlayerDetailResponse>
}

object PlayerApi {
    // lazy properties = thread safe --> can only be initialized once at a time
    // adds extra safety to our 1 instance we need.
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun ApiService.mockPutPlayer(player: ApiPlayer):ApiPlayer{
        return player
    }
}

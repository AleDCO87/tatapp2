package com.example.tatapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {
    // Debe terminar en "/"
    private const val BASE_URL = "http://3.139.90.244:4000/api/"

    fun okHttp(tokenProvider: () -> String? = { null }): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request()
                val b = req.newBuilder()
                    .header("Accept", "application/json")
                    .header("Accept-Charset", "utf-8")

                tokenProvider()?.let { b.header("Authorization", "Bearer $it") }
                chain.proceed(b.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    fun retrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            // 👇 primero Scalars (String), luego Gson
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

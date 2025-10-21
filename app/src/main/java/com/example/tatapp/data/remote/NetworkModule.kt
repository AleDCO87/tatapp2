package com.example.tatapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    // Base URL debe terminar en /
    private const val BASE_URL = "http://3.139.90.244:4000/"

    fun okHttp(tokenProvider: () -> String? = { null }): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val b = chain.request().newBuilder()
                tokenProvider()?.let { b.header("Authorization", "Bearer $it") }
                chain.proceed(b.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    fun retrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}

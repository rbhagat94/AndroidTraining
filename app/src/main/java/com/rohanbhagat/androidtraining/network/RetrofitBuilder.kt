package com.rohanbhagat.androidtraining.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://dummyjson.com/").addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).client(OkHttpClient.Builder().build()).build()
    }

    fun provideApiService(): ApiService {
        return getRetrofitInstance().create(ApiService::class.java)
    }
}
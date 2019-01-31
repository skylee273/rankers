package com.project.rankers.retrofit.crater

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.rankers.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RankersPostCreator {
    companion object {
        val API_BASE_URL = "http://58.229.208.198/rankers/"

        var gson: Gson = GsonBuilder()
                .setLenient()
                .create()

        private fun defaultRetrofit(): Retrofit{
            return Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createOkHttpClient())
                    .build()
        }

        fun <T> create(service: Class<T>): T{
            return defaultRetrofit().create(service)
        }

        private fun createOkHttpClient(): OkHttpClient{
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .build()
        }
    }
}
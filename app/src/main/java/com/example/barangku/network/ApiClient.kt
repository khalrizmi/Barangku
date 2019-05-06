package com.example.barangku.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Hamz on 04/05/2019.
 * ilham011001@gmail.com
 */

class ApiClient {
    private val BASE_URL = "http://192.168.43.188/laravel/barangku/public/api/"
    private val TIME_OUT = 10
    private var mRetrofit: Retrofit? = null

    fun getClient(): Retrofit? {

        if (mRetrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .build()

            mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
        }
        return mRetrofit
    }
}
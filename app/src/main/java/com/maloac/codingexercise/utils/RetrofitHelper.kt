package com.maloac.codingexercise.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val url = "https://api.github.com/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
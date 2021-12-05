package com.iqbal.submission3.githubuserapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
  private const val BASE_URL = "https://api.github.com/"
  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val apiInstance: ApiConfig = retrofit.create(ApiConfig::class.java)
}
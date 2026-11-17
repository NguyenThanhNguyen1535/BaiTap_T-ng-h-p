package com.ute.bainay.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 3. Retrofit Client Singleton
object RetrofitClient {
    private const val BASE_URL = "https://api.example.com/"

    val apiService: StudentApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentApiService::class.java)
    }
}

package com.example.retrofitandpictureloading.network

import com.example.retrofitandpictureloading.model.ApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApiService {
    @GET("?inc=gender,name,picture&results=10&noinfo")
    suspend fun getUsers(
        @Query("page") page: Int
    ): ApiResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://randomuser.me/api/"

    val instance: RandomUsersApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RandomUsersApiService::class.java)
    }
}


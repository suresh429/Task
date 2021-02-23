package com.vcspinfo.task.data.network

import com.vcspinfo.task.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("weather")
    suspend fun getUser(@Query("lat") latitude: Double,
                        @Query("lon") longitude: Double,
                        @Query("appid") appId: String): WeatherResponse


}
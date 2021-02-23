package com.vcspinfo.task.db

import androidx.room.*

@Dao
interface WeatherDao {

    @Insert
    suspend fun addWeather(weather: Weather)

    @Query("SELECT * FROM weather ORDER BY id DESC")
    suspend fun getAllWeather() : List<Weather>


    @Delete
    suspend fun deleteWeather(weather: Weather)
}
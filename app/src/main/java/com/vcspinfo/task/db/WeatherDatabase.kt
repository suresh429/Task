package com.vcspinfo.task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [Weather::class],
        version = 1
)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun getWeatherDao() : WeatherDao

    companion object {

        @Volatile private var instance : WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weatherdatabase"
        ).build()

    }
}
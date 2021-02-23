package com.vcspinfo.task.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Weather(
        val cityName: String,
        val latitude: Double,
        val longitude: Double
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
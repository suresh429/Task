package com.vcspinfo.task.data.repository


import com.vcspinfo.task.data.network.Api


class UserRepository(
    private val api: Api,
    private val latitude: Double?,
    private val longitude: Double?,
    private val apikey: String,
) : BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser(latitude!!,longitude!!,apikey)
    }

}
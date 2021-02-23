package com.vcspinfo.task.ui.t.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vcspinfo.task.data.network.Resource
import com.vcspinfo.task.data.repository.UserRepository
import com.vcspinfo.task.data.response.WeatherResponse
import kotlinx.coroutines.launch
import net.simplifiedcoding.ui.base.BaseViewModel

class WheatherViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val user: LiveData<Resource<WeatherResponse>>
        get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

}
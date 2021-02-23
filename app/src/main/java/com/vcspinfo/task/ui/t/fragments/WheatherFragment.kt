package com.vcspinfo.task.ui.t.fragments


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.vcspinfo.task.R
import com.vcspinfo.task.data.network.Resource
import com.vcspinfo.task.data.network.Api
import com.vcspinfo.task.data.repository.UserRepository
import com.vcspinfo.task.databinding.FragmentWheatherBinding
import com.vcspinfo.task.db.Weather
import com.vcspinfo.task.ui.t.base.BaseFragment
import com.vcspinfo.task.ui.t.base.WheatherViewModel


class WheatherFragment :
    BaseFragment<WheatherViewModel, FragmentWheatherBinding, UserRepository>() {
    private var weather: Weather? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            weather = WheatherFragmentArgs.fromBundle(it).weather

            latitude = weather?.latitude
            longitude = weather?.longitude
        }

        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Log.d(ContentValues.TAG, "onViewCreated: " + it.value)



                    if(it.value.weather[0].main == "Clear" ){
                        Glide.with(this)
                            .load(R.drawable.clouds)
                            .into(binding.image)
                    }else
                        if(it.value.weather[0].main == "Haze"){
                            Glide.with(this)
                                .load(R.drawable.haze)
                                .into(binding.image)
                        }else
                            if(it.value.weather[0].main == "Clouds" || it.value.weather[0].main =="Rain"){
                                Glide.with(this)
                                    .load(R.drawable.rain)
                                    .into(binding.image)
                            }

                    binding.description.text=it.value.weather[0].description
                    binding.name.text=it.value.name
                    binding.degree.text=it.value.wind.deg.toString()+"°C"
                    binding.speed.text=it.value.wind.speed.toString()
                    binding.temp.text=it.value.main.temp.toString()
                    binding.humidity.text=it.value.main.humidity.toString()

                }
                is Resource.Loading -> {

                    Log.d(ContentValues.TAG, "onViewCreatedFalse: " + it)
                }
                is Resource.Failure -> {

                    Log.d(ContentValues.TAG, "handleApiError: " + it.errorBody)
                }

            }
        })


    }


    override fun getViewModel() = WheatherViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWheatherBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {

        arguments?.let {
            weather = WheatherFragmentArgs.fromBundle(it).weather

            latitude = weather?.latitude
            longitude = weather?.longitude
        }

        val api = remoteDataSource.buildApi(Api::class.java)
        return UserRepository(api, latitude, longitude, "fae7190d7e6433ec3a45285ffcf55c86")
    }

    companion object {
        private const val TAG = "WheatherFragment"
    }


}
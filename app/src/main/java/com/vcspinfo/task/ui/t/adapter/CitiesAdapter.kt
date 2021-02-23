package com.vcspinfo.task.ui.t.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vcspinfo.task.databinding.CitiesLayoutBinding
import com.vcspinfo.task.db.Weather
import com.vcspinfo.task.ui.t.fragments.HomeDbFragmentDirections


class CitiesAdapter(private val weathers: List<Weather>, val btnlistener: BtnClickListener) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>(){
    companion object {
        var mClickListener: BtnClickListener? = null
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CitiesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = weathers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather: Weather = weathers[position]
        holder.bind(weather,position)
        mClickListener = btnlistener

    }

    class ViewHolder(private val itemBinding: CitiesLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(weather: Weather,position: Int) {

            itemBinding.textView.text = weather.cityName
            itemBinding.textView2.text = "latitude : "+weather.latitude.toString() +"\n"+"longitude : "+weather.longitude.toString()

            itemBinding.root.setOnClickListener {

                val action = HomeDbFragmentDirections.actionHomeFragmentToWheatherFragment()
                action.weather= weather
                Navigation.findNavController(it).navigate(action)
            }

            itemBinding.imgDelete.setOnClickListener {
                if (mClickListener != null)
                    mClickListener?.onBtnClick(position)
            }
        }
    }

    open interface BtnClickListener {
        fun onBtnClick(position: Int)
    }
}


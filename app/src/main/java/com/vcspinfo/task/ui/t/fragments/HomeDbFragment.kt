package com.vcspinfo.task.ui.t.fragments

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcspinfo.task.R
import com.vcspinfo.task.databinding.FragmentHomeBinding
import com.vcspinfo.task.db.Weather
import com.vcspinfo.task.db.WeatherDatabase
import com.vcspinfo.task.ui.t.adapter.CitiesAdapter
import com.vcspinfo.task.ui.t.base.BaseDbFragment
import kotlinx.coroutines.launch

class HomeDbFragment : BaseDbFragment() {
   lateinit var homeBinding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        return homeBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        launch {
            context?.let{
                homeBinding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                val weather = WeatherDatabase(it).getWeatherDao().getAllWeather()
                if(weather.size > 0) {
                    homeBinding.recyclerView.adapter =
                        CitiesAdapter(weather, object : CitiesAdapter.BtnClickListener {
                            override fun onBtnClick(position: Int) {
                                delete(weather.get(position))
                            }

                        })
                    homeBinding.txtError.visibility = View.GONE
                }else{
                    homeBinding.txtError.visibility = View.VISIBLE
                }
            }
        }


        homeBinding.buttonAdd.setOnClickListener { view ->
        view.findNavController().navigate(R.id.addLocation)


        }
    }


    private fun delete(weather: Weather) {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->
                launch {
                    WeatherDatabase(context).getWeatherDao().deleteWeather(weather)
                    val action = HomeDbFragmentDirections.actionHomeFragmentSelf()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.help -> {
                val action = HomeDbFragmentDirections.actionHomeFragmentToWebFragment()
                Navigation.findNavController(requireView()).navigate(action)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
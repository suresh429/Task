package com.vcspinfo.task.ui.t.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.vcspinfo.task.R
import com.vcspinfo.task.databinding.FragmentAddLocationBinding
import com.vcspinfo.task.db.Weather
import com.vcspinfo.task.db.WeatherDatabase
import com.vcspinfo.task.toast
import com.vcspinfo.task.ui.t.base.BaseDbFragment
import kotlinx.coroutines.launch
import java.util.*

class AddLocationDbFragment : BaseDbFragment(), OnMapReadyCallback {

    //  private var note: Note? = null
    lateinit var binding: FragmentAddLocationBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLocationBinding.inflate(inflater, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()

        return binding.root
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    requireContext(),
                    currentLocation.latitude.toString() + "" + currentLocation.longitude,
                    Toast.LENGTH_SHORT
                ).show()
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f))
        // var currentMarker = googleMap?.addMarker(markerOptions)

        googleMap?.setOnCameraIdleListener {
            googleMap.clear()
            //currentMarker?.remove()
            val latLng2 = googleMap.cameraPosition.target
            googleMap.addMarker(
                MarkerOptions().position(latLng2).title("new one").icon(
                    bitmapDescriptorFromVector(
                        requireContext(),
                        R.drawable.ic_baseline_location_on_24
                    )
                )
            )

            Log.d(TAG, "onMapReady: " + latLng2)

            if (latLng2 != null) {
                val lat = latLng2.latitude
                val lon = latLng2.longitude

                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses: List<Address>

                addresses = geocoder.getFromLocation(lat, lon, 1)

                val address = addresses[0].getAddressLine(0)
                val address2 = addresses[0].getAddressLine(1)
                var city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName

                Log.d(
                    TAG,
                    "onMapReady: " + "  " + knownName + "  city: " + city + "  " + state + "  "
                )

                binding.fab.setOnClickListener { view ->

                    if (city.isNullOrEmpty()) {

                        city = "Unnamed Road"

                    }

                    launch {

                        context?.let {

                            val mNote = Weather(city, lat, lon)

                            WeatherDatabase(it).getWeatherDao().addWeather(mNote)
                            it.toast("Location Saved")

                            val action =
                                AddLocationDbFragmentDirections.actionAddLocationToHomeFragment()
                            Navigation.findNavController(view).navigate(action)
                        }
                    }
                }

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }


    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}
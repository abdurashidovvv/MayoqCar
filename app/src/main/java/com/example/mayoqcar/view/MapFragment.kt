package com.example.mayoqcar.view

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentMapBinding
import com.example.mayoqcar.utils.LocationService
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : Fragment(), OnMapReadyCallback, LocationService.LocationListener {

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var locationService: LocationService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        locationService= LocationService(binding.root.context)

        binding.topbarTitle.setOnClickListener {
            locationService.startLocationUpdates(this)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapFragment = SupportMapFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, mapFragment).commit()

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap=p0
    }

    override fun onLocationReceived(location: Location) {
        Log.d("RealTimeLocation", "onLocationReceived: ${location.latitude}")
    }
}
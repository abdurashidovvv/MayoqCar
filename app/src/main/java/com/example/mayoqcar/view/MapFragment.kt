package com.example.mayoqcar.view

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentMapBinding
import com.example.mayoqcar.repository.MapRepository
import com.example.mayoqcar.repository.RegisterRepository
import com.example.mayoqcar.utils.LocationService
import com.example.mayoqcar.utils.MySharedPreference
import com.example.mayoqcar.viewmodels.MapViewModel
import com.example.mayoqcar.viewmodels.MapViewModelFactory
import com.example.mayoqcar.viewmodels.RegisterViewModel
import com.example.mayoqcar.viewmodels.RegisterViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase

class MapFragment : Fragment(), OnMapReadyCallback, LocationService.LocationListener {

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var locationService: LocationService
    private lateinit var registerRepository: RegisterRepository
    private lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var mapRepository: MapRepository
    private lateinit var mapViewModelFactory: MapViewModelFactory
    private lateinit var mapViewModel: MapViewModel
    private lateinit var currentLocation: LatLng
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        registerRepository = RegisterRepository()
        registerViewModelFactory = RegisterViewModelFactory(registerRepository)
        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]


        mapRepository = MapRepository()
        mapViewModelFactory = MapViewModelFactory(mapRepository)
        mapViewModel =
            ViewModelProvider(this, mapViewModelFactory)[MapViewModel::class.java]


        locationService = LocationService(binding.root.context)


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
        googleMap = p0

        locationService.startLocationUpdates(this)
    }

    override fun onLocationReceived(location: Location) {

        val markerList=ArrayList<Marker>()
        for (marker in markerList) {
            marker.remove()
        }
        Log.d("RealTimeLocation", "onLocationReceived: $markerList")
        val updates = mutableMapOf<String, Any>()
        updates["lat"] = "${location.latitude}"
        updates["long"] = "${location.longitude}"
        currentLocation = LatLng(location.latitude, location.longitude)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))

        // Add marker to the map
        val markerPosition = LatLng(currentLocation.latitude, currentLocation.longitude)
        val marker=googleMap.addMarker(
            MarkerOptions().position(markerPosition).title("Sizning manzilingiz")
        )
        markerList.add(marker!!)


        val databaseReference = FirebaseDatabase.getInstance().reference.child("workers")
        databaseReference.child(MySharedPreference.getWorker().id.toString())
            .updateChildren(updates)
    }
}
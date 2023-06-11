package com.example.fastdeliveries.view.collaborator.view.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastdeliveries.R
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.viewModel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var viewModel: MapsViewModel
    private var deliveryPoints: List<Delivery> = listOf()
    private lateinit var gM: GoogleMap;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        viewModel.getAllDeliveries()
        observe()

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onStart() {
        viewModel.getAllDeliveries()
        markerPoints()
        super.onStart()
    }

    private val callback = OnMapReadyCallback { googleMap ->
        gM = googleMap

        val brazil = LatLng(-8.75790319408473, -56.105726273406376);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(brazil))

        markerPoints()

        try {
            gM.setMapStyle(context?.let { MapStyleOptions.loadRawResourceStyle(it, R.raw.mapstyle) })
        } catch (_: Resources.NotFoundException) { }
    }

    private fun markerPoints() {
        for (delivery in deliveryPoints) {
            if (delivery.status == DeliveryStatus.PENDENTE) {
                val point = LatLng(delivery.order.latitude.toDouble(), delivery.order.longitude.toDouble())
                gM.addMarker(MarkerOptions()
                    .position(point)
                    .snippet("${delivery.order.recipient_name} \n ${delivery.order.expected_delivery_date}")
                    .title("${delivery.order.name_product} - ${delivery.code}"))
            }
        }
    }

    fun observe() {
        viewModel.allDeliveries.observe(viewLifecycleOwner) { deliveryPoints = it }
    }

}
package com.example.fastdeliveries.view.collaborator.view.fragments

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastdeliveries.R
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.view.DeliveryDetailsActivity
import com.example.fastdeliveries.view.collaborator.viewModel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnInfoWindowClickListener {
    private lateinit var viewModel: MapsViewModel
    private var gM: GoogleMap? = null
    private var deliveries: List<Delivery> = listOf()
    private var markers: MutableList<Marker> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        viewModel.getAllDeliveries()
        observe()

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onResume() {
        viewModel.getAllDeliveries()
        super.onResume()
    }

    override fun onInfoWindowClick(marker: Marker) {
        val delivery = deliveries.find {
            marker.position == LatLng(it.latitude.toDouble(), it.longitude.toDouble())
        }

        val intent = Intent(context, DeliveryDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putString(DeliveryConstants.PARAMS_DELIVERY_DETAILS.DELIVERY_ID, delivery!!.id.toString())
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        gM = googleMap
        gM!!.setOnInfoWindowClickListener(this)

        val brazil = LatLng(-8.75790319408473, -56.105726273406376)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(brazil))

        markerPoints()

        try {
            gM!!.setMapStyle(context?.let { MapStyleOptions.loadRawResourceStyle(it, R.raw.mapstyle) })
        } catch (_: Resources.NotFoundException) { }
    }

    private fun markerPoints() {
        gM?.clear();
        markers.clear();

        if (deliveries.isNotEmpty()) {
            for (delivery in deliveries) {
                val point = LatLng(delivery.latitude.toDouble(), delivery.longitude.toDouble())
                if (delivery.status === DeliveryStatus.PENDENTE) {
                    val marker = gM?.addMarker(
                        MarkerOptions()
                            .position(point)
                            .snippet("${delivery.recipient_name} \n ${delivery.expected_delivery_date}")
                            .title("${delivery.name_product} - ${delivery.code}"))

                    if (marker != null) {
                        markers.add(marker)
                    }
                } else {
                    val marker = markers.find { it.position == point }
                    val index = markers.indexOf(marker)
                    if (index >= 0) markers[index].remove()
                }
            }
        }
    }

    private fun observe() {
        viewModel.allDeliveries.observe(viewLifecycleOwner) {
            deliveries = it
            if (gM != null) markerPoints()
        }
    }
}
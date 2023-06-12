package com.example.fastdeliveries.view.collaborator.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsViewModel(application: Application): AndroidViewModel(application) {
    private val deliveriesRepository = DeliveriesRepository.getInstance(application.applicationContext)
    private var markers: MutableList<Marker> = mutableListOf()

    private val _allDeliveries = MutableLiveData<List<Delivery>>()
    var allDeliveries : LiveData<List<Delivery>> = _allDeliveries

    fun getAllDeliveries() {
        _allDeliveries.value = deliveriesRepository.getAllDeliveries()
    }

    fun markerPoints(gM: GoogleMap) {
        gM.clear();
        markers.clear();

        for (delivery in _allDeliveries.value!!) {
            val point = LatLng(delivery.order.latitude.toDouble(), delivery.order.longitude.toDouble())
            if (delivery.status === DeliveryStatus.PENDENTE) {
                val marker = gM.addMarker(
                    MarkerOptions()
                    .position(point)
                    .snippet("${delivery.order.recipient_name} \n ${delivery.order.expected_delivery_date}")
                    .title("${delivery.order.name_product} - ${delivery.code}"))

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
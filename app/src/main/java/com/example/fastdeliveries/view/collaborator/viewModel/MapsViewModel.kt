package com.example.fastdeliveries.view.collaborator.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository

class MapsViewModel(application: Application): AndroidViewModel(application) {
    private val deliveriesRepository = DeliveriesRepository.getInstance(application.applicationContext)

    private val _allDeliveries = MutableLiveData<List<Delivery>>()
    var allDeliveries : LiveData<List<Delivery>> = _allDeliveries

    fun getAllDeliveries() {
        _allDeliveries.value = deliveriesRepository.getAllDeliveries()
    }
}
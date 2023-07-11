package com.example.fastdeliveries.view.collaborator.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener
import kotlinx.coroutines.launch

class MapsViewModel(application: Application): AndroidViewModel(application) {
    private val deliveriesRepository = DeliveriesRepository.getInstance(application.applicationContext)

    private val _allDeliveries = MutableLiveData<List<Delivery>>()
    var allDeliveries : LiveData<List<Delivery>> = _allDeliveries

    fun getAllDeliveries() {
        viewModelScope.launch {
            deliveriesRepository.getAllDeliveries(false, object : IAPIListener<List<Delivery>> {
                override fun onResponse(result: List<Delivery>) {
                    _allDeliveries.value = result
                }

                override fun onFailure(result: String) {
                    _allDeliveries.value = listOf()
                }
            })
        }
    }

}
package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener

class DeliveriesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance(application.applicationContext);

    private val _listAllDeliveries = MutableLiveData<List<Delivery>>();
    val deliveries: LiveData<List<Delivery>> = _listAllDeliveries;

    private val _createDelivery = MutableLiveData<ValidationResponse>();
    val createDelivery: LiveData<ValidationResponse> = _createDelivery;

    fun getAllDeliveries() {
        _listAllDeliveries.value = repository.getAllDeliveries();
    }

    fun createNewDelivery(orderJSON: String) {
        repository.createNewDelivery(orderJSON, object : IAPIListener<Boolean> {
            override fun onResponse(result: Boolean) {
                _createDelivery.value = ValidationResponse()
            }

            override fun onFailure(result: String) {
                _createDelivery.value = ValidationResponse(result)
            }
        } )
    }
}
package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener

class DeliveryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance(application.applicationContext);

    private var listAllLastsUpdates = MutableLiveData<List<LastUpdateDelivery>>();
    val lastsUpdates: LiveData<List<LastUpdateDelivery>> = listAllLastsUpdates;

    private val _deliveryData = MutableLiveData<Delivery>();
    val deliveryData: LiveData<Delivery> = _deliveryData

    private val _deleteDelivery = MutableLiveData<ValidationResponse>()
    val deleteDelivery: LiveData<ValidationResponse> = _deleteDelivery

    private val _updateStatus = MutableLiveData<ValidationResponse>()
    val updateStatus : LiveData<ValidationResponse> = _updateStatus

    fun getAllLastsUpdates(id: Int) {
        listAllLastsUpdates.value = repository.getAllLastsUpdatesByDeliveryId(id)
    }

    fun getDataDelivery(id: Int) {
        _deliveryData.value = repository.getDeliveryById(id)
    }

    fun deleteDelivery(id: Int) {
        repository.deleteDelivery(id, object : IAPIListener<Boolean> {
            override fun onResponse(result: Boolean) { _deleteDelivery.value = ValidationResponse() }
            override fun onFailure(result: String) { _deleteDelivery.value = ValidationResponse(result) }
        })
    }

    fun updateStatusDelivery(id: Int, password: String, lastUpdateDelivery: LastUpdateDelivery) {
        repository.updateStatusDelivery(id, password, lastUpdateDelivery, object : IAPIListener<Boolean> {
            override fun onResponse(result: Boolean) { _updateStatus.value = ValidationResponse() }
            override fun onFailure(result: String) { _updateStatus.value = ValidationResponse(result) }
        })
    }

}
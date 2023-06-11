package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener

class UpdateDeliveryStatusViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance(application.applicationContext)

    private val _updateStatus = MutableLiveData<ValidationResponse>()
    val updateStatus : LiveData<ValidationResponse> = _updateStatus

    fun updateStatusDelivery(id: Int, password: String, lastUpdateDelivery: LastUpdateDelivery) {
        repository.updateStatusDelivery(id, password, lastUpdateDelivery, object : IAPIListener<Boolean> {
            override fun onResponse(result: Boolean) { _updateStatus.value = ValidationResponse() }
            override fun onFailure(result: String) { _updateStatus.value = ValidationResponse(result) }
        })
    }
}
package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.enums.LoadingTypes
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.example.fastdeliveries.view.collaborator.viewModel.services.LoadingState
import kotlinx.coroutines.launch

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

    private val _loading = MutableLiveData<LoadingState>();
    val loading: LiveData<LoadingState> = _loading;

    fun getAllLastsUpdates(id: String) {
        viewModelScope.launch {
            _loading.value = LoadingState(LoadingTypes.WITH_BACKGROUND, true)
            repository.getAllLastsUpdatesByDeliveryId(id, object : IAPIListener<List<LastUpdateDelivery>> {
                override fun onResponse(result: List<LastUpdateDelivery>) {
                    listAllLastsUpdates.value = result
                }

                override fun onFailure(result: String) {
                    _loading.value = LoadingState(LoadingTypes.WITH_BACKGROUND, false)
                }
            })
        }
    }

    fun getDataDelivery(id: String) {
        viewModelScope.launch {
            _deliveryData.value = repository.getDeliveryById(id)
        }
    }

    fun deleteDelivery(id: String) {
        viewModelScope.launch {
            repository.deleteDelivery(id, object : IAPIListener<Boolean> {
                override fun onResponse(result: Boolean) { _deleteDelivery.value = ValidationResponse() }
                override fun onFailure(result: String) { _deleteDelivery.value = ValidationResponse(result) }
            })
        }
    }

    fun updateStatusDelivery(id: String, password: String, deliveryUpdate: DeliveryUpdate) {
        viewModelScope.launch {
            repository.updateStatusDelivery(id, password, deliveryUpdate, object : IAPIListener<Boolean> {
                override fun onResponse(result: Boolean) { _updateStatus.value = ValidationResponse() }
                override fun onFailure(result: String) { _updateStatus.value = ValidationResponse(result) }
            })
        }
    }

}
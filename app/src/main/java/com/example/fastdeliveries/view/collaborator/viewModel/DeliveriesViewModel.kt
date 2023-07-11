package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fastdeliveries.view.collaborator.enums.LoadingTypes
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener
import com.example.fastdeliveries.view.collaborator.viewModel.services.LoadingState
import kotlinx.coroutines.launch

class DeliveriesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance(application.applicationContext);

    private val _listAllDeliveries = MutableLiveData<List<Delivery>>();
    val deliveries: LiveData<List<Delivery>> = _listAllDeliveries;

    private val _createDelivery = MutableLiveData<ValidationResponse>();
    val createDelivery: LiveData<ValidationResponse> = _createDelivery;

    private val _loading = MutableLiveData<LoadingState>();
    val loading: LiveData<LoadingState> = _loading;

    fun getAllDeliveries() {
        viewModelScope.launch {
            _loading.value = LoadingState(LoadingTypes.WITH_BACKGROUND)
            repository.getAllDeliveries(true, object : IAPIListener<List<Delivery>> {
                override fun onResponse(result: List<Delivery>) {
                    _listAllDeliveries.value = result
                    _loading.value = LoadingState(LoadingTypes.SIMPLE, false)
                }

                override fun onFailure(result: String) {
                    _loading.value = LoadingState(LoadingTypes.SIMPLE, false)
                }
            });
        }
    }

    fun createNewDelivery(orderJSON: String) {
        viewModelScope.launch {
            _loading.value = LoadingState(LoadingTypes.WITH_BACKGROUND)
            repository.createNewDelivery(orderJSON, object : IAPIListener<Boolean> {
                override fun onResponse(result: Boolean) {
                    _createDelivery.value = ValidationResponse()
                }

                override fun onFailure(result: String) {
                    _createDelivery.value = ValidationResponse(result)
                    _loading.value = LoadingState(LoadingTypes.WITH_BACKGROUND, false)
                }
            } )
        }
    }
}
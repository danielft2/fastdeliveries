package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences

class DeliveryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance(application.applicationContext);

    private var listAllLastsUpdates = MutableLiveData<List<LastUpdateDelivery>>();
    val lastsUpdates: LiveData<List<LastUpdateDelivery>> = listAllLastsUpdates;

    private val _deliveryData = MutableLiveData<Delivery>();
    val deliveryData: LiveData<Delivery> = _deliveryData

    fun getAllLastsUpdates(id: Int) {
        listAllLastsUpdates.value = repository.getAllLastsUpdatesByDeliveryId(id)
    }

    fun getDataDelivery(id: Int) {
        _deliveryData.value = repository.getDeliveryById(id)
    }
}
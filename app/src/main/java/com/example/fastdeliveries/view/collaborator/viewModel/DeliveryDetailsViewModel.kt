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
    private val repository = DeliveriesRepository.getInstance();
    private val authStoragePreferences = AuthStoragePreferences.getInstance(application.applicationContext);
    private val collaborator = authStoragePreferences.retrievePreference();

    private var listAllLastsUpdates = MutableLiveData<List<LastUpdateDelivery>>();
    val lastsUpdates: LiveData<List<LastUpdateDelivery>> = listAllLastsUpdates;

    fun getAllLastsUpdates(id: Int) {
        if (collaborator != null) {
           listAllLastsUpdates.value = repository.getAllLastsUpdatesByDeliveryId(id, collaborator.id)
        }
    }

    fun getDataDelivery(id: Int): Delivery? {
        if (collaborator != null) {
            return repository.getDeliveryById(id, collaborator.id)
        }
        return null
    }
}
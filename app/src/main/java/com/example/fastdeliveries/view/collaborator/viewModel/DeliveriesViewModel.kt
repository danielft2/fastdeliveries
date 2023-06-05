package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.repository.DeliveriesRepository
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences

class DeliveriesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DeliveriesRepository.getInstance();
    private val authStoragePreferences = AuthStoragePreferences.getInstance(application.applicationContext);

    private val listAllDeliveries = MutableLiveData<List<Delivery>>();
    val deliveries: LiveData<List<Delivery>> = listAllDeliveries;

    fun getAllDeliveries() {
        val collaborator = authStoragePreferences.retrievePreference();
        if (collaborator != null) {
            listAllDeliveries.value = repository.getAllDeliveries(collaborator.id);
        }
    }
}
package com.example.fastdeliveries.view.collaborator.repository

import com.example.fastdeliveries.view.collaborator.database.DeliveriesDatabase
import com.example.fastdeliveries.view.collaborator.models.Delivery

class DeliveriesRepository {
    private var deliveriesDatabase: DeliveriesDatabase = DeliveriesDatabase();

    companion object {
        private lateinit var deliveriesRepository: DeliveriesRepository;

        fun getInstance(): DeliveriesRepository {
            if (!::deliveriesRepository.isInitialized) {
                deliveriesRepository = DeliveriesRepository()
            }

            return deliveriesRepository;
        }

    }

    fun getAllDeliveries(id: Number): List<Delivery> {
        return deliveriesDatabase.getAllDeliveriesByCollaborator(id);
    }
}
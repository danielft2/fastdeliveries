package com.example.fastdeliveries.view.collaborator.repository

import com.example.fastdeliveries.view.collaborator.database.DeliveriesDatabase
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery

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

    fun getAllDeliveries(id: Int): List<Delivery> {
        return deliveriesDatabase.getAllByCollaborator(id);
    }

    fun getDeliveryById(id: Int, collaborator_id: Int): Delivery? {
        return deliveriesDatabase.getById(id, collaborator_id)
    }

    fun getAllLastsUpdatesByDeliveryId(id: Int, collaborator_id: Int): List<LastUpdateDelivery> {
        return deliveriesDatabase.getAllLastUpdateByDeliveryId(id, collaborator_id)
    }
}
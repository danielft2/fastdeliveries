package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse

interface IDeliveryDatabase {
    suspend fun getAllByCollaborator(id: String, isUpdates: Boolean): List<Delivery>;
    suspend fun getDeliveryById(id: String): Delivery;
    suspend fun getDeliveryByOrderId(id: String): Delivery?
    suspend fun getAllLastUpdateByDeliveryId(id: String): List<LastUpdateDelivery>
    suspend fun createNewDelivery(order: Order);
    suspend fun deleteDelivery(id: String)
    suspend fun updateStatusDelivery(id: String, deliveryUpdate: DeliveryUpdate)
}
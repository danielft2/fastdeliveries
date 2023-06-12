package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse

interface IDeliveryDatabase {
    fun getAllByCollaborator(id: Int): List<Delivery>;
    fun getDeliveryById(id: Int, id_collaborator: Int): Delivery?;
    fun getDeliveryByOrderId(id: Int, id_collaborator: Int): Delivery?
    fun getAllLastUpdateByDeliveryId(id: Int, id_collaborator: Int): List<LastUpdateDelivery>
    fun createNewDelivery(order: Order, id_collaborator: Int): ValidationResponse;
    fun deleteDelivery(id: Int, id_collaborator: Int): ValidationResponse
    fun updateStatusDelivery(
        id: Int,
        id_collaborator: Int,
        lastUpdateDelivery: LastUpdateDelivery
    ): ValidationResponse
}
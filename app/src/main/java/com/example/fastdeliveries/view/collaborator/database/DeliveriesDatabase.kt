package com.example.fastdeliveries.view.collaborator.database

import android.util.Log
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.interfaces.IDeliveryDatabase
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse

class DeliveriesDatabase: IDeliveryDatabase {
    private var deliveries: MutableList<Delivery> = mutableListOf();
    private var countId: Int = 0

    override fun getAllByCollaborator(id: Int): List<Delivery> {
       return deliveries.filter { it.collaborator_id == id };
    }

    override fun getDeliveryById(id: Int, id_collaborator: Int): Delivery? {
        return deliveries.find { it.id == id && it.collaborator_id == id_collaborator }
    }

    override fun getDeliveryByOrderId(id: Int, id_collaborator: Int): Delivery? {
        return deliveries.find { it.order.id == id && it.collaborator_id == id_collaborator }
    }

    override fun getAllLastUpdateByDeliveryId(id: Int, id_collaborator: Int): List<LastUpdateDelivery> {
        val delivery = deliveries.find { it.id == id && it.collaborator_id == id_collaborator }
        if (delivery != null) return delivery.lastsUpdates
        return listOf()
    }

    override fun createNewDelivery(order: Order, id_collaborator: Int): ValidationResponse {
        Log.e("ORDER", order.toString())
        val deliveryAlreadyExists = getDeliveryByOrderId(order.id, id_collaborator)

        if (deliveryAlreadyExists != null) {
            return ValidationResponse("A Entrega já foi cadastrada!")
        }

        deliveries.add(Delivery(countId++, "BR3838HDHE2", id_collaborator, order, DeliveryStatus.PENDENTE))
        return ValidationResponse()
    }
}
package com.example.fastdeliveries.view.collaborator.database

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.interfaces.IDeliveryDatabase
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery

class DeliveriesDatabase: IDeliveryDatabase {
    private var deliveries: MutableList<Delivery> = mutableListOf();

    override fun getAllByCollaborator(id: Int): List<Delivery> {
       return deliveries.filter { it -> it.collaborator_id == id.toInt() };
    }

    override fun getById(id: Int, id_collaborator: Int): Delivery? {
        return deliveries.find { it -> it.id == id && it.collaborator_id == id_collaborator }
    }

    override fun getAllLastUpdateByDeliveryId(id: Int, id_collaborator: Int): List<LastUpdateDelivery> {
        val delivery = deliveries.find { it -> it.id == id && it.collaborator_id == id_collaborator }
        if (delivery != null) return delivery.lastsUpdates
        return listOf()
    }

    init {
        deliveries = mutableListOf(
            Delivery(
                1, "BR3838HDHE2", 1, "Daniel Almeida de Freitas",
                "Ibicuitinga - CE", -4.908857534806692, -38.6128145061469,
                "Mouse da Razer", DeliveryStatus.ENTREGUE
            ),
            Delivery(
                2, "BR4838JAHE1", 2,  "Francisco Lucas",
                "Morada Nova - CE",  -5.1050436074364605, -38.36819917759575,
                "TV SEM 63 Polegdas", DeliveryStatus.PENDENTE
            ),
            Delivery(
                3, "BR2T3A1AH00", 1, "Larissa de Sousa",
                "Quixadá - CE", -4.971696375999397, -39.01444154580492,
                "Mesa de Escritório", DeliveryStatus.PENDENTE
            ),
            Delivery(
                4, "BR00A38JAP22", 1, "Francisco Gleidson de Oliveira",
                "Quixadá - CE", -4.971696375999397, -39.01444154580492,
                "Notbook Lenovo S145", DeliveryStatus.PENDENTE
            ),
            Delivery(
                5, "BR18464AH71", 2, "Francisco Gleidson de Oliveira",
                "Quixadá - CE", -4.971696375999397, -39.01444154580492,
                "Geladeira", DeliveryStatus.PENDENTE
            )
        );
    }
}
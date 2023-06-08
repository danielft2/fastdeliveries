package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery

interface IDeliveryDatabase {
    fun getAllByCollaborator(id: Int): List<Delivery>;
    fun getById(id: Int, id_collaborator: Int): Delivery?;
    fun getAllLastUpdateByDeliveryId(id: Int, id_collaborator: Int): List<LastUpdateDelivery>
}
package com.example.fastdeliveries.view.collaborator.models

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import java.util.Date

class Delivery(
    val id: Int,
    val code: String,
    val collaborator_id: Int,
    val order: Order,
    val status: DeliveryStatus) {

    val lastsUpdates: MutableList<LastUpdateDelivery> =
        mutableListOf(LastUpdateDelivery(DeliveryUpdate.AGUARDANDO_MOVIMENTACAO, Date().toString()));

    fun getLastUpdate(): String {
        return lastsUpdates[0].type.toString()
    }

    override fun toString(): String {
        return "${id} - ${code} - ${collaborator_id} - ${order.recipient_name} - ${order.city} - ${order.adress} - " +
                "${order.latitude} -" + "${order.longitude} - ${order.name_product} " +
                "${order.expected_delivery_date} - ${status}"
    }

}
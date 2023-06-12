package com.example.fastdeliveries.view.collaborator.models

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Delivery(
    var id: Int,
    var code: String,
    var collaborator_id: Int,
    var order: Order,
    var status: DeliveryStatus) {

    val lastsUpdates: MutableList<LastUpdateDelivery> =
        mutableListOf(LastUpdateDelivery(DeliveryUpdate.AGUARDANDO_MOVIMENTACAO, getDateFormatter()));

    fun getLastUpdate(): String {
        return lastsUpdates[lastsUpdates.count() - 1].type.value
    }

    private fun getDateFormatter(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss a")
        return LocalDateTime.now().format(formatter).toString()
    }


    override fun toString(): String {
        return "${id} - ${code} - ${collaborator_id} - ${order.recipient_name} - ${order.city} - ${order.adress} - " +
                "${order.latitude} -" + "${order.longitude} - ${order.name_product} " +
                "${order.expected_delivery_date} - ${status}"
    }
}
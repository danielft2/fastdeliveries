package com.example.fastdeliveries.view.collaborator.models

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Delivery(
    var id: String? = "",
    var order_id: String = "",
    var code: String = "",
    val name_product: String = "",
    val recipient_name: String = "",
    val expected_delivery_date: String = "",
    val city: String = "",
    val adress: String = "",
    val latitude: String = "",
    val longitude: String = "",
    var status: DeliveryStatus = DeliveryStatus.PENDENTE) {

    var lastsUpdates: List<LastUpdateDelivery> = mutableListOf();

    fun getLastUpdate(): String {
        return lastsUpdates[lastsUpdates.count() - 1].type.value
    }

    private fun getDateFormatter(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss a")
        return LocalDateTime.now().format(formatter).toString()
    }


    override fun toString(): String {
        return "${id} - ${order_id} - ${code} - ${recipient_name} - ${city} - ${adress} - " +
                "${latitude} -" + "${longitude} - ${name_product} " +
                "${expected_delivery_date} - ${status}"
    }
}
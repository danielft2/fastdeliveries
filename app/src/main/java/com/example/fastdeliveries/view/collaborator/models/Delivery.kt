package com.example.fastdeliveries.view.collaborator.models

import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import java.util.Date

class Delivery(
    var id: Int,
    var code: String,
    var collaborator_id: Int,
    var recipient_name: String,
    var adress: String,
    var latitude: Number,
    var logintude: Number,
    var name_product: String,
    var status: DeliveryStatus) {

    val lastsUpdates: MutableList<LastUpdateDelivery> = mutableListOf();

    fun getLastUpdate(): String {
        return lastsUpdates[0].type.toString()
    }

    init {
        lastsUpdates.add(LastUpdateDelivery(DeliveryUpdate.AGUARDANDO_MOVIMENTACAO, Date().toString()))
    }
}
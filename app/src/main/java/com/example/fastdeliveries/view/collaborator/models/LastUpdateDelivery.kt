package com.example.fastdeliveries.view.collaborator.models

import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate

class LastUpdateDelivery(var type: DeliveryUpdate, var data: String) {
    override fun toString(): String {
        return "${type.toString()} - ${data}"
    }
}
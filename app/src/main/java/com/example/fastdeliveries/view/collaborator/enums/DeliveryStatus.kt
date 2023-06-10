package com.example.fastdeliveries.view.collaborator.enums

import com.google.gson.annotations.SerializedName

enum class DeliveryStatus(val value: Int) {
    @SerializedName("0")
    PENDENTE(0),

    @SerializedName("1")
    ENTREGUE(1),
}
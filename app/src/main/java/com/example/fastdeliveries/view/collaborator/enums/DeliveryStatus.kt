package com.example.fastdeliveries.view.collaborator.enums

import com.google.gson.annotations.SerializedName

enum class DeliveryStatus(val value: String) {
    @SerializedName("0")
    PENDENTE("Pendente"),

    @SerializedName("1")
    ENTREGUE("Entregue")
}
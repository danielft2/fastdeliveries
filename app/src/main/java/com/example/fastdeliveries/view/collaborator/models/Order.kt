package com.example.fastdeliveries.view.collaborator.models

class Order(
    val id: String,
    val name_product: String,
    val recipient_name: String,
    val expected_delivery_date: String,
    val city: String,
    val adress: String,
    val latitude: String,
    val longitude: String
    ) {

    override fun toString(): String {
        return "$id - $recipient_name - $city - $adress - " +
                "$latitude -" + "$longitude} - $name_product " +
                expected_delivery_date
    }
}
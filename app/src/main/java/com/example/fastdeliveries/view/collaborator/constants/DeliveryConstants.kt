package com.example.fastdeliveries.view.collaborator.constants

class DeliveryConstants {
    object PARAMS_DELIVERY_DETAILS {
        const val DELIVERY_ID = "delivery_id"
    }

    object DELIVERY_FIREBASE {
        const val ORDER_ID = "order_id"
        const val CODE = "code"
        const val NAME_PRODUCT = "name_product"
        const val NAME_RECIPIENTE = "recipient_name"
        const val DATE = "expected_delivery_date"
        const val CITY = "city"
        const val ADRESS = "adress"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val STATUS = "status"
        const val UPDATE_TYPE = "type"
        const val UPDATE_DATE = "data"

        const val COLLABORATOR_COLL = "collaborators"
        const val DELIVERIES_COLL = "deliveries"
        const val LASTUPDATES_COLL = "lastUpdates"

        const val ERROR_DATA_DELIVERY = "Ocorreu um erro inesperado ao carregar as informações da entrega!"
        const val ERROR_LIST_DELIVERIES = "Ocorreu um erro inesperado ao carregar as entregas!"
        const val ERROR_DELETE_DELIVERY = "Ocorreu um erro inesperado ao excluir a entrega!"
        const val ERROR_DELIVERY_ALREDY_EXISTS = "A Entrega já foi cadastrada!"
        const val ERROR_UPDATE_DELIVERY = "Ocorreu um erro inesperado ao atualizar a entrega!"
    }
}
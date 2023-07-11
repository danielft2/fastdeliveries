package com.example.fastdeliveries.view.collaborator.utils

import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.google.firebase.firestore.DocumentSnapshot

class Utils {
    object UTILS_FUN {
        fun getTypeLastUpdate(type: String): DeliveryUpdate {
            return DeliveryUpdate.values().find { it.ordinal.toString() == type }
                ?: DeliveryUpdate.AGUARDANDO_MOVIMENTACAO;
        }

        fun serializeQueryDelivery(snapshot: DocumentSnapshot): Delivery {
           val CONSTANTS = DeliveryConstants.DELIVERY_FIREBASE

            return Delivery(
                snapshot.id,
                snapshot.data!![CONSTANTS.ORDER_ID] as String,
                snapshot.data!![CONSTANTS.CODE] as String,
                snapshot.data!![CONSTANTS.NAME_PRODUCT] as String,
                snapshot.data!![CONSTANTS.NAME_RECIPIENTE] as String,
                snapshot.data!![CONSTANTS.DATE] as String,
                snapshot.data!![CONSTANTS.CITY] as String,
                snapshot.data!![CONSTANTS.ADRESS] as String,
                snapshot.data!![CONSTANTS.LATITUDE] as String,
                snapshot.data!![CONSTANTS.LONGITUDE] as String,
                getStatusDelivery(snapshot.data!![CONSTANTS.STATUS] as String)
            )
        }

        private fun getStatusDelivery(status: String): DeliveryStatus {
            return if (status == "0") DeliveryStatus.PENDENTE;
            else DeliveryStatus.ENTREGUE
        }
    }
}
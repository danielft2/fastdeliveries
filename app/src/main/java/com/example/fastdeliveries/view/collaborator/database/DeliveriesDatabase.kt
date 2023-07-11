package com.example.fastdeliveries.view.collaborator.database

import android.util.Log
import com.example.fastdeliveries.view.collaborator.constants.DeliveryConstants
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.interfaces.IDeliveryDatabase
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.utils.Utils
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date

class DeliveriesDatabase(private val id_collaborator: String): IDeliveryDatabase {
    private val CONSTANTS = DeliveryConstants.DELIVERY_FIREBASE

    private val db = FirebaseFirestore.getInstance()
    private val collaboratorRef = db.collection(CONSTANTS.COLLABORATOR_COLL).document(id_collaborator)
    private  val deliveriesRef = collaboratorRef.collection(CONSTANTS.DELIVERIES_COLL)


    override suspend fun getAllByCollaborator(id: String, isUpdates: Boolean): List<Delivery> {
        return withContext(Dispatchers.IO) {
            try {
                val deliveriesData = deliveriesRef.get().await()
                return@withContext deliveriesData.map {
                    val delivery = Utils.UTILS_FUN.serializeQueryDelivery(it)
                    if (isUpdates) delivery.lastsUpdates = getAllLastUpdateByDeliveryId(it.id)
                    return@map delivery
                }
            } catch (error: Throwable) {
               throw AppError(CONSTANTS.ERROR_LIST_DELIVERIES)
            }
        }
    }

    override suspend fun getDeliveryById(id: String): Delivery {
        return withContext(Dispatchers.IO) {
            try {
                val res = deliveriesRef.document(id).get().await()
                return@withContext Utils.UTILS_FUN.serializeQueryDelivery(res)
            } catch (error: AppError) {
                throw AppError(CONSTANTS.ERROR_DATA_DELIVERY)
            }
        }
    }

    override suspend fun getDeliveryByOrderId(id:String): Delivery? {
        return withContext(Dispatchers.IO) {
            val deliveries = deliveriesRef.get().await()
            val delivery = deliveries.find { it.data[CONSTANTS.ORDER_ID] == id }
            if (delivery != null) return@withContext Utils.UTILS_FUN.serializeQueryDelivery(delivery)

            return@withContext null
        }
    }

    override suspend fun getAllLastUpdateByDeliveryId(id: String): List<LastUpdateDelivery> {
        return withContext(Dispatchers.IO) {
            val delivery = deliveriesRef.document(id)
            val lastsUpdates = delivery.collection(CONSTANTS.LASTUPDATES_COLL).get().await()
            return@withContext lastsUpdates.map { LastUpdateDelivery(
                Utils.UTILS_FUN.getTypeLastUpdate(it.data[CONSTANTS.UPDATE_TYPE] as String),
                it.data[CONSTANTS.UPDATE_DATE] as String
            )}
        }
    }

    override suspend fun createNewDelivery(order: Order) {
        return withContext(Dispatchers.IO) {
            try {
                val deliveryAlreadyExists = getDeliveryByOrderId(order.id)
                if (deliveryAlreadyExists == null) {
                    val delivery = hashMapOf(
                        CONSTANTS.ORDER_ID to order.id,
                        CONSTANTS.CODE to "BRGGSG32JS3",
                        CONSTANTS.NAME_PRODUCT to order.name_product,
                        CONSTANTS.NAME_RECIPIENTE to order.recipient_name,
                        CONSTANTS.DATE to order.expected_delivery_date,
                        CONSTANTS.CITY to order.city,
                        CONSTANTS.ADRESS to order.adress,
                        CONSTANTS.LATITUDE to order.latitude,
                        CONSTANTS.LONGITUDE to order.longitude,
                        CONSTANTS.STATUS to DeliveryStatus.PENDENTE.ordinal.toString()
                    )

                    val firstUpdate = hashMapOf(
                        CONSTANTS.UPDATE_TYPE to DeliveryUpdate.AGUARDANDO_MOVIMENTACAO.ordinal.toString(),
                        CONSTANTS.UPDATE_DATE to Date().toString()
                    )

                    val res = deliveriesRef.add(delivery).await()
                    deliveriesRef.document(res.id).collection(CONSTANTS.LASTUPDATES_COLL).add(firstUpdate).await()
                } else {
                    throw AppError(CONSTANTS.ERROR_DELIVERY_ALREDY_EXISTS)
                }
            } catch (error: Throwable) {
                Log.d("Error", error.message.toString())
                throw error
            }
        }
    }

    override suspend fun deleteDelivery(id: String) {
        return withContext(Dispatchers.IO) {
            try {
                deliveriesRef.document(id).delete().await()
            } catch (error: Throwable) {
                throw AppError(CONSTANTS.ERROR_DELETE_DELIVERY);
            }
        }
    }

    override suspend fun updateStatusDelivery(id: String, deliveryUpdate: DeliveryUpdate) {
        return withContext(Dispatchers.IO) {
            val update = hashMapOf(
                CONSTANTS.UPDATE_TYPE to deliveryUpdate.ordinal.toString(),
                CONSTANTS.UPDATE_DATE to Date().toString()
            )
            try {
                deliveriesRef.document(id).collection(CONSTANTS.LASTUPDATES_COLL).add(update).await()
                if (deliveryUpdate === DeliveryUpdate.ENTREGUE_AO_DESTINATARIO) {
                    deliveriesRef.document(id).update(CONSTANTS.STATUS, "1").await()
                }
            } catch (error: Throwable) {
                throw AppError(CONSTANTS.ERROR_UPDATE_DELIVERY)
            }
        }
    }
}
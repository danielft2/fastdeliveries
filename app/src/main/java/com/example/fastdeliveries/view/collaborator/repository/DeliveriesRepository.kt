package com.example.fastdeliveries.view.collaborator.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.fastdeliveries.R
import com.example.fastdeliveries.view.collaborator.database.DeliveriesDatabase
import com.example.fastdeliveries.view.collaborator.enums.DeliveryUpdate
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.google.gson.Gson

class DeliveriesRepository(val context: Context) {
    private var deliveriesDatabase: DeliveriesDatabase;
    private val authStoragePreferences = AuthStoragePreferences.getInstance(context);

    private val gson = Gson();
    private var collaborator_id: String = ""
    private var collaborator_cpf: String = ""


    init {
        collaborator_id = authStoragePreferences.retrievePreference()?.id ?: "-1"
        collaborator_cpf = authStoragePreferences.retrievePreference()?.cpf ?: ""
        deliveriesDatabase = DeliveriesDatabase(collaborator_id);
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var deliveriesRepository: DeliveriesRepository;

        fun getInstance(context: Context): DeliveriesRepository {
            if (!::deliveriesRepository.isInitialized) {
                deliveriesRepository = DeliveriesRepository(context)
            }

            return deliveriesRepository;
        }

    }

    suspend fun getAllDeliveries(isUpdates: Boolean = false, listener: IAPIListener<List<Delivery>>) {
        try {
            val deliveries = deliveriesDatabase.getAllByCollaborator(collaborator_id, isUpdates);
            listener.onResponse(deliveries)
        } catch (error: AppError) {
            listener.onFailure(error.message.toString())
        }
    }

    suspend fun getDeliveryById(id: String): Delivery {
        return deliveriesDatabase.getDeliveryById(id)
    }

    suspend fun getAllLastsUpdatesByDeliveryId(id: String, listener: IAPIListener<List<LastUpdateDelivery>>) {
        try {
            val updates = deliveriesDatabase.getAllLastUpdateByDeliveryId(id)
            listener.onResponse(updates)
        } catch (error: Throwable) {
            listener.onFailure(error.message.toString())
        }
    }

    suspend fun createNewDelivery(orderJSON: String, listener: IAPIListener<Boolean>) {
        try {
            val order = gson.fromJson(orderJSON, Order::class.java);
            deliveriesDatabase.createNewDelivery(order)
            listener.onResponse(true)
        } catch (e: Throwable) {
            if (e::class.java == AppError::class.java) listener.onFailure(e.message.toString())
            else listener.onFailure(context.getString(R.string.QR_CODE_INVALID))
        }
    }

    suspend fun deleteDelivery(id: String, listener: IAPIListener<Boolean>) {
        try {
            deliveriesDatabase.deleteDelivery(id)
            listener.onResponse(true)
        } catch(error: AppError) {
            listener.onFailure(error.message.toString())
        }
    }

    suspend fun updateStatusDelivery(
        id: String,
        password: String,
        deliveryUpdate: DeliveryUpdate,
        listener: IAPIListener<Boolean>
    ) {
        try {
            if(password != "") {
                if (authStoragePreferences.retrievePreference()?.password == password) {
                    deliveriesDatabase.updateStatusDelivery(id, deliveryUpdate)
                    listener.onResponse(true)
                } else {
                    listener.onFailure(context.getString(R.string.CREDENCIALS_INVALID))
                }
            } else {
                listener.onFailure("Preencha todos os campos.")
            }
        } catch (error: AppError) {
            listener.onFailure(error.message.toString())
        }
    }
}
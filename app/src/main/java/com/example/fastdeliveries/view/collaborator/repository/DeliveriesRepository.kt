package com.example.fastdeliveries.view.collaborator.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.fastdeliveries.R
import com.example.fastdeliveries.view.collaborator.database.DeliveriesDatabase
import com.example.fastdeliveries.view.collaborator.enums.DeliveryStatus
import com.example.fastdeliveries.view.collaborator.models.Delivery
import com.example.fastdeliveries.view.collaborator.models.LastUpdateDelivery
import com.example.fastdeliveries.view.collaborator.models.Order
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences
import com.example.fastdeliveries.view.collaborator.view.listeners.IAPIListener
import com.google.gson.Gson
import com.google.gson.JsonParseException

class DeliveriesRepository(val context: Context) {
    private var deliveriesDatabase: DeliveriesDatabase = DeliveriesDatabase();
    private var authRepository: AuthRepository = AuthRepository.getInstance();
    private val authStoragePreferences = AuthStoragePreferences.getInstance(context);

    private val gson = Gson();
    private var collaborator_id: Int = -1
    private var collaborator_cpf: String = ""

    init {
        collaborator_id = authStoragePreferences.retrievePreference()?.id ?: -1
        collaborator_cpf = authStoragePreferences.retrievePreference()?.cpf ?: ""
    }

    companion object {
        private lateinit var deliveriesRepository: DeliveriesRepository;

        fun getInstance(context: Context): DeliveriesRepository {
            if (!::deliveriesRepository.isInitialized) {
                deliveriesRepository = DeliveriesRepository(context)
            }

            return deliveriesRepository;
        }

    }

    fun getAllDeliveries(): List<Delivery> {
        return deliveriesDatabase.getAllByCollaborator(collaborator_id);
    }

    fun getDeliveryById(id: Int): Delivery? {
        return deliveriesDatabase.getDeliveryById(id, collaborator_id)
    }

    fun getAllLastsUpdatesByDeliveryId(id: Int): List<LastUpdateDelivery> {
        return deliveriesDatabase.getAllLastUpdateByDeliveryId(id, collaborator_id)
    }

    fun createNewDelivery(orderJSON: String, listener: IAPIListener<Boolean>) {
        try {
            val order = gson.fromJson(orderJSON, Order::class.java);
            val response = deliveriesDatabase.createNewDelivery(order, collaborator_id)

            if (response.status()) listener.onResponse(true)
            else listener.onFailure(response.message())
        } catch (e: JsonParseException) {
            listener.onFailure(context.getString(R.string.QR_CODE_INVALID))
        }
    }

    fun updateStatusDelivery(
        id: Int,
        password: String,
        lastUpdateDelivery: LastUpdateDelivery,
        listener: IAPIListener<Boolean>
    ) {
        if(password != "") {
            val credencialsValidate = authRepository.validateCredencials(collaborator_cpf, password)
            if (credencialsValidate.status()) {
                val response = deliveriesDatabase.updateStatusDelivery(id, collaborator_id, lastUpdateDelivery)
                if (response.status()) listener.onResponse(true)
                else listener.onFailure(response.message())
            } else {
                listener.onFailure(credencialsValidate.message())
            }
        } else {
            listener.onFailure("Preencha todos os campos.")
        }
    }
}
package com.example.fastdeliveries.view.collaborator.database

import android.util.Log
import com.example.fastdeliveries.view.collaborator.models.Delivery

class DeliveriesDatabase {
    private var deliveries: MutableList<Delivery> = mutableListOf();

    fun getAllDeliveriesByCollaborator(id: Number): List<Delivery> {
       return deliveries.filter { it -> it.collaborator_id == id.toInt() };
    }

    init {
        deliveries = mutableListOf(
            Delivery(1, 1, "Daniel Almeida de Freitas", "Ibicuitinga - CE", "Fone de ouvido", "01/04/2023 - 10:20h", "pendente"),
            Delivery(2, 1, "Francisco Lucas", "Morada Nova - CE", "Notbook Gamer", "02/05/2023 - 09:18h", "pendente"),
            Delivery(3, 1, "Larissa de Sousa", "Quixadá - CE", "TV SEMP 62 Polegadas", "22/05/2023 - 19:18h", "pendente"),
            Delivery(4, 1, "Maria Samara", "Quixadá - CE", "TV SEMP 62 Polegadas", "22/05/2023 - 19:18h", "pendente"),
            Delivery(5, 1, "Luana Sousa", "Quixadá - CE", "TV SEMP 62 Polegadas", "22/05/2023 - 19:18h", "pendente"),
            Delivery(6, 1, "Luana Sousa", "Quixadá - CE", "TV SEMP 62 Polegadas", "22/05/2023 - 19:18h", "pendente"),
            )
    }
}
package com.example.fastdeliveries.view.collaborator.database
import android.util.Log
import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.interfaces.IAuthDatabase
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse

class AuthDatabase() : IAuthDatabase {
    private var collaborators: MutableList<Collaborator> = mutableListOf()

    override fun signin(cpf: String, password: String): Collaborator? {
        return collaborators.find { it.cpf == cpf && it.password == password }
    }

    override fun validateCredencials(cpf: String, password: String): ValidationResponse {
        val collaborator = collaborators.find { it.cpf == cpf && it.password == password }

        return if (collaborator != null) ValidationResponse()
        else ValidationResponse("Credenciais inválidas.");
    }

    init {
        collaborators.add(Collaborator(1, "Daniel", "09579556342", "123456"))
    }
}
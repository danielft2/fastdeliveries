package com.example.fastdeliveries.view.collaborator.database
import android.util.Log
import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.interfaces.IAuthDatabase

class AuthDatabase() : IAuthDatabase {
    private var collaborators: MutableList<Collaborator> = mutableListOf()

    override fun signin(cpf: String, password: String): Collaborator? {
        var collaborator = this.collaborators.find {
                it -> it.cpf == cpf && it.password == password
        }

        return collaborator;
    }

    init {
        collaborators.add(Collaborator(1, "Daniel", "09579556342", "123456"))
    }
}
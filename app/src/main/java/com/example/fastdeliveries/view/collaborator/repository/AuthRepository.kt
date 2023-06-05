package com.example.fastdeliveries.view.collaborator.repository

import com.example.fastdeliveries.view.collaborator.database.AuthDatabase
import com.example.fastdeliveries.view.collaborator.models.Collaborator

class AuthRepository private constructor() {
    private var authDatabase: AuthDatabase = AuthDatabase()

    companion object {
        private lateinit var repository: AuthRepository

        fun getInstance(): AuthRepository {
            if (!::repository.isInitialized) {
                repository = AuthRepository()
            }
            return repository
        }
    }

    fun signin(cpf: String, password: String): Collaborator? {
        return this.authDatabase.signin(cpf, password)
    }
}
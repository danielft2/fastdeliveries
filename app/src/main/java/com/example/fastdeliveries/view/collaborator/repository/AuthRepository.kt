package com.example.fastdeliveries.view.collaborator.repository

import android.util.Log
import com.example.fastdeliveries.view.collaborator.database.AuthDatabase
import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse


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

    suspend fun signin(cpf: String, password: String): Collaborator? {
        return try {
            val collaborator = this.authDatabase.signin(cpf, password);
            collaborator
        } catch (e: AppError) {
            Log.e("error", e.message.toString());
            null
        }
    }

}
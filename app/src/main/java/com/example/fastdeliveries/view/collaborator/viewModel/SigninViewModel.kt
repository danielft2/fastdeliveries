package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.fastdeliveries.view.collaborator.repository.AuthRepository
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences

class SigninViewModel(application: Application) : AndroidViewModel(application) {
    private var authRepository = AuthRepository.getInstance()
    private val authStoragePreferences = AuthStoragePreferences.getInstance(application.applicationContext)

    fun signin(cpf: String, password: String): Boolean {
        if (cpf === "" && password === "") return false

        val collaborator = authRepository.signin(cpf, password);
        if (collaborator != null) {
            authStoragePreferences.savedPreference(collaborator)
            return true
        }

        return false
    }
}
package com.example.fastdeliveries.view.collaborator.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.fastdeliveries.view.collaborator.repository.AuthRepository
import com.example.fastdeliveries.view.collaborator.storage.AuthStoragePreferences
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse
import kotlinx.coroutines.launch

class SigninViewModel(application: Application) : AndroidViewModel(application) {
    private var authRepository = AuthRepository.getInstance()
    private val authStoragePreferences = AuthStoragePreferences.getInstance(application.applicationContext)

    private val _signinResponse = MutableLiveData<ValidationResponse>()
    val signinResponse: LiveData<ValidationResponse> = _signinResponse;

    fun signin(cpf: String, password: String) {
        viewModelScope.launch {
            try {
                if (cpf === "" && password === "") throw AppError("Usuario e/ou senha icorretas.")
                val collaborator = authRepository.signin(cpf, password);
                if (collaborator != null) {
                    authStoragePreferences.savedPreference(collaborator)
                    _signinResponse.value = ValidationResponse();
                }
            } catch (error: Throwable) {
                _signinResponse.value = ValidationResponse(error.message.toString());
            }
        }
    }
}
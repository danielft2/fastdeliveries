package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse

interface IAuthDatabase {
    suspend fun signin(cpf: String, password: String): Collaborator?;
    suspend fun validateCredencials(cpf: String, password: String): ValidationResponse;
}
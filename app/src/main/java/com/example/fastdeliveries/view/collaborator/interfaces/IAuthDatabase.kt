package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.services.ValidationResponse

interface IAuthDatabase {
    fun signin(cpf: String, password: String): Collaborator?;
    fun validateCredencials(cpf: String, password: String): ValidationResponse;
}
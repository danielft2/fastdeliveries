package com.example.fastdeliveries.view.collaborator.interfaces

import com.example.fastdeliveries.view.collaborator.models.Collaborator

interface IAuthDatabase {
    fun signin(cpf: String, password: String): Collaborator?;
}
package com.example.fastdeliveries.view.collaborator.models

data class Collaborator(var id: String = "", val name: String = "", val cpf: String = "", val password: String = "") {
    override fun toString(): String {
        return "${id} - ${name} - ${cpf}"
    }
}
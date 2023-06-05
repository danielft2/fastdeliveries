package com.example.fastdeliveries.view.collaborator.models

class Collaborator(var id: Number, val name: String, val cpf: String, val password: String) {
    override fun toString(): String {
        return "${id} - ${name} - ${cpf} - ${password}"
    }
}
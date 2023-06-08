package com.example.fastdeliveries.view.collaborator.models

class Collaborator(var id: Int, val name: String, val cpf: String, val password: String) {
    override fun toString(): String {
        return "${id} - ${name} - ${cpf} - ${password}"
    }
}
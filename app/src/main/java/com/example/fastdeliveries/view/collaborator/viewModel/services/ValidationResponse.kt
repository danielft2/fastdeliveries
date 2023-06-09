package com.example.fastdeliveries.view.collaborator.viewModel.services

class ValidationResponse(message: String = "") {
    private var status: Boolean = true
    private var validationMessage: String = ""

    init {
        if (message != "") {
            validationMessage = message
            status = false
        }

    }

    fun status() = status
    fun message() = validationMessage
}
package com.example.fastdeliveries.view.collaborator.viewModel.services

import com.example.fastdeliveries.view.collaborator.enums.LoadingTypes

class LoadingState(type: LoadingTypes = LoadingTypes.SIMPLE, status: Boolean = true) {
    private var status: Boolean
    private var type: LoadingTypes = LoadingTypes.SIMPLE

    init {
        this.status = status
        this.type = type
    }

    fun status() = status
    fun type() = type
}
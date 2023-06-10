package com.example.fastdeliveries.view.collaborator.view.listeners

interface IAPIListener<T> {
    fun onResponse(result: T)
    fun onFailure(result: String)
}
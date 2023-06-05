package com.example.fastdeliveries.view.collaborator.interfaces

interface IStorageSharedPreferences<T> {
    fun retrievePreference(): T?;
    fun savedPreference(information: T);
}
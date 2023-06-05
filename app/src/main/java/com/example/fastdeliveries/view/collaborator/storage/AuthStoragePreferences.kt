package com.example.fastdeliveries.view.collaborator.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.fastdeliveries.view.collaborator.interfaces.IStorageSharedPreferences
import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.google.gson.Gson

class AuthStoragePreferences(context: Context): IStorageSharedPreferences<Collaborator> {
    private val gson = Gson()
    private val key = StoragePreferencesConfig.KEYS.USER_LOGGED;

    companion object {
        private lateinit var authStoragePreferences: AuthStoragePreferences

        fun getInstance(context: Context): AuthStoragePreferences {
            if (!::authStoragePreferences.isInitialized) {
                authStoragePreferences = AuthStoragePreferences(context);
            }

            return authStoragePreferences
        }
    }

    private val storagePreferences: SharedPreferences = context.getSharedPreferences(
        StoragePreferencesConfig.KEYS.APP_NAME, Context.MODE_PRIVATE
    )

    override fun retrievePreference(): Collaborator? {
        val collaboratorJSON = storagePreferences.getString(key, "") ?: "";
        val collaborator = gson.fromJson(collaboratorJSON, Collaborator::class.java);
        return collaborator
    }

    override fun savedPreference(information: Collaborator) {
        val collaboratorJSON = gson.toJson(information);
        storagePreferences.edit().putString(key, collaboratorJSON).apply()
    }
}
package com.example.fastdeliveries.view.collaborator.database
import android.util.Log
import com.example.fastdeliveries.view.collaborator.models.Collaborator
import com.example.fastdeliveries.view.collaborator.interfaces.IAuthDatabase
import com.example.fastdeliveries.view.collaborator.viewModel.services.AppError
import com.example.fastdeliveries.view.collaborator.viewModel.services.ValidationResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthDatabase() : IAuthDatabase {
    private val db = FirebaseFirestore.getInstance();

    override suspend fun signin(cpf: String, password: String): Collaborator {
        return withContext(Dispatchers.IO) {
            try {
                var collaborator: Collaborator? = null;

                val response = db.collection("collaborators").get().await()
                response.forEach {
                    if (it.data["cpf"] == cpf && it.data["password"] == password) {
                        collaborator = it.toObject(Collaborator::class.java)
                        collaborator!!.id = it.id;
                    }

                }

                if (collaborator != null) return@withContext collaborator!!
                else throw AppError("Usuário e/ou senha incorretos");
            } catch (error: Throwable) {
                if (error::class.java == AppError::class.java) {
                    throw AppError(error.message);
                }

                throw AppError("Ocorreu um erro inesperado, tente novamente.");
            }
        }
    }

    override suspend fun validateCredencials(cpf: String, password: String): ValidationResponse {
        //val collaborator = collaborators.find { it.cpf == cpf && it.password == password }

       // return if (collaborator != null) ValidationResponse()
        //else ValidationResponse("Credenciais inválidas.");
        return ValidationResponse()
    }
}
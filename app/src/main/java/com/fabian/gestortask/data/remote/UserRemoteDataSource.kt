package com.fabian.gestortask.data.remote

import android.util.Log
import com.fabian.gestortask.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    firestore: FirebaseFirestore
)
{
    private val usersCollection = firestore.collection("users")

    fun saveUser(user: User) {
        usersCollection.document(user.id).set(user)
    }

    suspend fun getUserProfile(uid: String): User? {
        return try {
            val document = usersCollection.document(uid).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("UserRemoteDataSource", "Error al obtener perfil de usuario", e)
            null
        }
    }
}

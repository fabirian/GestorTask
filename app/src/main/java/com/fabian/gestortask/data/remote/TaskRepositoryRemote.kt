package com.fabian.gestortask.data.remote

import android.util.Log
import com.fabian.gestortask.auth.FirebaseAuthManager
import com.fabian.gestortask.domain.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryRemote @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authManager: FirebaseAuthManager
) {

    suspend fun getTasks(): List<Task> {
        val userId = authManager.getCurrentUserId() ?: return emptyList()
        return try {
            val snapshot = firestore.collection("tasks")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al obtener tareas", e)
            emptyList()
        }
    }

    suspend fun getTaskById(id: String): Task? {
        return try {
            val doc = firestore.collection("tasks").document(id).get().await()
            doc.toObject(Task::class.java)
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al obtener tarea por ID", e)
            null
        }
    }

    suspend fun addTask(task: Task) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val docRef = firestore.collection("tasks").document()
            val taskWithId = task.copy(id = docRef.id, userId = userId)
            docRef.set(taskWithId).await()
        }
    }


    suspend fun updateTask(task: Task) {
        try {
            firestore.collection("tasks").document(task.id).set(task).await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al actualizar tarea", e)
        }
    }

    suspend fun deleteTask(id: String) {
        try {
            firestore.collection("tasks").document(id).delete().await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al eliminar tarea", e)
        }
    }
}

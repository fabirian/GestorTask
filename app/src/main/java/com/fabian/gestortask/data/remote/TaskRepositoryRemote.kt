package com.fabian.gestortask.data.remote

import android.util.Log
import com.fabian.gestortask.auth.FirebaseAuthManager
import com.fabian.gestortask.domain.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryRemote @Inject constructor(
    val firestore: FirebaseFirestore,
    private val authManager: FirebaseAuthManager
) {

    private val taskCollection = firestore.collection("tasks")

    suspend fun getTasks(): List<Task> {
        val userId = authManager.getCurrentUserId() ?: return emptyList()
        return try {
            val snapshot = taskCollection
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
            val doc = taskCollection.document(id).get().await()
            doc.toObject(Task::class.java)
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al obtener tarea por ID", e)
            null
        }
    }

    suspend fun addTask(task: Task) {
        val userId = authManager.getCurrentUserId()
        if (userId == null) {
            Log.e("TaskRepositoryRemote", "Usuario no autenticado, no se puede agregar la tarea")
            return
        }

        try {
            val docRef = taskCollection.document()
            val taskWithId = task.copy(id = docRef.id, userId = userId)
            docRef.set(taskWithId).await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al añadir tarea", e)
        }
    }

    suspend fun updateTask(task: Task) {
        try {
            taskCollection.document(task.id).set(task).await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al actualizar tarea", e)
        }
    }

    suspend fun deleteTask(id: String) {
        try {
            taskCollection.document(id).delete().await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al eliminar tarea", e)
        }
    }

    suspend fun getTasksByListId(listId: String): List<Task> {
        val userId = authManager.getCurrentUserId() ?: return emptyList()

        return try {
            val snapshot = firestore.collection("tasks")
                .whereEqualTo("userId", userId)
                .whereEqualTo("listId", listId)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(Task::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al obtener tareas por lista", e)
            emptyList()
        }
    }

    fun getCurrentUserId(): String? {
        return authManager.getCurrentUserId()
    }

}


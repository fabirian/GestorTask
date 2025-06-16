package com.fabian.gestortask.data.remote

import android.util.Log
import com.fabian.gestortask.auth.FirebaseAuthManager
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.model.TaskList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskListRepositoryRemote @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authManager: FirebaseAuthManager
) {
    private val tasksListCollection = firestore.collection("tasksLists")
    private val taskCollection = firestore.collection("tasks")

    suspend fun getLists(): List<TaskList> {
        return try {
            val userId = authManager.getCurrentUserId()
            val snapshot = tasksListCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(TaskList::class.java) }
        } catch (e: Exception) {
            Log.e("TaskListRepositoryRemote", "Error al obtener listas de tareas", e)
            emptyList()
        }
    }

    suspend fun getListById(listId: String): TaskList? {
        return try {
            val doc = tasksListCollection.document(listId).get().await()
            val taskList = doc.toObject(TaskList::class.java)
            taskList?.copy(id = doc.id)
        } catch (e: Exception) {
            Log.e("TaskListRepositoryRemote", "Error al obtener lista por ID", e)
            null
        }
    }

    suspend fun addList(taskList: TaskList) {
        val userId = authManager.getCurrentUserId()
        if (userId == null) {
            Log.e("TaskListRepositoryRemote", "Usuario no autenticado, no se puede crear la lista")
            return
        }

        try {
            val docRef = tasksListCollection.document()
            val taskListWithId = taskList.copy(id = docRef.id, userId = userId)
            docRef.set(taskListWithId).await()
        } catch (e: Exception) {
            Log.e("TaskListRepositoryRemote", "Error al añadir lista", e)
        }
    }

    suspend fun deleteList(listId: String) {
        try {
            tasksListCollection.document(listId).delete().await()
        } catch (e: Exception) {
            Log.e("TaskListRepositoryRemote", "Error al eliminar lista", e)
        }
    }

    suspend fun updateList(taskList: TaskList) {
        try {
            tasksListCollection.document(taskList.id).set(taskList).await()
        } catch (e: Exception) {
            Log.e("TaskListRepositoryRemote", "Error al actualizar lista", e)
        }
    }

    suspend fun fetchDefaultListId(userId: String): String {
        return try {
            val snapshot = tasksListCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("defaultList", true)
                .get()
                .await()
            snapshot.documents.firstOrNull()?.getString("id") ?: ""
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al obtener lista por defecto", e)
            ""
        }
    }

    suspend fun updateDefaultListId(userId: String, listId: String) {
        try {
            val snapshot = tasksListCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("defaultList", true)
                .get()
                .await()
            snapshot.documents.forEach { doc ->
                tasksListCollection.document(doc.id).update("defaultList", false).await()
            }

            tasksListCollection.document(listId).update("defaultList", true).await()
        } catch (e: Exception) {
            Log.e("TaskRepositoryRemote", "Error al actualizar lista por defecto", e)
        }
    }
}

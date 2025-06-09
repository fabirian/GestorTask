package com.fabian.gestortask.data.repository

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRepository {

    private val taskCollection = firestore.collection("tasks")

    override suspend fun getTasks(): List<Task> {
        return try {
            val snapshot = taskCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addTask(task: Task) {
        try {
            val docRef = taskCollection.document()
            val taskWithId = task.copy(id = docRef.id)
            println("Añadiendo tarea con id: ${taskWithId.id}, título: ${taskWithId.title}")
            docRef.set(taskWithId).await()
        } catch (e: Exception) {
            println("Error al añadir tarea: ${e.message}")
        }
    }

    override suspend fun getTaskById(id: String): Task? {
        return try {
            val doc = taskCollection.document(id).get().await()
            doc.toObject(Task::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateTask(task: Task) {
        try {
            taskCollection.document(task.id).set(task).await()
        } catch (_: Exception) {}
    }

    override suspend fun deleteTask(id: String) {
        try {
            taskCollection.document(id).delete().await()
        } catch (_: Exception) {}
    }
}

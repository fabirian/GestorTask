package com.fabian.gestortask.data.remote

import com.fabian.gestortask.domain.model.ColorLabel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ColorLabelRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val colorLabelCollection = firestore.collection("colorLabels")

    suspend fun getColorLabels(): List<ColorLabel> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        val snapshot = colorLabelCollection
            .whereEqualTo("userId", uid)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(ColorLabel::class.java) }
    }

    suspend fun addColorLabel(newColor: ColorLabel) {
        val uid = auth.currentUser?.uid ?: return
        val colorLabel = ColorLabel(uid, newColor.colorHex, newColor.label)
        colorLabelCollection.add(colorLabel).await()
    }

    suspend fun deleteColorLabel(hex: String) {
        val uid = auth.currentUser?.uid ?: return
        val snapshot = colorLabelCollection
            .whereEqualTo("userId", uid)
            .whereEqualTo("colorHex", hex)
            .get()
            .await()
        snapshot.documents.forEach { it.reference.delete().await() }
    }

    suspend fun editColorLabel(hex: String, newLabel: String) {
        val uid = auth.currentUser?.uid ?: return
        val snapshot = colorLabelCollection
            .whereEqualTo("userId", uid)
            .whereEqualTo("colorHex", hex)
            .get()
            .await()
        snapshot.documents.forEach {
            it.reference.update("label", newLabel).await()
        }
    }

    suspend fun saveAllLabels(labels: List<ColorLabel>) {
        val uid = auth.currentUser?.uid ?: return
        val batch = firestore.batch()

        val snapshot = colorLabelCollection
            .whereEqualTo("userId", uid)
            .get()
            .await()

        snapshot.documents.forEach {
            batch.delete(it.reference)
        }

        labels.forEach {
            val newDoc = colorLabelCollection.document()
            batch.set(newDoc, it)
        }

        batch.commit().await()
    }
}

package com.fabian.gestortask.di

import com.fabian.gestortask.data.repository.TaskRepositoryImpl
import com.fabian.gestortask.domain.repository.TaskRepository
import com.fabian.gestortask.domain.usecases.task.AddTask
import com.fabian.gestortask.domain.usecases.task.DeleteTask
import com.fabian.gestortask.domain.usecases.task.GetTaskById
import com.fabian.gestortask.domain.usecases.task.GetTasks
import com.fabian.gestortask.domain.usecases.task.TaskUseCases
import com.fabian.gestortask.domain.usecases.task.UpdateTask
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        firestore: FirebaseFirestore
    ): TaskRepository = TaskRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            addTask = AddTask(repository),
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            updateTask = UpdateTask(repository),
            getTaskById = GetTaskById(repository)
        )
    }
}


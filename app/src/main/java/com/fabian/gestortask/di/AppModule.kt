package com.fabian.gestortask.di

import com.fabian.gestortask.data.remote.ColorLabelRemoteDataSource
import com.fabian.gestortask.data.remote.TaskListRepositoryRemote
import com.fabian.gestortask.data.remote.TaskRepositoryRemote
import com.fabian.gestortask.data.repository.ColorLabelRepositoryImpl
import com.fabian.gestortask.data.repository.TaskListRepositoryImpl
import com.fabian.gestortask.data.repository.TaskRepositoryImpl
import com.fabian.gestortask.domain.repository.ColorLabelRepository
import com.fabian.gestortask.domain.repository.TaskListRepository
import com.fabian.gestortask.domain.repository.TaskRepository
import com.fabian.gestortask.domain.usecases.colorlabel.AddColorLabel
import com.fabian.gestortask.domain.usecases.colorlabel.ColorUseCase
import com.fabian.gestortask.domain.usecases.colorlabel.DeleteColorLabel
import com.fabian.gestortask.domain.usecases.colorlabel.EditColorLabel
import com.fabian.gestortask.domain.usecases.colorlabel.GetColorLabels
import com.fabian.gestortask.domain.usecases.colorlabel.SaveAllLabels
import com.fabian.gestortask.domain.usecases.task.AddTask
import com.fabian.gestortask.domain.usecases.task.DeleteTask
import com.fabian.gestortask.domain.usecases.task.GetCurrentUserId
import com.fabian.gestortask.domain.usecases.task.GetTaskById
import com.fabian.gestortask.domain.usecases.task.GetTasks
import com.fabian.gestortask.domain.usecases.task.GetTasksByListId
import com.fabian.gestortask.domain.usecases.task.TaskUseCases
import com.fabian.gestortask.domain.usecases.task.UpdateTask
import com.fabian.gestortask.domain.usecases.tasklist.AddList
import com.fabian.gestortask.domain.usecases.tasklist.DeleteList
import com.fabian.gestortask.domain.usecases.tasklist.FetchDefaultListId
import com.fabian.gestortask.domain.usecases.tasklist.GetListById
import com.fabian.gestortask.domain.usecases.tasklist.GetLists
import com.fabian.gestortask.domain.usecases.tasklist.TaskListUseCases
import com.fabian.gestortask.domain.usecases.tasklist.UpdateDefaultListId
import com.fabian.gestortask.domain.usecases.tasklist.UpdateList
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
        remote: TaskRepositoryRemote
    ): TaskRepository = TaskRepositoryImpl(remote)

    @Provides
    @Singleton
    fun provideTaskListRepository(
        remoteList: TaskListRepositoryRemote
    ): TaskListRepository = TaskListRepositoryImpl(remoteList)

    @Provides
    @Singleton
    fun providesColorLabelRepository(
        remote: ColorLabelRemoteDataSource
    ): ColorLabelRepository = ColorLabelRepositoryImpl(remote)

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            addTask = AddTask(repository),
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            updateTask = UpdateTask(repository),
            getTaskById = GetTaskById(repository),
            getTasksByListId = GetTasksByListId(repository),
            getCurrentUserId = GetCurrentUserId(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTaskListUseCases(repository: TaskListRepository): TaskListUseCases = TaskListUseCases(
        getLists = GetLists(repository),
        addList = AddList(repository),
        deleteList = DeleteList(repository),
        updateList = UpdateList(repository),
        getListById = GetListById(repository),
        fetchDefaultListId = FetchDefaultListId(repository),
        updateDefaultListId = UpdateDefaultListId(repository)
    )

     @Provides
     @Singleton
     fun providesColorUseCases(repository: ColorLabelRepository): ColorUseCase = ColorUseCase(
         getColorLabels = GetColorLabels(repository),
         addColorLabel = AddColorLabel(repository),
         deleteColorLabel = DeleteColorLabel(repository),
         editColorLabel = EditColorLabel(repository),
         saveAllLabels = SaveAllLabels(repository)
     )
}


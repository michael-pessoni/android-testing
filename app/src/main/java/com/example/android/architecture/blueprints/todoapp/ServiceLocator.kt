package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private var database: ToDoDatabase? = null

    @Volatile
    var taskRepository: TaskRepository? = null
        @VisibleForTesting set

    private val lock = Any()

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                TasksRemoteDataSource.deleteAllTasks()
            }
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            taskRepository = null
        }
    }

    fun provideTaskRepository(context: Context): TaskRepository {
        synchronized(this) {
            return taskRepository ?: createTaskRepository(context)
        }
    }

    private fun createTaskRepository(context: Context): TaskRepository {
        val newRepository =
            DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        taskRepository = newRepository
        return newRepository
    }

    private fun createTaskLocalDataSource(context: Context): TasksLocalDataSource {
        val database = database ?: createDataBase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDataBase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()
    }

}
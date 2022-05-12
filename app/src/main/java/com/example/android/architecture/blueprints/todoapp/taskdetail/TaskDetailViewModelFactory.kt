package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository

@Suppress("UNCHECKED_CAST")
class TaskDetailViewModelFactory(private val tasksRepository: TaskRepository):
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (TaskDetailViewModel(tasksRepository) as T)
}
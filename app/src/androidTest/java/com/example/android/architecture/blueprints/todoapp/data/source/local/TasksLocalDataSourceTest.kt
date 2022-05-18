package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksLocalDataSource: TasksLocalDataSource
    private lateinit var database: ToDoDatabase

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        tasksLocalDataSource = TasksLocalDataSource(database.taskDao(), Dispatchers.Main)
    }

    @After
    fun cleanUp(){
        database.close()
    }

    @Test
    fun saveTask_retrievesTask() = runBlocking {

        val newTask = Task("title", "description", false)
        tasksLocalDataSource.saveTask(newTask)

        val result = tasksLocalDataSource.getTask(newTask.id)

        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`("title"))
        assertThat(result.data.description, `is`("description"))
        assertThat(result.data.isCompleted, `is`(false))
    }

    @Test
    fun completedTask_retrievesTaskIsCompleted() = runBlocking {

        val newTask = Task("title", "description", false)
        tasksLocalDataSource.saveTask(newTask)

        tasksLocalDataSource.completeTask(newTask)

        val result = tasksLocalDataSource.getTask(newTask.id)

        assertThat(result.succeeded, `is`(true))
        result as Result.Success
        assertThat(result.data.title, `is`("title"))
            assertThat(result.data.isCompleted, `is`(true))
    }



}
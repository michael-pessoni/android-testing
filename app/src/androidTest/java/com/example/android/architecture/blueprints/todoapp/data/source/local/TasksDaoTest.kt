package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase

    @Before
    fun setupDatabase(){
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            ToDoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = runBlockingTest{
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        val loadedTask = database.taskDao().getTaskById(task.id)

        assertThat<Task>(loadedTask as Task, notNullValue())
        assertThat(loadedTask.id, `is`(task.id))
        assertThat(loadedTask.title, `is`(task.title))
        assertThat(loadedTask.description, `is`(task.description))
        assertThat(loadedTask.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = runBlockingTest{
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        val updatedTask = Task("modified", "modifiedDescription", true, task.id)
        database.taskDao().updateTask(updatedTask)

        val loadedTask = database.taskDao().getTaskById(task.id)

        assertThat(loadedTask as Task, notNullValue())
        assertThat(loadedTask.id, `is`(updatedTask.id))
        assertThat(loadedTask.title, `is`(updatedTask.title))
        assertThat(loadedTask.description, `is`(updatedTask.description))
        assertThat(loadedTask.isCompleted, `is`(updatedTask.isCompleted))
    }

}

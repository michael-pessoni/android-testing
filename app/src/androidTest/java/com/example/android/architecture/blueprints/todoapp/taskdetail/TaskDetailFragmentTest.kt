package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest{

    lateinit var repository: FakeAndroidTestRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.taskRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayInUi() = runBlockingTest {
        // Creates a task
        val activeTask = Task("Active task", "AndroidX Rocks", false)

        // Save task to repository
        repository.saveTask(activeTask)

        // Creates a Bundle to pass the task as the fragment argument
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()

        // Creates a FragmentScenario with the bundle and a theme
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
    }

}
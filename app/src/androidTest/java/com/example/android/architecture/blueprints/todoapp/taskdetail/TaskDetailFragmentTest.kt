package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest{

    @Test
    fun activeTaskDetails_DisplayInUi() {
        // Creates a task
        val activeTask = Task("Active task", "AndroidX Rocks", false)

        // Creates a Bundle to pass the task as the fragment argument
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()

        // Creates a FragmentScenario with the bundle and a theme
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
    }

}
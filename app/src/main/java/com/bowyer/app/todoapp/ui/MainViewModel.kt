package com.bowyer.app.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val toDoRepository: ToDoRepository
) : ViewModel() {

  fun onCreate() {
    viewModelScope.launch {
      try {
        val todoList = toDoRepository.getAll()
        Timber.tag("todolist").d(todoList.size.toString())
      } catch (e: Throwable) {
        Timber.tag("todolist").e(e)
      }
    }
  }
}

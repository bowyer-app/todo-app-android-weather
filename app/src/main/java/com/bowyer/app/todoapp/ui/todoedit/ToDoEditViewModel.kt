package com.bowyer.app.todoapp.ui.todoedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import com.bowyer.app.todoapp.domain.DateTimeVO
import com.bowyer.app.todoapp.domain.ToDoContent
import com.bowyer.app.todoapp.domain.ToDoId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class ToDoEditViewModel @Inject constructor(
  private val toDoRepository: ToDoRepository
) : ViewModel() {

  private val _state = MutableLiveData(
    ToDoEditState()
  )
  val state: LiveData<ToDoEditState> = _state

  private val _event = MutableSharedFlow<ToDoEditEvent>()
  val event: SharedFlow<ToDoEditEvent> get() = _event

  fun onCreateView(content: ToDoContent?) {
    _state.value = _state.value?.copy(toDoContent = content)
  }

  fun onClickSave(title: String?, description: String?) {
    viewModelScope.launch {
      _event.emit(ToDoEditEvent.HideKeyboard)
    }
    if (title.isNullOrEmpty() || description.isNullOrEmpty()) {
      viewModelScope.launch {
        _event.emit(ToDoEditEvent.PresentShowNeedValueAlertDialog)
      }
      return
    }
    val content = state.value?.toDoContent?.copy(
      title = title,
      description = description
    )
      ?: ToDoContent(
        id = ToDoId.DEFAULT,
        title = title,
        description = description,
        dateTime = DateTimeVO(DateTime.now().millis),
        isDone = false
      )
    viewModelScope.launch {
      try {
        toDoRepository.save(todo = content)
        viewModelScope.launch {
          _event.emit(ToDoEditEvent.PopBackStack)
        }
      } catch (e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
          _event.emit(ToDoEditEvent.PresentSaveToDoFailedDialog)
        }
      }
    }
  }

  fun onClickDelete() {
    val content = _state.value?.toDoContent ?: return
    viewModelScope.launch {
      try {
        toDoRepository.delete(todo = content)
        viewModelScope.launch {
          _event.emit(ToDoEditEvent.PresentDeleteToDoSuccessToastPop)
        }
      } catch (e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
          _event.emit(ToDoEditEvent.PresentDeleteToDoFailed)
        }
      }
    }
  }
}

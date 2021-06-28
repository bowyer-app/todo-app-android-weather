package com.bowyer.app.todoapp.ui.todoedit

sealed class ToDoEditEvent {
  object PresentSaveToDoFailedDialog : ToDoEditEvent()
  object PresentDeleteToDoFailed : ToDoEditEvent()
  object PopBackStack : ToDoEditEvent()
  object PresentDeleteToDoSuccessToastPop : ToDoEditEvent()
  object PresentShowNeedValueAlertDialog : ToDoEditEvent()
  object HideKeyboard : ToDoEditEvent()
}

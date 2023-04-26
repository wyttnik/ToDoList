package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.database.ToDoDao
import com.example.todolist.data.entity.ToDoAction
import kotlinx.coroutines.launch

class ToDoListViewModel(private val todoDao: ToDoDao): ViewModel() {
    val allActions = todoDao.getAllActions().asLiveData()

    fun addNewAction(action:String, detailedInfo:String, checkStatus:Boolean) =
        viewModelScope.launch {
            todoDao.insert(ToDoAction(
                action = action,
                detailedInfo = detailedInfo,
                checkStatus = checkStatus)
            )
        }

    fun retrieveAction(id:Int) = todoDao.getItem(id).asLiveData()

    fun updateActionCheckStatus(action: ToDoAction) {
        val newAction = action.copy(checkStatus = !action.checkStatus)
        // action.checkStatus = !action.checkStatus // areContentsTheSame returns true in this case, so don't use it
        viewModelScope.launch {
            todoDao.updateReady(newAction)
        }
    }

    fun deleteAction(toDoAction: ToDoAction) = viewModelScope.launch {
        todoDao.delete(toDoAction)
    }
}

class ToDoListViewModelFactory(private val todoDao: ToDoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
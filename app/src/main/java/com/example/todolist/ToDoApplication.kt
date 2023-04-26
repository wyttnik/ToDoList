package com.example.todolist

import android.app.Application
import com.example.todolist.data.database.ToDoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ToDoApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ToDoDatabase.getDatabase(this, applicationScope) }
}
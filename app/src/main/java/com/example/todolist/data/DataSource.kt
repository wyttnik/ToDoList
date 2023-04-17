package com.example.todolist.data

import com.example.todolist.model.ToDoItem

object DataSource {
    fun loadToDoItems():MutableList<ToDoItem> {
        toDoItems.add(ToDoItem("Try too hard"))
        toDoItems.add(ToDoItem("Get so far"))
        return toDoItems
    }

    val toDoItems:MutableList<ToDoItem> = mutableListOf()
}
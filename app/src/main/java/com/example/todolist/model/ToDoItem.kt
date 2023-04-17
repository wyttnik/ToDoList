package com.example.todolist.model

data class ToDoItem(val action: String, val detailedInfo:String = "", var checkStatus: Boolean = false)
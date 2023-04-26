package com.example.todolist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "To_do_things")
data class ToDoAction (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "row_id")
    val id: Int = 0,
    val action: String,
    val detailedInfo:String = "",
    var checkStatus: Boolean = false
)
package com.example.todolist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.data.entity.ToDoAction
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("select * from to_do_things")
    fun getAllActions(): Flow<List<ToDoAction>>

    @Query("select * from to_do_things where row_id=:id")
    fun getItem(id:Int):Flow<ToDoAction>

    @Insert
    suspend fun insert(action: ToDoAction)

    @Update
    suspend fun updateReady(action: ToDoAction)

    @Delete
    suspend fun delete(action: ToDoAction)
}
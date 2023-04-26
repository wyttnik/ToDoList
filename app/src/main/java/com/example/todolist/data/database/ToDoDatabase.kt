package com.example.todolist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.entity.ToDoAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ToDoAction::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): ToDoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "to_do_database"
                )
                    .addCallback(ToDoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
//                instance
            }
        }
        private class ToDoDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.toDoDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(todoDao: ToDoDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            for(i in 0 until 30){
                todoDao.insert(ToDoAction(
                    action="Need to do thing $i",
                    detailedInfo = "Detailed info about thing $i",
                    checkStatus = false))
            }
        }
    }
}
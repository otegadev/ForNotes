package com.promisesdk.fornotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (
    entities = [Note::class, Todo::class, TodoItem::class, Journal::class, Entry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converter::class
)
abstract class ForNotesDatabase: RoomDatabase() {
    abstract val forNotesDao: ForNotesDao

    companion object {
        @Volatile
        private var instance: ForNotesDatabase? = null

        fun getDatabase(context: Context): ForNotesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = ForNotesDatabase::class.java,
                    name = "forNote_Database"
                )
                    .build()
                    .also {instance = it}
            }
        }
    }
}
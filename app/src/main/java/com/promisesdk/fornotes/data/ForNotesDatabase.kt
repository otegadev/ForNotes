package com.promisesdk.fornotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (
    entities = [NotesData::class, TodosData::class, TodoItem::class, JournalsData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class
)
abstract class ForNotesDatabase: RoomDatabase() {
    abstract val forNotesDao: ForNotesDao
    abstract val stringListConverter: StringListConverter

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
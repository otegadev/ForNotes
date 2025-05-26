package com.promisesdk.fornotes.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Data class for notes
 */
@Entity(tableName = "notes")
data class NotesData (
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val noteTitle: String,
    val noteContent: String,
    val noteLabel: String? = null,
    val creationTimeInMillis: Long,
)

/**
 * Data class for todos
 */
@Entity(tableName = "todos")
data class TodosData (
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0,
    val todoTitle: String,
    val todoLabel: String,
    val todoStatus: String,
    val creationTimeInMillis: Long,
)

/**
 * Data class for todo items
 */
@Serializable
@Entity(
    tableName = "todoItems",
    foreignKeys = [ForeignKey(
        entity = TodosData::class,
        parentColumns = ["todoId"],
        childColumns = ["todosDataId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["todosDataId"])]
)
data class TodoItem(
    @PrimaryKey (autoGenerate = true) val todoItemId: Int = 0,
    val todosDataId: Int,
    val primaryText: String,
    @Serializable val secondaryTexts: List<String> = emptyList(),
    var isChecked: Boolean = false,
    val priority: String = "",
)

/**
 * Data class for todos with items
 */
data class TodoDataWithItems (
    @Embedded val todosData: TodosData,
    @Relation(
        parentColumn = "todoId",
        entityColumn = "todosDataId"
    )
    val todoItems: List<TodoItem>
)

/**
 * Type converter for list of strings
 */
class StringListConverter {
    @TypeConverter
    fun fromStringList (stringList: List<String>): String{
        return Json.encodeToString(stringList)
    }

    @TypeConverter
    fun toTodoItemList(listString: String): List<String> {
        return Json.decodeFromString(listString)
    }
}

/**
 * Data class for journals
 */

@Entity(tableName = "journals")
data class JournalsData (
    @PrimaryKey(autoGenerate = true)
    val journalId: Int = 0,
    val journalName: String,
    val journalDescription: String,
    val journalContent: String,
    val journalType: String,
    val creationTimeInMillis: Long,
)
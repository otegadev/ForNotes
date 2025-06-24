package com.promisesdk.fornotes.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.DevicesOther
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material.icons.rounded.MeetingRoom
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.Portrait
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.material.icons.rounded.VideogameAsset
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.ui.theme.customColor1
import com.promisesdk.fornotes.ui.theme.customColor10
import com.promisesdk.fornotes.ui.theme.customColor11
import com.promisesdk.fornotes.ui.theme.customColor12
import com.promisesdk.fornotes.ui.theme.customColor13
import com.promisesdk.fornotes.ui.theme.customColor14
import com.promisesdk.fornotes.ui.theme.customColor15
import com.promisesdk.fornotes.ui.theme.customColor16
import com.promisesdk.fornotes.ui.theme.customColor17
import com.promisesdk.fornotes.ui.theme.customColor18
import com.promisesdk.fornotes.ui.theme.customColor19
import com.promisesdk.fornotes.ui.theme.customColor2
import com.promisesdk.fornotes.ui.theme.customColor20
import com.promisesdk.fornotes.ui.theme.customColor21
import com.promisesdk.fornotes.ui.theme.customColor22
import com.promisesdk.fornotes.ui.theme.customColor3
import com.promisesdk.fornotes.ui.theme.customColor4
import com.promisesdk.fornotes.ui.theme.customColor5
import com.promisesdk.fornotes.ui.theme.customColor6
import com.promisesdk.fornotes.ui.theme.customColor7
import com.promisesdk.fornotes.ui.theme.customColor8
import com.promisesdk.fornotes.ui.theme.customColor9
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Data class for notes
 */
@Serializable
@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var content: String,
    @Serializable val label: ForNotesLabels? = null,
    val creationTimeInMillis: Long,
)

/**
 * Data class for todos
 */
@Serializable
@Entity(tableName = "todos")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @Serializable val label: ForNotesLabels? = null,
    @Serializable val status: TodoStatus,
    val creationTimeInMillis: Long,
)

/**
 * Data class for todo items
 */
@Serializable
@Entity(
    tableName = "todoItems",
    foreignKeys = [ForeignKey(
        entity = Todo::class,
        parentColumns = ["id"],
        childColumns = ["todoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["todoId"])]
)
data class TodoItem(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val todoId: Int,
    var primaryText: String,
    @Serializable val secondaryTexts: List<String> = emptyList(),
    var isChecked: Boolean = false,
    val priority: String? = null,
)

/**
 * Data class for todos with items
 */
data class TodoWithItems (
    @Embedded val todo: Todo,
    @Relation(
        parentColumn = "id",
        entityColumn = "todoId"
    )
    val todoItems: List<TodoItem>
)

/**
 * Data class for journals
 */
@Serializable
@Entity(tableName = "journals")
data class Journal (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val noOfEntries: Int,
    @Serializable val type: JournalType,
    val creationTimeInMillis: Long,
)

@Entity(
    tableName = "entries",
    foreignKeys = [
        ForeignKey(
            entity = Journal::class,
            parentColumns = ["id"],
            childColumns = ["journalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["journalId"])]
)
data class Entry (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val journalId: Int,
    var name: String,
    var content: String,
    val creationTimeInMillis: Long
)

data class JournalWithEntries(
    @Embedded val journal: Journal,
    @Relation(
        parentColumn = "id",
        entityColumn = "journalId"
    )
    val entries: List<Entry>
)

/**
 * Type converter for enums and list of strings
 */
class Converter {
    @TypeConverter
    fun fromStringList (stringList: List<String>): String{
        return Json.encodeToString(stringList)
    }

    @TypeConverter
    fun toTodoItemList(listString: String): List<String> {
        return Json.decodeFromString(listString)
    }

    @TypeConverter
    fun fromLabels(label: ForNotesLabels?): String? {
        return label?.name
    }

    @TypeConverter
    fun toLabels(label: String?): ForNotesLabels? {
        return label?.let { ForNotesLabels.valueOf(it) }
    }

    @TypeConverter
    fun fromTodoStatus(status: TodoStatus) = status.name

    @TypeConverter
    fun toTodoStatus(status: String) = TodoStatus.valueOf(status)

    @TypeConverter
    fun fromJournalType(type: JournalType) = type.name

    @TypeConverter
    fun toJournalType(type: String) = JournalType.valueOf(type)
}


enum class ForNotesLabels (
    val color: Color,
    val icon: ImageVector
) {
    School (
        color = customColor1,
        icon = Icons.Rounded.School
    ),
    Business (
        color = customColor2,
        icon = Icons.Rounded.Business
    ) ,
    Event (
        color = customColor3,
        icon = Icons.Rounded.Event
    ),
    Meeting (
        color = customColor4,
        icon = Icons.Rounded.MeetingRoom
    ),
    Personal (
        color = customColor5,
        icon = Icons.Rounded.Portrait
    ),
    Work (
        color = customColor6,
        icon = Icons.Rounded.Work
    ),
    Ideas (
        color = customColor7,
        icon = Icons.Rounded.Lightbulb
    ),
    Goals (
        color = customColor8,
        icon = Icons.Rounded.EmojiEvents
    ),
    Shopping (
        color = customColor9,
        icon = Icons.Rounded.ShoppingBasket
    ),
    Travel (
        color = customColor10,
        icon = Icons.Rounded.FlightTakeoff
    ),
    Reminder (
        color = customColor11,
        icon = Icons.Rounded.PendingActions
    ),
    Gadgets (
        color = customColor12,
        icon = Icons.Rounded.DevicesOther
    ),
    Study (
        color = customColor13,
        icon = Icons.Rounded.LocalLibrary
    ),
    Fitness (
        color = customColor14,
        icon = Icons.Rounded.FitnessCenter
    ),
    Health (
        color = customColor15,
        icon = Icons.Rounded.HealthAndSafety
    ),
    Project (
        color = customColor16,
        icon = Icons.AutoMirrored.Rounded.Assignment
    ),
    Hobby (
        color = customColor17,
        icon = Icons.Rounded.VideogameAsset
    )
}

enum class TodoItemPriority {
    Low,
    Medium,
    High
}

enum class TodoStatus (
    val status: Int,
    val color: Color
) {
    Pending (
        status = R.string.pending_status,
        color = customColor4
    ),
    InProgress (
        status = R.string.in_progress_status,
        color = customColor20
    ),
    Completed (
        status = R.string.completed_status,
        color = customColor22
    ),
    Cancelled (
        status = R.string.cancelled_status,
        color = customColor3
    )
}

enum class JournalType (
    val journalTypeName: String,
    val color: Color,
) {
    Diary (
        journalTypeName = "Diary",
        color = customColor18
    ),
    Gratitude (
        journalTypeName = "Gratitude",
        color = customColor19
    ),
    Dream (
        journalTypeName = "Dream",
        color = customColor20
    ),
    HabitTracker (
        journalTypeName = "Habit Tracker",
        color = customColor21
    ),
    Custom (
        journalTypeName = "Custom",
        color = customColor22
    )
}
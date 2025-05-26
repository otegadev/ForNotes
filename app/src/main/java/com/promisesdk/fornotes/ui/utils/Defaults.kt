package com.promisesdk.fornotes.ui.utils

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
import com.promisesdk.fornotes.data.JournalsData
import com.promisesdk.fornotes.data.NotesData
import com.promisesdk.fornotes.data.TodoDataWithItems
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

enum class TodoStatus (val status: String) {
    Pending (status = "Pending"),
    InProgress (status = "In Progress"),
    Completed (status = "Completed"),
    Cancelled (status = "Cancelled")
}

sealed interface SearchResults {
    data class NotesSearchResults(val notes: List<NotesData>) : SearchResults
    data class TodosSearchResults(val todos: List<TodoDataWithItems>) : SearchResults
    data class JournalsSearchResults(val journals: List<JournalsData>) : SearchResults
}
package com.promisesdk.fornotes.ui.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.promisesdk.fornotes.data.Journal
import com.promisesdk.fornotes.data.Note
import com.promisesdk.fornotes.data.TodoWithItems

class EditScreenActions(
    val actionName: String,
    val icon: ImageVector,
    val onActionClick: () -> Unit
)

sealed interface SearchResults {
    data class NotesSearchResults(val notes: List<Note>) : SearchResults
    data class TodosSearchResults(val todos: List<TodoWithItems>) : SearchResults
    data class JournalsSearchResults(val journals: List<Journal>) : SearchResults
}
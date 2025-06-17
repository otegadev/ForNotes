package com.promisesdk.fornotes.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.Journal
import com.promisesdk.fornotes.data.Note
import com.promisesdk.fornotes.data.TodoWithItems

class EditScreenActions(
    val actionName: Int,
    val icon: ImageVector,
    val onActionClick: () -> Unit
)

val defaultTopBarActions = listOf(
    EditScreenActions(
        actionName = R.string.labels,
        icon = Icons.AutoMirrored.Rounded.Label,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.share,
        icon = Icons.Rounded.Share,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.delete,
        icon = Icons.Rounded.Delete,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.move_archive,
        icon = Icons.Rounded.Archive,
        onActionClick = {}
    )
)

sealed interface SearchResults {
    data class NotesSearchResults(val notes: List<Note>) : SearchResults
    data class TodosSearchResults(val todos: List<TodoWithItems>) : SearchResults
    data class JournalsSearchResults(val journals: List<Journal>) : SearchResults
}
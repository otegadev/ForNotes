package com.promisesdk.fornotes.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatUnderlined
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

val bottomBarActions: List<EditScreenActions> = listOf(
    EditScreenActions(
        actionName = R.string.bold,
        icon = Icons.Rounded.FormatBold,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.italic,
        icon = Icons.Rounded.FormatItalic,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.underline,
        icon = Icons.Rounded.FormatUnderlined,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.cut,
        icon = Icons.Rounded.ContentCut,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.copy,
        icon = Icons.Rounded.ContentCopy,
        onActionClick = {}
    ),
    EditScreenActions(
        actionName = R.string.paste,
        icon = Icons.Rounded.ContentPaste,
        onActionClick = {}
    ),
)

sealed interface SearchResults {
    data class NotesSearchResults(val notes: List<Note>) : SearchResults
    data class TodosSearchResults(val todos: List<TodoWithItems>) : SearchResults
    data class JournalsSearchResults(val journals: List<Journal>) : SearchResults
}
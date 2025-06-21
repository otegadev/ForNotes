package com.promisesdk.fornotes.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class NotesViewModel (
    val forNotesRepository: ForNotesRepository
): ViewModel() {

    val notesHomeUiState: StateFlow<NotesUiState> =
        forNotesRepository.getAllNotes().map { NotesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = NotesUiState()
            )

}


data class NotesUiState(
    val notes: List<Note> = emptyList()
)
package com.promisesdk.fornotes.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class NotesViewModel (
    val forNotesRepository: ForNotesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(NoteState())
    val uiState: StateFlow<NoteState> = _uiState.asStateFlow()

    val notesHomeUiState: StateFlow<NotesUiState> =
        forNotesRepository.getAllNotes().map { NotesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NotesUiState()
            )

    fun loadNote(id: Int): StateFlow<NoteState>? {
        return if (id != 0)
            forNotesRepository.getNoteById(id)?.map { NoteState(note = it) }
                ?.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = NoteState()
                )
        else
            _uiState
    }

    fun addNote(note: Note) {
        if (_uiState.value.validInput())
            viewModelScope.launch {
                forNotesRepository.insertNote(note = note)
            }
    }

    fun updateNote() {
        viewModelScope.launch {
            forNotesRepository.updateNote(note = _uiState.value.note)
        }
    }
}

data class NoteState(
    val note: Note = Note(
        id = 0,
        title = "",
        content = "",
        label = null,
        creationTimeInMillis = System.currentTimeMillis()
    )
)

fun NoteState.validInput(): Boolean {
    return note.title.trim() != "" || note.content.trim() != ""
}

data class NotesUiState(
    val notes: List<Note> = emptyList()
)
package com.promisesdk.fornotes.ui.screens.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesLabels
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NotesViewModel (
    val forNotesRepository: ForNotesRepository
): ViewModel() {

    var noteUiState by mutableStateOf(NoteState())
        private set

    val notesHomeUiState: StateFlow<NotesHomeUiState> =
        forNotesRepository.getAllNotes().map { NotesHomeUiState(notes = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NotesHomeUiState()
            )

    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteState(noteDetails = noteDetails, isInputValid = validateInput(noteDetails))
    }

    fun resetUiState() {
        noteUiState = NoteState()
    }

    private fun validateInput(noteState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(noteState) {
            title.isNotBlank() || content.isNotBlank()
        }
    }

    fun loadExistingNote(id: Int): StateFlow<NoteState> {
       return forNotesRepository.getNoteById(id).map { NoteState(
           noteDetails = it.toNoteDetails(), isInputValid = true
       ) }
           .stateIn(
               scope = viewModelScope,
               started = SharingStarted.WhileSubscribed(5000L),
               initialValue = NoteState()
           )
    }

    fun loadNewNote(): NoteState {
        return noteUiState
    }

    fun addNote(noteUiState: NoteState) {
        with(noteUiState) {
            if (validateInput(noteDetails))
                viewModelScope.launch {
                    forNotesRepository.insertNote(note = noteDetails.toNote())
                }
        }
    }


    fun updateNote() {

    }
}

data class NoteState (
    val noteDetails: NoteDetails = NoteDetails(),
    val isInputValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val content:String = "",
    val label: ForNotesLabels? = null,
    val creationTimeInMillis: Long = System.currentTimeMillis()
)

fun NoteDetails.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        label = label,
        creationTimeInMillis = creationTimeInMillis
    )
}

fun Note.toNoteDetails(): NoteDetails {
    return NoteDetails(
        id = id,
        title = title,
        content = content,
        label = label,
        creationTimeInMillis = creationTimeInMillis
    )
}

data class NotesHomeUiState(
    val notes: List<Note> = emptyList()
)
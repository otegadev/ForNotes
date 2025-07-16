package com.promisesdk.fornotes.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesLabels
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class NotesViewModel (
    val forNotesRepository: ForNotesRepository
): ViewModel() {

    private var _noteUiState = MutableStateFlow(NoteState())
    val noteUiState = _noteUiState.asStateFlow()

    val notesHomeUiState: StateFlow<NotesHomeUiState> =
        forNotesRepository.getAllNotes().map { NotesHomeUiState(notes = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NotesHomeUiState()
            )

    fun updateUiState(noteDetails: NoteDetails) {
        _noteUiState.value = NoteState(noteDetails = noteDetails, isInputValid = validateInput(noteDetails))
    }

    fun resetUiState() {
        _noteUiState.value = NoteState()
    }

    fun addNote(noteUiState: NoteState) {
        with(noteUiState) {
            if (validateInput(noteDetails))
                viewModelScope.launch {
                    forNotesRepository.insertNote(note = noteDetails.toNote())
                }
        }
    }

    private fun validateInput(noteState: NoteDetails = _noteUiState.value.noteDetails): Boolean {
        return with(noteState) {
            title.isNotBlank() || content.isNotBlank()
        }
    }

    fun loadExistingNote(id: Int) {
        if (id == 0) {
            loadNewNote()
            return
        }
        else
            viewModelScope.launch {
                forNotesRepository.getNoteById(id).collect { note ->
                    _noteUiState.value =
                        NoteState(
                            noteDetails = note.toNoteDetails(),
                            isInputValid = validateInput(note.toNoteDetails())
                        )
                    }
            }
    }

    fun loadNewNote() {
        _noteUiState.value = NoteState()
    }

    fun updateNote(noteUiState: NoteState) {
        with(noteUiState) {
            if (validateInput(noteDetails))
                viewModelScope.launch {
                    forNotesRepository.updateNote(noteDetails.toNote())
                }
        }
    }

    fun deleteNote(noteUiState: NoteState) {
        with(noteUiState) {
            viewModelScope.launch {
                forNotesRepository.deleteNote(noteDetails.toNote())
            }
        }
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
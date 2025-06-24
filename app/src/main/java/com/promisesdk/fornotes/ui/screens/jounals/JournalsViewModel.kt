package com.promisesdk.fornotes.ui.screens.jounals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Journal
import com.promisesdk.fornotes.data.JournalType
import com.promisesdk.fornotes.data.JournalWithEntries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class JournalsViewModel(
    forNotesRepository: ForNotesRepository
): ViewModel() {

    val journalHomeUiState: StateFlow<JournalHomeUiState> =
        forNotesRepository.getAllJournals().map { JournalHomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = JournalHomeUiState()
            )

}

data class JournalState(
    val journal: JournalWithEntries = JournalWithEntries(
        journal = Journal(
            id = 0,
            creationTimeInMillis = 0,
            title = "",
            description = "",
            noOfEntries = 0,
            type = JournalType.Custom
        ),
        entries = emptyList()
    )
)

data class JournalHomeUiState(
    val journals: List<JournalWithEntries> = listOf()
)
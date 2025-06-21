package com.promisesdk.fornotes.ui.screens.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.TodoWithItems
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodosViewModel (
    val forNotesRepository: ForNotesRepository
): ViewModel() {

    val todoHomeUiState: StateFlow<TodoHomeUiState> =
        forNotesRepository.getAllTodos().map { TodoHomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TodoHomeUiState()
            )

}

data class TodoHomeUiState(
    val todos: List<TodoWithItems> = emptyList()
)
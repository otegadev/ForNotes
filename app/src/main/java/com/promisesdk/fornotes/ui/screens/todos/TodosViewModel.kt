package com.promisesdk.fornotes.ui.screens.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Todo
import com.promisesdk.fornotes.data.TodoStatus
import com.promisesdk.fornotes.data.TodoWithItems
import kotlinx.coroutines.flow.Flow
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
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = TodoHomeUiState()
            )

    fun getTodoById(id: Int): Flow<TodoState>? {
        return forNotesRepository.getTodoById(id)?.map { TodoState(todo = it) }
    }
}

data class TodoState (
    val todo: TodoWithItems = TodoWithItems(
        todo = Todo(
            id = 0,
            title = "",
            label = null,
            status = TodoStatus.Pending,
            creationTimeInMillis = 0
        ),
        todoItems = emptyList()
    ),
)

data class TodoHomeUiState(
    val todos: List<TodoWithItems> = emptyList()
)
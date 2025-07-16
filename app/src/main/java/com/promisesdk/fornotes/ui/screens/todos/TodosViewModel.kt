package com.promisesdk.fornotes.ui.screens.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.promisesdk.fornotes.data.ForNotesLabels
import com.promisesdk.fornotes.data.ForNotesRepository
import com.promisesdk.fornotes.data.Todo
import com.promisesdk.fornotes.data.TodoStatus
import com.promisesdk.fornotes.data.TodoWithItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable

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

//    fun getTodoById(id: Int): Flow<TodoState>? {
//        return forNotesRepository.getTodoById(id)?.map { TodoState(todo = it) }
//    }
}

data class TodoHomeUiState(
    val todos: List<TodoWithItems> = emptyList()
)

//data class TodoState (
//
//)

data class TodoDetails(
    val id: Int = 0,
    val title: String = "",
    val label: ForNotesLabels? = null,
    val status: TodoStatus = TodoStatus.Pending,
    val creationTimeInMillis: Long = System.currentTimeMillis()
)

data class TodoItemDetails(
    val id: Int = 0,
    val todoId: Int,
    var primaryText: String,
    val secondaryTexts: List<String> = emptyList(),
    var isChecked: Boolean = false,
    val priority: String? = null,
)

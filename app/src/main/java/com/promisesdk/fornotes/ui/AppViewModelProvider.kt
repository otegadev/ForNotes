package com.promisesdk.fornotes.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.promisesdk.fornotes.ForNotesApplication
import com.promisesdk.fornotes.ui.screens.jounals.JournalsViewModel
import com.promisesdk.fornotes.ui.screens.notes.NotesViewModel
import com.promisesdk.fornotes.ui.screens.todos.TodosViewModel

class AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            NotesViewModel(
                forNotesRepository = forNotesApplication().container.forNotesRepository
            )
        }

        initializer {
            JournalsViewModel(
                forNotesRepository = forNotesApplication().container.forNotesRepository
            )
        }

        initializer {
            TodosViewModel(
                forNotesRepository = forNotesApplication().container.forNotesRepository
            )
        }
    }
}

fun CreationExtras.forNotesApplication(): ForNotesApplication {
    return (this[AndroidViewModelFactory.APPLICATION_KEY] as ForNotesApplication)
}
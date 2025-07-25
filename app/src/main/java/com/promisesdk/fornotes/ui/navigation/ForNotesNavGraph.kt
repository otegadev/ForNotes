package com.promisesdk.fornotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.promisesdk.fornotes.ui.AppViewModelProvider
import com.promisesdk.fornotes.ui.screens.jounals.JournalsHome
import com.promisesdk.fornotes.ui.screens.jounals.JournalsViewModel
import com.promisesdk.fornotes.ui.screens.notes.NoteEditScreen
import com.promisesdk.fornotes.ui.screens.notes.NoteState
import com.promisesdk.fornotes.ui.screens.notes.NotesHome
import com.promisesdk.fornotes.ui.screens.notes.NotesViewModel
import com.promisesdk.fornotes.ui.screens.todos.TodosHome
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.Screen
import kotlinx.serialization.Serializable

@Serializable
object Notes

@Serializable
object Todos

@Serializable
object Journals

@Serializable
data class EditNote(val id: Int)

@Serializable
object Archive

@Serializable
object Trash

@Serializable
object Settings


@Composable
fun ForNotesNavigationHost(
    windowSize: ForNotesWindowSize
) {
    val navController: NavHostController = rememberNavController()
    var currentScreen: MutableState<Screen> = remember { mutableStateOf(Screen.NotesScreen) }

    val coroutineScope = rememberCoroutineScope()

    val journalsViewModel: JournalsViewModel = viewModel(factory = AppViewModelProvider().factory)
    val journalHomeUiState = journalsViewModel.journalHomeUiState.collectAsState()

    val notesViewModel: NotesViewModel = viewModel(factory = AppViewModelProvider().factory)
    val notesHomeUiState = notesViewModel.notesHomeUiState.collectAsState()
    var noteUiState = notesViewModel.noteUiState.collectAsState()

    //val todosViewModel: TodosViewModel = viewModel(factory = AppViewModelProvider().factory)
    //val todosHomeUiState = todosViewModel.todoHomeUiState.collectAsState()
    //var todoId by rememberSaveable { mutableIntStateOf(0) }
    //var todos = todosViewModel.getTodoById(todoId)?.collectAsState(initial = TodoState())

    NavHost(
        navController = navController,
        startDestination = Notes
    ) {
        composable<Notes> {
            NotesHome(
                notes = notesHomeUiState.value.notes,
                windowSize = windowSize,
                onNavItemClick = {
                    currentScreen.value = Screen.NotesScreen
                    navItemNavigation(
                        clickedScreen = it,
                        navHostController = navController
                    )
                },
                onNoteClick = {
                    navController.navigate(EditNote(it))
                },
                onFabClick = {
                    navController.navigate(EditNote(id = 0))
                }
            )
        }
        composable<Todos> {
            TodosHome(
                todos = emptyList(),
                windowSize = windowSize,
                onNavItemClick = {
                    currentScreen.value = Screen.TodoScreen
                    navItemNavigation(
                        clickedScreen = it,
                        navHostController = navController
                    )
                },

            )
        }
        composable<Journals> {
            JournalsHome(
                journals = journalHomeUiState.value.journals,
                windowSize = windowSize,
                onNavItemClick = {
                    currentScreen.value = Screen.JournalScreen
                    navItemNavigation(
                        clickedScreen = it,
                        navHostController = navController
                    )
                },
            )
        }
        composable<EditNote> { backStackEntry ->
            val args = backStackEntry.toRoute<EditNote>()
            val noteIdFromNav = args.id
            LaunchedEffect(noteIdFromNav) {
                if (noteIdFromNav != 0) {
                    notesViewModel.loadExistingNote(noteIdFromNav)
                } else {
                    notesViewModel.loadNewNote()
                }
            }
            val noteState: NoteState = noteUiState.value
            NoteEditScreen(
                noteState = noteState,
                onValueChange = notesViewModel::updateUiState,
                onBackPress = {
                    if (noteIdFromNav == 0) {
                        notesViewModel.addNote(noteState)
                    } else {
                        notesViewModel.updateNote(noteState)
                    }
                    navController.popBackStack()
                    notesViewModel.resetUiState()
                },
                forNotesWindowSize = windowSize
            )
        }
    }
}

private fun navItemNavigation(
    clickedScreen: Screen,
    navHostController: NavHostController
) {
    when (clickedScreen) {
        Screen.NotesScreen -> {
            navHostController.navigate(Notes)
            navHostController.popBackStack()
        }
        Screen.TodoScreen -> {
            navHostController.navigate(Todos)
        }
        Screen.JournalScreen -> {
            navHostController.navigate(Journals)
        }
        Screen.ArchiveScreen -> {
            navHostController.navigate(Archive)
        }
        Screen.TrashScreen -> {
            navHostController.navigate(Trash)
        }
        Screen.SettingsScreens -> {
            navHostController.navigate(Settings)
        }
    }
}

package com.promisesdk.fornotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.promisesdk.fornotes.ui.screens.jounals.JournalsHome
import com.promisesdk.fornotes.ui.screens.notes.NotesHome
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
    NavHost(
        navController = navController,
        startDestination = Notes
    ) {
        composable<Notes> {
            NotesHome(
                notesList = emptyList(),
                windowSize = windowSize,
                onNavItemClick = {
                    currentScreen.value = Screen.NotesScreen
                    navItemNavigation(
                        clickedScreen = it,
                        navHostController = navController
                    )
                },
            )
        }
        composable<Todos> {
            TodosHome(
                todosList = emptyList(),
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
                journalsList = emptyList(),
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
    }

}

private fun navItemNavigation(
    clickedScreen: Screen,
    navHostController: NavHostController
) {
    when (clickedScreen) {
        Screen.NotesScreen -> {
            navHostController.navigate(Notes)
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

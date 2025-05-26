package com.promisesdk.fornotes.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.NotesData
import com.promisesdk.fornotes.data.TodosData
import com.promisesdk.fornotes.ui.screens.jounals.JournalsList
import com.promisesdk.fornotes.ui.screens.notes.NotesList
import com.promisesdk.fornotes.ui.screens.todos.TodosList
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.Screen
import com.promisesdk.fornotes.ui.utils.SearchResults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForNotesTopAppBar(
    onNavigationClick: () -> Unit,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    screen: Screen,
    searchResults: SearchResults,
    onResultClick: () -> Unit,
    modifier: Modifier = Modifier
) {
   val currentScreen: String? =
       if (screen == Screen.NotesScreen)  {
           stringResource(R.string.notes)
       } else if (screen == Screen.TodoScreen) {
           stringResource(R.string.todos)
       } else if(screen == Screen.JournalScreen) {
           stringResource(R.string.journals)
       } else {
           null
       }
   Row (modifier = modifier) {
       IconButton(
           onClick = onNavigationClick,
           modifier = Modifier
               .padding(dimensionResource(R.dimen.padding_small))
               .windowInsetsPadding(WindowInsets.statusBars),
           enabled = true,
       ) {
           Icon(
               imageVector = Icons.Rounded.Menu,
               contentDescription = stringResource(R.string.nav_drawer_description),
               modifier = Modifier
                   .size(48.dp),
               tint = MaterialTheme.colorScheme.onSurface
           )
       }
       SearchBar(
           inputField = {
               SearchBarDefaults.InputField(
                   query = searchBarQuery,
                   onQueryChange = onQueryChange,
                   onSearch = onSearch,
                   expanded = expanded,
                   onExpandedChange = onExpandedChange,
                   leadingIcon = {
                           Icon(
                               imageVector = Icons.Rounded.Search,
                               contentDescription = stringResource(R.string.search_icon),
                               modifier = Modifier.size(32.dp)
                           )
                   },
                   placeholder = {
                       Text(
                           text = stringResource(R.string.search_placeholder, currentScreen.toString().lowercase()),
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.onSurfaceVariant
                       )
                   }
               )
           },
           expanded = expanded,
           onExpandedChange = onExpandedChange,
           modifier = Modifier.weight(1f),
           shape = SearchBarDefaults.inputFieldShape,
           windowInsets = SearchBarDefaults.windowInsets
       ) {
           when (searchResults) {
               is SearchResults.JournalsSearchResults ->
                   JournalsList(
                       journalList = searchResults.journals,
                       onJournalClick = { onResultClick() },
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                   )
               is SearchResults.NotesSearchResults ->
                   NotesList(
                       notesList = searchResults.notes,
                       onNoteClick = onResultClick,
                       contentPadding = PaddingValues(0.dp),
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                   )
               is SearchResults.TodosSearchResults ->
                   TodosList(
                       todosList = searchResults.todos,
                       onTodoClick = onResultClick,
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                   )
           }
       }
   }
}

@Preview (
    showSystemUi = true, showBackground = true, device = "id:pixel_9_pro_xl",
)
@Composable
fun ForNotesTopAppBarPreview() {
    ForNotesTheme (
        darkTheme = false
    ) {
        ForNotesTopAppBar(
            searchBarQuery = "",
            onQueryChange = {},
            onSearch = {},
            onNavigationClick = {},
            expanded = false,
            onExpandedChange = {},
            screen = Screen.JournalScreen,
            searchResults = SearchResults.NotesSearchResults(emptyList()),
            onResultClick = {},
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }

}
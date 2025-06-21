package com.promisesdk.fornotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.ForNotesLabels
import com.promisesdk.fornotes.ui.navigation.ForNotesBottomNavBar
import com.promisesdk.fornotes.ui.navigation.ForNotesNavDrawerContent
import com.promisesdk.fornotes.ui.navigation.ForNotesNavRail
import com.promisesdk.fornotes.ui.navigation.NavItem
import com.promisesdk.fornotes.ui.navigation.navItemList
import com.promisesdk.fornotes.ui.navigation.navRailItemExtras
import com.promisesdk.fornotes.ui.screens.jounals.JournalsList
import com.promisesdk.fornotes.ui.screens.notes.NotesList
import com.promisesdk.fornotes.ui.screens.todos.TodosList
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.EditScreenActions
import com.promisesdk.fornotes.ui.utils.Screen
import com.promisesdk.fornotes.ui.utils.SearchResults
import com.promisesdk.fornotes.ui.utils.bottomBarActions
import kotlinx.coroutines.launch

/**
 * Top app bar containing menu and search bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactTopAppBar(
    onNavigationClick: () -> Unit,
    screen: Screen,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
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
   Row (
       modifier = modifier,
   ) {
       IconButton(
           onClick = onNavigationClick,
           modifier = Modifier
               .padding(
                   top = dimensionResource(R.dimen.padding_small),
                   bottom = dimensionResource(R.dimen.padding_small),
                   start = dimensionResource(R.dimen.padding_very_small),
                   end = dimensionResource(R.dimen.padding_very_small),
               )
               .windowInsetsPadding(WindowInsets.statusBars),
           enabled = true,
       ) {
           Icon(
               imageVector = Icons.Rounded.Menu,
               contentDescription = stringResource(R.string.nav_drawer_description),
               modifier = Modifier
                   .size(40.dp),
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
           modifier = Modifier
               .weight(1f)
               .padding(
                   end = dimensionResource(R.dimen.padding_small)
               )
           ,
           shape = SearchBarDefaults.inputFieldShape,
           windowInsets = SearchBarDefaults.windowInsets
       ) {
           when (searchResults) {
               is SearchResults.JournalsSearchResults ->
                   JournalsList(
                       journals = emptyList(),
                       onJournalClick = { onResultClick() },
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                   )
               is SearchResults.NotesSearchResults ->
                   NotesList(
                       notes = searchResults.notes,
                       onNoteClick = onResultClick,
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                   )
               is SearchResults.TodosSearchResults ->
                   TodosList(
                       todos = searchResults.todos,
                       onTodoClick = onResultClick,
                       modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                   )
           }
       }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBar(
    navItems: List<NavItem>,
    onNavItemClick: (Screen) -> Unit,
    screen: Screen,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
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
    Row (
        modifier = modifier,
    ) {
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
            modifier = Modifier
                .weight(0.5f)
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small)
                )
            ,
            shape = SearchBarDefaults.inputFieldShape,
            windowInsets = SearchBarDefaults.windowInsets
        ) {
            when (searchResults) {
                is SearchResults.JournalsSearchResults ->
                    JournalsList(
                        journals = emptyList(),
                        onJournalClick = { onResultClick() },
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    )
                is SearchResults.NotesSearchResults ->
                    NotesList(
                        notes = searchResults.notes,
                        onNoteClick = onResultClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    )
                is SearchResults.TodosSearchResults ->
                    TodosList(
                        todos = searchResults.todos,
                        onTodoClick = onResultClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    )
            }
        }
        navItems.forEach { navItem ->
            Column (modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_very_small),
                )
                .windowInsetsPadding(WindowInsets.statusBars),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { onNavItemClick(navItem.screen) },
                    modifier = Modifier,
                    enabled = true
                ) {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(navItem.text),
                        modifier = Modifier
                            .size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = stringResource(navItem.text),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedTopAppBar(
    screen: Screen,
    searchBarQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
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
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = currentScreen.toString(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_very_small),
                )
                .windowInsetsPadding(WindowInsets.statusBars)
                .weight(0.5f)
            ,
            fontWeight = FontWeight.Bold
        )
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
            modifier = Modifier
                .weight(0.5f)
                .padding(
                    end = dimensionResource(R.dimen.padding_small)
                ),
            shape = SearchBarDefaults.inputFieldShape,
            windowInsets = SearchBarDefaults.windowInsets
        ) {
            when (searchResults) {
                is SearchResults.JournalsSearchResults ->
                    JournalsList(
                        journals = emptyList(),
                        onJournalClick = { onResultClick() },
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    )
                is SearchResults.NotesSearchResults ->
                    NotesList(
                        notes = searchResults.notes,
                        onNoteClick = onResultClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    )
                is SearchResults.TodosSearchResults ->
                    TodosList(
                        todos = searchResults.todos,
                        onTodoClick = onResultClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    )
            }
        }
    }
}

/**
 * General composable for notes, todos and journal home screens on compact devices
 */
@Composable
fun CompactHomeScreenLayout(
    screen: Screen,
    itemList: @Composable () -> Unit,
    onNavItemClick: (Screen) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val screenName = if (screen == Screen.NotesScreen)
        stringResource(R.string.notes)
        else if (screen == Screen.TodoScreen)
            stringResource(R.string.todos)
        else if (screen == Screen.JournalScreen)
            stringResource(R.string.journals)
        else
            null

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(thickness = 4.dp)
                ForNotesNavDrawerContent(
                    navDrawerItemList = navRailItemExtras,
                    onClick = onNavItemClick
                )
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true,
    ) {
        Scaffold (
            modifier = modifier,
            topBar = {
                CompactTopAppBar(
                    onNavigationClick = { scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                    },
                    searchBarQuery = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    screen = screen,
                    searchResults = SearchResults.NotesSearchResults(emptyList()),
                    onResultClick = {},
                    modifier = modifier
                )
            },
            floatingActionButton = {
                HomeScreenFab(
                    screen = screen,
                    onClick = onFabClick,
                    modifier = modifier
                )
            },
            bottomBar = {
                ForNotesBottomNavBar(
                    navItems = navItemList,
                    onClick = onNavItemClick,
                    modifier = Modifier,
                    currentScreen = screen
                )
            }
        ) { contentPadding ->
            Column (
                modifier = Modifier.padding(contentPadding)
            ) {
                Text(
                    text = screenName.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    fontWeight = FontWeight.Bold
                )
                itemList
            }

        }
    }
}

/**
 * Composable for notes, todos and journals screens on medium devices
 */
@Composable
fun MediumHomeScreenLayout(
    screen: Screen,
    itemGrid: @Composable () -> Unit,
    onNavItemClick: (Screen) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenName = if (screen == Screen.NotesScreen)
        stringResource(R.string.notes)
    else if (screen == Screen.TodoScreen)
        stringResource(R.string.todos)
    else if (screen == Screen.JournalScreen)
        stringResource(R.string.journals)
    else
        null


    Scaffold (
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                navItems = navRailItemExtras,
                onNavItemClick = onNavItemClick,
                screen = screen,
                searchBarQuery = "",
                onQueryChange = {},
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                searchResults = SearchResults.NotesSearchResults(emptyList()),
                onResultClick = {},
                modifier = modifier,
            )
        },
        floatingActionButton = {
            HomeScreenFab(
                screen = screen,
                onClick = onFabClick,
                modifier = modifier
            )
        },
        bottomBar = {
            ForNotesBottomNavBar(
                navItems = navItemList,
                onClick = onNavItemClick,
                modifier = Modifier,
                currentScreen = screen
            )
        }
    ) { contentPadding ->
        Column (
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                text = screenName.toString(),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                fontWeight = FontWeight.Bold
            )
            itemGrid
        }
    }
}


@Composable
fun ExpandedHomeScreenLayout(
    screen: Screen,
    itemGrid: @Composable () -> Unit,
    onNavItemClick: (Screen) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        ForNotesNavRail(
            currentScreen = screen,
            onClick = onNavItemClick,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        )
        Scaffold (
            topBar = {
                ExpandedTopAppBar(
                    screen = screen,
                    searchBarQuery = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    searchResults = SearchResults.NotesSearchResults(emptyList()),
                    onResultClick = {},
                    modifier = modifier,
                )
            },
            floatingActionButton = {
                HomeScreenFab(
                    screen = screen,
                    onClick = onFabClick,
                    modifier = modifier
                )
            },
        ) { contentPadding ->
            Column (modifier = Modifier.padding(contentPadding)) {
                itemGrid
            }

        }
    }
}

@Composable
fun HomeScreenFab(
    screen: Screen,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fabText = if (screen == Screen.NotesScreen)
        stringResource(R.string.create_note)
    else if (screen == Screen.TodoScreen)
        stringResource(R.string.create_todo)
    else if (screen == Screen.JournalScreen)
        stringResource(R.string.create_a_journal)
    else
        null
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = FloatingActionButtonDefaults.extendedFabShape,
        containerColor = containerColor,
        contentColor = contentColorFor(containerColor),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        ),
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
            )
            Text(
                text = fabText.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun Label(
    label: ForNotesLabels
) {
    Row(
        Modifier
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .background(color = label.color)
            .border(
                width = 1.dp,
                color = Color(0xFF000000),
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Icon(
            imageVector = label.icon,
            contentDescription = label.name,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = label.name,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            )
        )
    }
}


@Composable
fun SaveButton(
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small)),
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = stringResource(R.string.save),
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = stringResource(R.string.save),
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun EditDropDownMenu(
    menuOptions: List<EditScreenActions>,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
    ) {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .padding(dimensionResource((R.dimen.padding_small))),
            enabled = true,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = stringResource(R.string.top_bar_actions),
                modifier = Modifier
                    .size(28.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            menuOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(option.actionName),
                        )
                    },
                    onClick = {
                        option.onActionClick()
                        expanded = false
                    },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_very_small)),
                    leadingIcon = {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = stringResource(option.actionName)
                        )
                    },
                    enabled = true,
                )
            }
        }
    }
}


@Composable
fun EditBottomBar(
    inEditMode: Boolean = false,

) {
    val bottomBarActions = bottomBarActions

    AnimatedVisibility(
        visible = inEditMode,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.ime)
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.large
                    )
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomBarActions.forEach { action ->
                    IconButton(
                        onClick = action.onActionClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        modifier = Modifier
                            .padding(dimensionResource((R.dimen.padding_very_small))),
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = stringResource(action.actionName),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview (
    showSystemUi = true, showBackground = true, device = "spec:width=673dp,height=841dp",
)

@Composable
fun MediumAppBarLayoutPreview() {
    ForNotesTheme (
        darkTheme = false
    ) {
        MediumTopAppBar(
            navItems = navRailItemExtras,
            onNavItemClick = {},
            screen = Screen.NotesScreen,
            searchBarQuery = "",
            onQueryChange = {},
            onSearch = {},
            expanded = false,
            onExpandedChange = {},
            searchResults = SearchResults.NotesSearchResults(emptyList()),
            onResultClick = {},
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )
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
        CompactTopAppBar(
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

@Preview (
    showSystemUi = true, showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240",
)
@Composable
fun ExpandedAppBarLayoutPreview() {
    ForNotesTheme (
        darkTheme = false
    ) {
        ExpandedTopAppBar(
            screen = Screen.NotesScreen,
            searchBarQuery = "",
            onQueryChange = {},
            onSearch = {},
            expanded = false,
            onExpandedChange = {},
            searchResults = SearchResults.NotesSearchResults(emptyList()),
            onResultClick = {},
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )
    }
}
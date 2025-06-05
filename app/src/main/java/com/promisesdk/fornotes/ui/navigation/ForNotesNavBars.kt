package com.promisesdk.fornotes.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.AutoDelete
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.Screen

@Composable
fun ForNotesNavDrawerContent(
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier,
    navDrawerItemList: List<NavItem> = navRailItemExtras,
    currentScreen: Screen? = null,
) {
    Column (modifier = modifier) {
        for (navItem in navDrawerItemList) {
            NavigationDrawerItem(
                label = {Text(
                    text = stringResource(navItem.text)
                )},
                selected = currentScreen == navItem.screen,
                onClick = { onClick(navItem.screen) },
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(navItem.text)
                    )
                },
            )
        }
    }
}

@Composable
fun ForNotesNavRail(
    currentScreen: Screen,
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier,
    navItem: List<NavItem> = navItemList,
    navRailExtras: List<NavItem> = navRailItemExtras,
) {
    NavigationRail (modifier = modifier) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (navItem in navItem) {
                NavigationRailItem(
                    selected = currentScreen == navItem.screen,
                    onClick = { onClick(navItem.screen) },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = stringResource(navItem.text)
                        )
                    },
                    modifier = Modifier
                        .padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium),
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small))
                        //.weight(1f)
                    ,
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(navItem.text),
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    alwaysShowLabel = true
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            for (navItem in navRailExtras) {
                NavigationRailItem(
                    selected = currentScreen == navItem.screen,
                    onClick = { onClick(navItem.screen) },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = stringResource(navItem.text)
                        )
                    },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(navItem.text),
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }
}



@Composable
fun ForNotesBottomNavBar(
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier,
    navItems: List<NavItem> = navItemList,
    currentScreen: Screen,
) {
    NavigationBar (modifier = modifier) { 
        for (navItem in navItems) {
            NavigationBarItem(
                selected = currentScreen == navItem.screen,
                onClick = { onClick(navItem.screen) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(navItem.text)
                    )
                },
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
                enabled = true,
                label = {
                    Text(
                        text = stringResource(navItem.text),
                        style = MaterialTheme.typography.labelLarge                    )
                },
                alwaysShowLabel = true,
            )
        }
    }
}

val navItemList = listOf<NavItem>(
    NavItem(
        screen = Screen.NotesScreen,
        icon = Icons.AutoMirrored.Rounded.Notes,
        text = R.string.notes
    ),
    NavItem(
        screen = Screen.TodoScreen,
        icon = Icons.Rounded.Checklist,
        text = R.string.todos
    ),
    NavItem(
        screen = Screen.JournalScreen,
        icon = Icons.Rounded.Book,
        text = R.string.journals
    )

)

val navRailItemExtras = listOf<NavItem>(
    NavItem(
        screen = Screen.ArchiveScreen,
        icon = Icons.Rounded.Archive,
        text = R.string.archive
    ),
    NavItem(
        screen = Screen.TrashScreen,
        icon = Icons.Rounded.AutoDelete,
        text = R.string.trash
    ),
    NavItem(
        screen = Screen.SettingsScreens,
        icon = Icons.Rounded.Settings,
        text = R.string.settings
    )
)

data class NavItem (
    val screen: Screen,
    val icon: ImageVector,
    val text: Int
)

@Preview
@Composable
fun NavBarPreview() {
    ForNotesTheme (darkTheme = true) {
        ForNotesBottomNavBar(
            navItems = navItemList,
            onClick = {},
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            currentScreen = Screen.NotesScreen
        )
    }
}

@Preview
@Composable
fun NavRailPreview() {
    ForNotesTheme (darkTheme = true) {
        ForNotesNavRail(
            navItem = navItemList,
            navRailExtras = navRailItemExtras,
            currentScreen = Screen.NotesScreen,
            onClick = {},
        )
    }
}

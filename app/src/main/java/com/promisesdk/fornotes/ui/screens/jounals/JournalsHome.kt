package com.promisesdk.fornotes.ui.screens.jounals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.JournalsData
import com.promisesdk.fornotes.ui.ForNotesTopAppBar
import com.promisesdk.fornotes.ui.navigation.ForNotesBottomNavBar
import com.promisesdk.fornotes.ui.navigation.navItemList
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.JournalType
import com.promisesdk.fornotes.ui.utils.Screen
import com.promisesdk.fornotes.ui.utils.SearchResults


@Composable
fun JournalsHome(
    journalsList: List<JournalsData>,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        modifier = modifier,
        topBar = {
            ForNotesTopAppBar(
                onNavigationClick = {},
                searchBarQuery = "",
                onQueryChange = {},
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                screen = Screen.JournalScreen,
                searchResults = SearchResults.JournalsSearchResults(emptyList()),
                onResultClick = {},
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier,
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
                        text = "Create a journal",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        bottomBar = {
            ForNotesBottomNavBar(
                navItemList = navItemList,
                onClick = onNavItemClick,
                modifier = Modifier,
                currentScreen = Screen.JournalScreen
            )
        }
    ) { contentPadding ->
        Column (modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                text = stringResource(R.string.journals),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                fontWeight = FontWeight.Bold
            )
            JournalsList(
                journalList = journalsList,
                onJournalClick = {},
                modifier = modifier
            )
        }
    }
}

@Composable
fun JournalsList(
    journalList: List<JournalsData>,
    onJournalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = journalList,
            key = {journal -> journal.journalId}
        ) { journal ->
           JournalCard(
               journal = journal,
               journalType = JournalType.Diary,
               modifier = Modifier
                   .padding(8.dp)
                   .clickable(
                       onClick = onJournalClick,
                       onClickLabel = stringResource(
                           R.string.navigate_to_this_journal,
                           journal.journalName
                       )
                   )
           )
        }
    }
}

@Composable
fun JournalCard(
    journal: JournalsData,
    journalType: JournalType,
    modifier: Modifier = Modifier,
) {
    Card (modifier = modifier) {
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row () {
                Text(
                    text = journal.journalName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                //Spacer(modifier = Modifier.width(150.dp))
                Row(
                    Modifier
                        .clip(shape = MaterialTheme.shapes.extraLarge)
                        .background(color = journalType.color)
                        .border(
                            width = 1.dp,
                            color = Color(0xFF000000),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    ) {
                    Text(
                        text = journalType.journalTypeName,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_medium),)
                        )
                    }
            }
            Text(
                text = journal.journalDescription,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
            )
        }
    }

}

// --- Sample JournalsData Instance Creation ---
val sampleJournalEntry = JournalsData(
    journalId = 1, // If you have an ID field
    journalName = "My Daily Reflections",
    journalDescription = "A collection of thoughts, events, and feelings from each day. This space is for personal growth and understanding patterns in my life. I plan to write here every evening before bed.",
    journalType = JournalType.Diary.journalTypeName,
    journalContent = "This is a sample journal entry.",
    creationTimeInMillis = System.currentTimeMillis() // If you have a date field
)

@Preview(device = "id:pixel_9_pro")
@Composable
fun JournalCardPreview() {
    ForNotesTheme (darkTheme = true) {
        JournalCard(
            journal = sampleJournalEntry,
            journalType = JournalType.HabitTracker,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview (
    showSystemUi = true, device = "id:pixel_9"
)
@Composable
fun JournalsHomePreview() {
    ForNotesTheme (
        darkTheme = true
    ) {
        JournalsHome(
            journalsList = emptyList(),
            onNavItemClick = {},
            modifier = Modifier
                //.windowInsetsPadding(WindowInsets.statusBars)
        )
    }
}


package com.promisesdk.fornotes.ui.screens.jounals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.JournalsData
import com.promisesdk.fornotes.data.sampleJournalEntry
import com.promisesdk.fornotes.ui.CompactHomeScreenLayout
import com.promisesdk.fornotes.ui.ExpandedHomeScreenLayout
import com.promisesdk.fornotes.ui.MediumHomeScreenLayout
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.JournalType
import com.promisesdk.fornotes.ui.utils.Screen


@Composable
fun JournalsHome(
    journalsList: List<JournalsData>,
    windowSize: ForNotesWindowSize,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

    when (windowSize) {
        ForNotesWindowSize.Compact ->
            CompactHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemList = {
                    JournalsList(
                        journalList = journalsList,
                        onJournalClick = {},
                        modifier = modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                modifier = modifier,
            )
        ForNotesWindowSize.Medium ->
            MediumHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemGrid = {
                    JournalsGrid(
                        journalList = journalsList,
                        onJournalClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                modifier = modifier
            )
        ForNotesWindowSize.Expanded ->
            ExpandedHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemGrid = {
                    JournalsGrid(
                        journalList = journalsList,
                        onJournalClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                modifier = modifier
            )
    }

}

/**
 * Lazy Staggered Grid for journals
 */
@Composable
fun JournalsGrid(
    journalList: List<JournalsData>,
    onJournalClick: () -> Unit,
    windowSize: ForNotesWindowSize,
    modifier: Modifier = Modifier
) {
    val columnCount: StaggeredGridCells = when (windowSize) {
        ForNotesWindowSize.Compact -> StaggeredGridCells.Fixed(2)
        ForNotesWindowSize.Medium -> StaggeredGridCells.Fixed(2)
        ForNotesWindowSize.Expanded -> StaggeredGridCells.Fixed(3)
    }
    LazyVerticalStaggeredGrid(
        columns = columnCount,
        verticalItemSpacing = dimensionResource(R.dimen.padding_small),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items (
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


/**
 * Lazy list for journals
 */
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
            Row {
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
            windowSize = ForNotesWindowSize.Medium,
            onNavItemClick = {},
            modifier = Modifier
                //.windowInsetsPadding(WindowInsets.statusBars)
        )
    }
}


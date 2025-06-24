package com.promisesdk.fornotes.ui.screens.jounals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.Journal
import com.promisesdk.fornotes.data.JournalType
import com.promisesdk.fornotes.data.JournalWithEntries
import com.promisesdk.fornotes.data.sampleJournalEntry
import com.promisesdk.fornotes.ui.CompactHomeScreenLayout
import com.promisesdk.fornotes.ui.ExpandedHomeScreenLayout
import com.promisesdk.fornotes.ui.MediumHomeScreenLayout
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsHome(
    journals: List<JournalWithEntries>,
    windowSize: ForNotesWindowSize,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
       skipPartiallyExpanded = false
    )
    var dropDownExpanded by rememberSaveable { mutableStateOf(false) }
    var journalName by rememberSaveable { mutableStateOf("") }
    var journalDescription : String? by rememberSaveable { mutableStateOf(null) }
    val journalTypes = JournalType.entries.toList()
    var journalType by rememberSaveable { mutableStateOf(journalTypes[0]) }


    val fabAction = { showBottomSheet = true }
    when (windowSize) {
        ForNotesWindowSize.Compact ->
            CompactHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemList = {
                    JournalsList(
                        journals = journals,
                        onJournalClick = {},
                        modifier = modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = fabAction,
                //showBottomSheet = showBottomSheet,
                modifier = modifier,
            )
        ForNotesWindowSize.Medium ->
            MediumHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemGrid = {
                    JournalsGrid(
                        journals = journals,
                        onJournalClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier
            )
        ForNotesWindowSize.Expanded ->
            ExpandedHomeScreenLayout(
                screen = Screen.JournalScreen,
                itemGrid = {
                    JournalsGrid(
                        journals = journals,
                        onJournalClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier
            )
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.journal_name),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_very_small)
                )
            )
                OutlinedTextField(
                    value = journalName,
                    onValueChange = {
                        journalName = it
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.journal_name),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small))
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.journal_description),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_very_small)
                )
            )
                OutlinedTextField(
                    value = journalDescription ?: "",
                    onValueChange = {
                        journalDescription = it
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.journal_description),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small))
                        .fillMaxWidth()
            )

                Text(
                    text = stringResource(R.string.journal_type),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_very_small)
                    )
                )
                ExposedDropdownMenuBox(
                    expanded = dropDownExpanded,
                    onExpandedChange = { dropDownExpanded = it },
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small))
                ) {
                    OutlinedTextField(
                        value = journalType.journalTypeName,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        trailingIcon = {
                            IconButton(
                                onClick = { dropDownExpanded = true },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector =
                                        if (dropDownExpanded) Icons.Rounded.ExpandLess
                                        else Icons.Rounded.ExpandMore,
                                    contentDescription =
                                        if (dropDownExpanded) stringResource(R.string.expand_less)
                                        else stringResource(R.string.expand_more),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        journalTypes.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = type.journalTypeName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                onClick = {
                                    journalType = type
                                    dropDownExpanded = false
                                },
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(R.dimen.padding_small),
                                    vertical = dimensionResource(R.dimen.padding_very_small)
                                )
                            )
                        }
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {},
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = stringResource(R.string.create_journal),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Lazy Staggered Grid for journals
 */
@Composable
fun JournalsGrid(
    journals: List<JournalWithEntries>,
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
            items = journals,
            key = {journalWithEntries -> journalWithEntries.journal.id}
        ) { journalWithEntries ->
            JournalCard(
                journal = journalWithEntries.journal,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        onClick = onJournalClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_journal,
                            journalWithEntries.journal.title
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
    journals: List<JournalWithEntries>,
    onJournalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = journals,
            key = {journalWithEntries -> journalWithEntries.journal.id}
        ) { journalWithEntries ->
           JournalCard(
               journal = journalWithEntries.journal,
               modifier = Modifier
                   .padding(8.dp)
                   .clickable(
                       onClick = onJournalClick,
                       onClickLabel = stringResource(
                           R.string.navigate_to_this_journal,
                           journalWithEntries.journal.title
                       )
                   )
           )
        }
    }
}

@Composable
fun JournalCard(
    journal: Journal,
    modifier: Modifier = Modifier,
) {
    Card (modifier = modifier) {
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row {
                Text(
                    text = journal.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                //Spacer(modifier = Modifier.width(150.dp))
                Row(
                    Modifier
                        .clip(shape = MaterialTheme.shapes.extraLarge)
                        .background(color = journal.type.color)
                        .border(
                            width = 1.dp,
                            color = Color(0xFF000000),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    ) {
                    Text(
                        text = journal.type.journalTypeName,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_medium),)
                        )
                    }
            }
            Text(
                text = journal.description,
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
            journals = emptyList(),
            windowSize = ForNotesWindowSize.Compact,
            onNavItemClick = {},
            modifier = Modifier
                //.windowInsetsPadding(WindowInsets.statusBars)
        )
    }
}


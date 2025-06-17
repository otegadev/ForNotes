package com.promisesdk.fornotes.ui.screens.jounals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.promisesdk.fornotes.data.Entry
import com.promisesdk.fornotes.data.JournalWithEntries
import com.promisesdk.fornotes.data.sampleEntry
import com.promisesdk.fornotes.data.sampleJournalEntry
import com.promisesdk.fornotes.data.sampleJournalWithEntries
import com.promisesdk.fornotes.ui.EditDropDownMenu
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.defaultTopBarActions
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryScreen(
    journalWithEntries: JournalWithEntries,
    windowSize: ForNotesWindowSize,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = {
            EntryScreenTopBar(
                journalName = journalWithEntries.journal.name,
                windowSize = windowSize,
                onBackPress = onBackPress,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .fillMaxWidth()

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
                        text = stringResource(R.string.add_entry),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) { contentPadding ->
        EntryScreenContent(
            journalWithEntries = journalWithEntries,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenTopBar(
    journalName: String,
    windowSize: ForNotesWindowSize,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var topBarActions = defaultTopBarActions

    var searchExpanded by rememberSaveable { mutableStateOf(false) }
    if (searchExpanded) {
        Row {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = {
                            Text(
                                text = stringResource(R.string.search_entries),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = { searchExpanded = false },
                                modifier = Modifier
                                    .padding(dimensionResource((R.dimen.padding_small))),
                                enabled = true,
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = stringResource(R.string.navigate_back),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    )
                },
                expanded = false,
                onExpandedChange = {},
                modifier = modifier,
                windowInsets = WindowInsets.statusBars
            ) { }
        }
    } else {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackPress,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource((R.dimen.padding_small))
                    ),
                enabled = true,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon (
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back),
                    modifier = Modifier.size(28.dp)
                )
            }
            Text(
                text = journalName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )
            if (windowSize == ForNotesWindowSize.Expanded || windowSize == ForNotesWindowSize.Medium) {
                Row (
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { searchExpanded = true },
                        modifier = Modifier
                            .padding(dimensionResource((R.dimen.padding_small)))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.navigate_back),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    topBarActions.forEach { action ->
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .padding(dimensionResource((R.dimen.padding_small))),
                            enabled = true,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Icon(
                                imageVector = action.icon,
                                contentDescription = stringResource(action.actionName),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            } else {
                Row (
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { searchExpanded = true },
                        modifier = Modifier
                            .padding(dimensionResource((R.dimen.padding_small)))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.navigate_back),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    EditDropDownMenu(
                        menuOptions = topBarActions
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryScreenContent(
    journalWithEntries: JournalWithEntries,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Row(
            Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .background(color = journalWithEntries.journal.type.color)
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = MaterialTheme.shapes.extraLarge
                ),
        ) {
            Text(
                text = journalWithEntries.journal.type.journalTypeName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_medium),)
            )
        }
        Text(
            text = stringResource(R.string.entries),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            fontWeight = FontWeight.Bold
        )
        LazyColumn { 
            items (
                items = journalWithEntries.entries,
                key = { entry -> entry.id }
            ) { entry ->
                EntryCard(
                    entry = entry,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryCard(
    entry: Entry,
    modifier: Modifier = Modifier
) {
    val datePattern = "MM-dd-yyyy"
    val timePattern = "HH:mm"
    Card (
        modifier = modifier
    ) {
        Column (
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row (
                modifier = Modifier
                    .padding( dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = entry.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Column {
                    Text(
                        text = formatDateFromMillis(
                            entry = entry,
                            pattern = datePattern
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatDateFromMillis(
                            entry = entry,
                            pattern = timePattern
                        ),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            Text(
                text = entry.content,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDateFromMillis(
    entry: Entry,
    pattern: String,
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val instant = Instant.ofEpochMilli(entry.creationTimeInMillis)
    val dateTime = LocalDateTime.ofInstant(instant, zoneId)
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return dateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun EntryCardPreview() {
    ForNotesTheme (darkTheme = true){
        EntryCard(
            entry = sampleEntry,
            modifier = Modifier.padding(
                8.dp
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview (
    showSystemUi = true, device = "id:pixel_9_pro"
)
@Composable
fun EntryScreenContentPreview() {
    ForNotesTheme (darkTheme = false) {
        EntryScreenContent(
            journalWithEntries = sampleJournalWithEntries,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Preview (
    showBackground = true
)
@Composable
fun EntryScreenTopBarPreview() {
    ForNotesTheme {
        EntryScreenTopBar(
            journalName = sampleJournalEntry.name,
            windowSize = ForNotesWindowSize.Compact,
            onBackPress = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview (
    showSystemUi = true, device = "id:pixel_9_pro"
)
@Composable
fun EntryScreenPreview() {
    ForNotesTheme {
        EntryScreen(
            journalWithEntries = sampleJournalWithEntries,
            windowSize = ForNotesWindowSize.Compact,
            onBackPress = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}
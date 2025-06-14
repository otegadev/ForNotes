package com.promisesdk.fornotes.ui.screens.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.Note
import com.promisesdk.fornotes.data.sampleNote
import com.promisesdk.fornotes.data.sampleNotes
import com.promisesdk.fornotes.ui.CompactHomeScreenLayout
import com.promisesdk.fornotes.ui.ExpandedHomeScreenLayout
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.MediumHomeScreenLayout
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.Screen

@Composable
fun NotesHome(
    notesList: List<Note>,
    windowSize: ForNotesWindowSize,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    when (windowSize) {
        ForNotesWindowSize.Compact ->
            CompactHomeScreenLayout(
                screen = Screen.NotesScreen,
                itemList = {
                    NotesList(
                        notesList = notesList,
                        onNoteClick = {},
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier,
            )
        ForNotesWindowSize.Medium ->
            MediumHomeScreenLayout(
                screen = Screen.NotesScreen,
                itemGrid = {
                    NotesGrid(
                        notesList = notesList,
                        onNoteClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier
            )
        ForNotesWindowSize.Expanded ->
            ExpandedHomeScreenLayout(
                screen = Screen.NotesScreen,
                itemGrid = {
                    NotesGrid(
                        notesList = notesList,
                        onNoteClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier
            )
    }
}

/**
 * Lazy Staggered Grid for notes
 */
@Composable
fun NotesGrid(
    notesList: List<Note>,
    onNoteClick: () -> Unit,
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
        verticalItemSpacing = dimensionResource(R.dimen.padding_very_small),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_very_small)),
        modifier = modifier
    ) {
        items (
            items = notesList,
            key = {note -> note.id}
        ) { note ->
            NotesCard(
                notesData = note,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_very_small))
                    .clickable(
                        onClick = onNoteClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_note,
                            note.title
                        )
                    )
            )
        }
    }
}

/**
 * Notes List for compact screen size
 */
@Composable
fun NotesList(
    notesList: List<Note>,
    onNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = notesList,
            key = {note -> note.id}
        ) { note ->
            NotesCard(
                notesData = note,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        onClick = onNoteClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_note,
                            note.title
                        )
                    )
            )
        }
    }
}

/**
 * Notes card (Adaptive for all screens)
 */
@Composable
fun NotesCard(
    notesData: Note,
    modifier: Modifier = Modifier,
) {
    Card (modifier = modifier) {
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row {
                Text(
                    text = notesData.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                //Spacer(modifier = Modifier.width(150.dp))
                if (notesData.label != null) {
                    Label(
                        label = notesData.label
                    )
                }

            }
            Text(
                text = notesData.content,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}



@Preview (
    showSystemUi = true, device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
private fun NotesGridPreview() {
    ForNotesTheme {
        NotesGrid(
            notesList = sampleNotes,
            onNoteClick = {},
            windowSize = ForNotesWindowSize.Expanded, // You can change this to test different sizes
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}



@Preview
@Composable
private fun NotesItemCardPreview() {
    ForNotesTheme (
        darkTheme = true
    ) {
        NotesCard(
            notesData = sampleNote,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview (
    showSystemUi = true, device = "id:pixel_9_pro_xl"
)
@Composable
private fun NotesHomePreview() {
    ForNotesTheme (
        darkTheme = true
    ) {
        NotesHome(
            notesList = emptyList(),
            onNavItemClick = {},
            windowSize = ForNotesWindowSize.Medium,
        )
    }
}

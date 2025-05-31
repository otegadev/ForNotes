package com.promisesdk.fornotes.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.promisesdk.fornotes.data.NotesData
import com.promisesdk.fornotes.ui.HomeScreen
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesLabels
import com.promisesdk.fornotes.ui.utils.Screen

@Composable
fun NotesHome(
    notesList: List<NotesData>,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    HomeScreen(
        screen = Screen.NotesScreen,
        itemList = {
            NotesList(
                notesList = notesList,
                onNoteClick = {},
                modifier = modifier
            )
        },
        onNavItemClick = onNavItemClick,
        modifier = modifier,
    )
}

/**
 * Notes List for compact screen size
 */
@Composable
fun NotesList(
    notesList: List<NotesData>,
    onNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = notesList,
            key = {note -> note.noteId}
        ) { note ->
            NotesCard(
                notesData = note,
                hasLabel = true,
                label = ForNotesLabels.Personal,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        onClick = onNoteClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_note,
                            note.noteTitle
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
    notesData: NotesData,
    hasLabel: Boolean,
    label: ForNotesLabels,
    modifier: Modifier = Modifier,
) {
    Card (modifier = modifier) {
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row {
                Text(
                    text = notesData.noteTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                //Spacer(modifier = Modifier.width(150.dp))
                if (hasLabel) {
                    Row(
                        Modifier
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .background(color = label.color)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF000000),
                                shape = MaterialTheme.shapes.extraLarge
                            ),
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
                                end = dimensionResource(R.dimen.padding_small),
                            )
                        )
                    }
                }

            }
            Text(
                text = notesData.noteContent,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

val sampleNote = NotesData(
    noteId = 0,
    noteTitle = "Note 1: Lorem ipsum dolor smit",
    noteContent = "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit,",
    noteLabel = "School",
    creationTimeInMillis = 122
)

@Preview
@Composable
private fun NotesItemCardPreview() {
    ForNotesTheme (
        darkTheme = true
    ) {
        NotesCard(
            notesData = sampleNote,
            hasLabel = true,
            label = ForNotesLabels.Personal,
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
            onNavItemClick = {}
        )
    }
}

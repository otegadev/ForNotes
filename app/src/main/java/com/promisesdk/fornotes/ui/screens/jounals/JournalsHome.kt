package com.promisesdk.fornotes.ui.screens.jounals

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
import com.promisesdk.fornotes.data.JournalsData
import com.promisesdk.fornotes.data.TodoDataWithItems
import com.promisesdk.fornotes.ui.screens.todos.TodoCard
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesLabels
import com.promisesdk.fornotes.ui.utils.JournalType

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
                       onClickLabel = stringResource(R.string.navigate_to_this_journal, journal.journalName)
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

@Preview
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


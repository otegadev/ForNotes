package com.promisesdk.fornotes.ui.screens.notes

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.Note
import com.promisesdk.fornotes.data.sampleNote
import com.promisesdk.fornotes.ui.EditBottomBar
import com.promisesdk.fornotes.ui.EditDropDownMenu
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.SaveButton
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.defaultTopBarActions

@Composable
fun NoteEditScreen(
    notesData: Note,
    onBackPress: () -> Unit,
    forNotesWindowSize: ForNotesWindowSize
) {

    var interactionSource = rememberSaveable { MutableInteractionSource() }
    var isFocused = interactionSource.collectIsFocusedAsState()

    Scaffold (
        topBar = {
            NotesEditTopAppBar(
                onBackPress = onBackPress,
                windowSize = forNotesWindowSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        bottomBar = {
           EditBottomBar(
                inEditMode = isFocused.value
           )
        }
    ) { contentPadding ->
        NotesEditTextArea(
            notesData = notesData,
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(contentPadding)

        )
    }
}

@Composable
fun NotesEditTopAppBar(
    onBackPress: () -> Unit,
    windowSize: ForNotesWindowSize,
    modifier: Modifier = Modifier
) {
    val topBarActions = defaultTopBarActions

    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackPress,
            modifier = Modifier
                .padding(dimensionResource((R.dimen.padding_small))),
                //.weight(0.2f)
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
        if (windowSize == ForNotesWindowSize.Expanded || windowSize == ForNotesWindowSize.Medium) {
            Row {
                topBarActions.forEach { action ->
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(dimensionResource((R.dimen.padding_small))),
                        enabled = true,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = stringResource(action.actionName),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                SaveButton(
                    onClick = {}
                )
            }
        } else {
            Row {
                SaveButton(
                    onClick = {}
                )
                EditDropDownMenu(
                    menuOptions = topBarActions,
                )
            }
        }

    }
}

@Composable
fun NotesEditTextArea (
    notesData: Note,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_very_small))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = notesData.title,
                onValueChange = {
                    notesData.title = it
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.padding_very_small)),
                interactionSource = interactionSource
            )
            if (notesData.label != null) {
                Label(
                    label = notesData.label
                )
            }
        }
        TextField(
            value = notesData.content,
            onValueChange = {
                notesData.content = it
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_very_small),
                    end = dimensionResource(R.dimen.padding_very_small)
                )
                .fillMaxSize()
                .weight(1f)
        )
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun NotesEditTopBarPreview() {
    ForNotesTheme {
        NotesEditTopAppBar(
            onBackPress = { },
            windowSize = ForNotesWindowSize.Compact,
            Modifier.fillMaxWidth()
        )
    }
}

@Preview (
    showSystemUi = true
)
@Composable
fun NotesEditTextAreaPreview() {
    ForNotesTheme {
        NotesEditTextArea(
            notesData = sampleNote,
            interactionSource = rememberSaveable { MutableInteractionSource() }
        )
    }
}

@Preview (
    showSystemUi = true, device = "id:pixel_9_pro_xl"
)
@Composable
fun NotesEditScreenPreview(){
    ForNotesTheme (
        darkTheme = true
    ) {
        NoteEditScreen(
            notesData = sampleNote,
            onBackPress = { },
            forNotesWindowSize = ForNotesWindowSize.Compact
        )
    }
}
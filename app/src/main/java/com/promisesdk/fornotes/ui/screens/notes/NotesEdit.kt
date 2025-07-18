package com.promisesdk.fornotes.ui.screens.notes

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.ui.EditBottomBar
import com.promisesdk.fornotes.ui.EditDropDownMenu
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.SaveButton
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.EditScreenActions
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteState: NoteState,
    onValueChange: (NoteDetails) -> Unit,
    onBackPress: () -> Unit,
    forNotesWindowSize: ForNotesWindowSize
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()

    var showLabelPickerDialog by rememberSaveable { mutableStateOf(false) }
    val sheetState: SheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    BackHandler (
        enabled = true,
        onBack = onBackPress
    )
    Scaffold (
        topBar = {
            NotesEditTopAppBar(
                onBackPress = onBackPress,
                addLabelAction = { showLabelPickerDialog = true },
                onShareAction = {},
                moveToTrashAction = {},
                moveToArchiveAction = {},
                windowSize = forNotesWindowSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars),

            )
        },
        bottomBar = {
           EditBottomBar(
                inEditMode = isFocused.value
           )
        }
    ) { contentPadding ->
        NotesEditTextArea(
            noteDetails = noteState.noteDetails,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(contentPadding)

        )
    }

    if (showLabelPickerDialog) {

    }
}

@Composable
fun NotesEditTopAppBar(
    onBackPress: () -> Unit,
    addLabelAction: () -> Unit,
    onShareAction: () -> Unit,
    moveToTrashAction: () -> Unit,
    moveToArchiveAction: () -> Unit,
    windowSize: ForNotesWindowSize,
    modifier: Modifier = Modifier
) {
    val topBarActions = listOf(
        EditScreenActions(
            actionName = R.string.labels,
            icon = Icons.AutoMirrored.Rounded.Label,
            onActionClick = addLabelAction
        ),
        EditScreenActions(
            actionName = R.string.share,
            icon = Icons.Rounded.Share,
            onActionClick = onShareAction
        ),
        EditScreenActions(
            actionName = R.string.delete,
            icon = Icons.Rounded.Delete,
            onActionClick = moveToTrashAction
        ),
        EditScreenActions(
            actionName = R.string.move_archive,
            icon = Icons.Rounded.Archive,
            onActionClick = moveToArchiveAction
        )
    )

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
    noteDetails: NoteDetails,
    onValueChange: (NoteDetails) -> Unit,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
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
                value = noteDetails.title,
                onValueChange = {
                    onValueChange(noteDetails.copy(title = it))
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
                placeholder = {
                    Text(
                        text = "Title goes here",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    )
                },
                interactionSource = interactionSource
            )
            if (noteDetails.label != null) {
                Label(
                    label = noteDetails.label
                )
            }
        }
        TextField(
            value = noteDetails.content,
            onValueChange = {
                onValueChange(noteDetails.copy(content = it))
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
                .weight(1f),
            placeholder = {
                Text(
                    text = "Start typing....",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            interactionSource = interactionSource
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
            modifier = Modifier.fillMaxWidth(),
            addLabelAction = {},
            onShareAction = {},
            moveToTrashAction = {},
            moveToArchiveAction = {},
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
            noteDetails = NoteDetails(),
            onValueChange = {},
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
            noteState = NoteState(),
            onValueChange = {},
            onBackPress = { },
            forNotesWindowSize = ForNotesWindowSize.Compact
        )
    }
}
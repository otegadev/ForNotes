package com.promisesdk.fornotes.ui.screens.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material.icons.rounded.Share
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
import com.promisesdk.fornotes.ui.EditDropDownMenu
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.SaveButton
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.EditScreenActions
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize

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
            NotesEditBottomBar(
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
    val topBarActions = listOf(
        EditScreenActions(
            actionName = stringResource(R.string.labels),
            icon = Icons.AutoMirrored.Rounded.Label,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.share),
            icon = Icons.Rounded.Share,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.delete),
            icon = Icons.Rounded.Delete,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.move_archive),
            icon = Icons.Rounded.Archive,
            onActionClick = {}
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
                            contentDescription = action.actionName,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                SaveButton()
            }
        } else {
            Row {
                SaveButton()
                EditDropDownMenu(
                    menuOptions = topBarActions,
                )
            }
        }

    }
}

@Composable
fun NotesEditBottomBar(
    inEditMode: Boolean = false,
) {
    val bottomBarActions: List<EditScreenActions> = listOf(
        EditScreenActions(
            actionName = stringResource(R.string.bold),
            icon = Icons.Rounded.FormatBold,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.italic),
            icon = Icons.Rounded.FormatItalic,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.underline),
            icon = Icons.Rounded.FormatUnderlined,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.cut),
            icon = Icons.Rounded.ContentCut,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.copy),
            icon = Icons.Rounded.ContentCopy,
            onActionClick = {}
        ),
        EditScreenActions(
            actionName = stringResource(R.string.paste),
            icon = Icons.Rounded.ContentPaste,
            onActionClick = {}
        ),
    )
    AnimatedVisibility(
        visible = inEditMode,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.ime)
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.large
                    )
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomBarActions.forEach { action ->
                    IconButton(
                        onClick = action.onActionClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        modifier = Modifier
                            .padding(dimensionResource((R.dimen.padding_very_small))),
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = action.actionName,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
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

@Preview
@Composable
fun NotesEditBottomBarPreview(){
    ForNotesTheme {
        NotesEditBottomBar(
            inEditMode = true
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
package com.promisesdk.fornotes.ui.screens.todos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.TodoWithItems
import com.promisesdk.fornotes.data.TodoItem
import com.promisesdk.fornotes.data.TodoStatus
import com.promisesdk.fornotes.data.sampleTodoDataWithItemsInstance
import com.promisesdk.fornotes.data.sampleTodoItems
import com.promisesdk.fornotes.ui.EditDropDownMenu
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.EditScreenActions
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize

@Composable
fun TodoEditScreen(
    todoWithItems: TodoWithItems,
    onBackPress: () -> Unit,
    forNotesWindowSize: ForNotesWindowSize,
) {

    Scaffold (
        topBar = {
            TodoEditTopBar(
                onBackPress = onBackPress,
                windowSize = forNotesWindowSize,
                todoStatus = todoWithItems.todo.status,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
            )
        }
    ) { contentPadding ->
        TodoEditContent(
            todoWithItems = todoWithItems,
            onValueChange = {},
            onItemCreate = {},
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
fun TodoEditTopBar (
    onBackPress: () -> Unit,
    windowSize: ForNotesWindowSize,
    todoStatus: TodoStatus,
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackPress,
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
        if (windowSize == ForNotesWindowSize.Expanded || windowSize == ForNotesWindowSize.Medium) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TodoStatus(
                    todoStatus = todoStatus,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
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
            }
        } else {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                TodoStatus(
                    todoStatus = todoStatus,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
                EditDropDownMenu(
                    menuOptions = topBarActions,
                )
            }
        }
    }

}

@Composable
fun TodoEditContent(
    todoWithItems: TodoWithItems,
    onValueChange: (String) -> Unit,
    onItemCreate: () -> Unit,
    modifier: Modifier
) {
    var interactionSource = rememberSaveable { MutableInteractionSource() }
    var isFocused = interactionSource.collectIsFocusedAsState()
    Column (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = todoWithItems.todo.title,
                onValueChange = onValueChange,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .weight(1f),
                textStyle =
                    MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
            )
            if (todoWithItems.todo.label != null) {
                Label(
                    label = todoWithItems.todo.label,
                )
            }
        }
        LazyColumn {
            items (
                items = todoWithItems.todoItems,
                key = { todoItem -> todoItem.id }
            ) { todoItem ->
                TodoItem(
                    todoItem = todoItem,
                    onCheckedChange = {},
                    onValueChange = {},
                    onDelete = {},
                    onFocus = isFocused.value,
                    interactionSource = interactionSource,
                )
            }
        }
        TextButton(
            onClick = onItemCreate,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.padding_medium)
            ),
            enabled = true
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add_todo),
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = stringResource(R.string.add_todo),
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todoItem: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
    onFocus: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todoItem.isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxColors(
                checkedCheckmarkColor = MaterialTheme.colorScheme.onPrimary,
                uncheckedCheckmarkColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkedBoxColor = MaterialTheme.colorScheme.primary,
                uncheckedBoxColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledCheckedBoxColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledUncheckedBoxColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledIndeterminateBoxColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                uncheckedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledBorderColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledUncheckedBorderColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                disabledIndeterminateBorderColor = MaterialTheme.colorScheme.surfaceContainerHighest
            ),
        )
        BasicTextField(
            value = todoItem.primaryText,
            onValueChange = onValueChange,
            textStyle =
                MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimatedVisibility(
            visible = onFocus
        ) {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(R.string.delete_todo)
                )
            }
        }

    }
}

@Composable
fun TodoStatus(
    todoStatus: TodoStatus,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Checklist,
            contentDescription = stringResource(todoStatus.status),
            tint = todoStatus.color,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_very_small))
        )
        Text(
            text = stringResource(todoStatus.status),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = todoStatus.color,
        )
    }
}

@Preview (
    showBackground = true
)
@Composable
fun TodoStatusPreview() {
    ForNotesTheme {
        TodoStatus(
            todoStatus = TodoStatus.Completed,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview (
    showBackground = true
)
@Composable
fun TodoItemPreview() {
    ForNotesTheme {
        TodoItem(
            todoItem = sampleTodoItems[1],
            onCheckedChange = {},
            onDelete = {},
            onValueChange = {},
            interactionSource = remember { MutableInteractionSource() },
            onFocus = true
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun TodoEditContentPreview() {
    ForNotesTheme {
        TodoEditContent(
            todoWithItems = sampleTodoDataWithItemsInstance,
            onValueChange = {},
            onItemCreate = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview (
    showBackground = true, device = "spec:width=673dp,height=841dp"
)
@Composable
fun TodoEditTopBarPreview() {
    ForNotesTheme {
        TodoEditTopBar(
            onBackPress = {},
            windowSize = ForNotesWindowSize.Expanded,
            todoStatus = TodoStatus.InProgress,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview (
    showSystemUi = true, device = "id:pixel_9_pro"
)
@Composable
fun TodoEditScreenPreview() {
    ForNotesTheme (
        darkTheme = true
    ) {
        TodoEditScreen(
            todoWithItems = sampleTodoDataWithItemsInstance,
            onBackPress = {},
            forNotesWindowSize = ForNotesWindowSize.Compact,
        )
    }
}
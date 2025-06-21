package com.promisesdk.fornotes.ui.screens.todos

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.TodoWithItems
import com.promisesdk.fornotes.data.sampleTodoDataWithItemsInstance
import com.promisesdk.fornotes.ui.CompactHomeScreenLayout
import com.promisesdk.fornotes.ui.ExpandedHomeScreenLayout
import com.promisesdk.fornotes.ui.Label
import com.promisesdk.fornotes.ui.MediumHomeScreenLayout
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize
import com.promisesdk.fornotes.ui.utils.Screen

@Composable
fun TodosHome(
    todos: List<TodoWithItems>,
    windowSize: ForNotesWindowSize,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

    when (windowSize) {
        ForNotesWindowSize.Compact ->
            CompactHomeScreenLayout(
                screen = Screen.TodoScreen,
                itemList = {
                    TodosList(
                        todos = todos,
                        onTodoClick = {},
                        modifier = modifier
                    )
                },
                onFabClick = {},
                onNavItemClick = onNavItemClick,
                modifier = modifier
            )
        ForNotesWindowSize.Medium ->
            MediumHomeScreenLayout(
                screen = Screen.TodoScreen,
                itemGrid = {
                    TodosGrid(
                        todos = todos,
                        onTodoClick = {},
                        windowSize = windowSize,
                        modifier = modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = Modifier
            )
        ForNotesWindowSize.Expanded ->
            ExpandedHomeScreenLayout(
                screen = Screen.TodoScreen,
                itemGrid = {
                    TodosGrid(
                        todos = todos,
                        onTodoClick = {},
                        windowSize = windowSize,
                        modifier = Modifier
                    )
                },
                onNavItemClick = onNavItemClick,
                onFabClick = {},
                modifier = modifier
            )
    }
}

/**
 * Lazy Staggered Grid for todos
 */
@Composable
fun TodosGrid(
    todos: List<TodoWithItems>,
    onTodoClick: () -> Unit,
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
            items = todos,
            key = {todo -> todo.todo.id}
        ) { todo ->
            TodoCard(
                todoWithItems = todo,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable(
                        onClick = onTodoClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_todo,
                            todo.todo.title
                        )
                    )
            )
        }
    }
}

/**
 * Todos List on home screen for compact screen size
 */
@Composable
fun TodosList(
    todos: List<TodoWithItems>,
    onTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = todos,
            key = {todo -> todo.todo.id}
        ) { todo ->
            TodoCard(
                todoWithItems = todo,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable(
                        onClick = onTodoClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_todo,
                            todo.todo.title
                        )
                    )
            )
        }
    }
}

@Composable
fun TodoCard(
    todoWithItems: TodoWithItems,
    modifier: Modifier = Modifier
) {
    val firstFiveItems = todoWithItems.todoItems.subList(0, 5)
    Card (modifier = modifier){
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row {
                Text(
                    text = todoWithItems.todo.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (todoWithItems.todo.label != null) {
                    Label(
                        label = todoWithItems.todo.label
                    )
                }

            }
            LazyColumn {
               items (
                   items = firstFiveItems,
                   key = {todo -> todo.id}
               ) { todo ->
                   Row {
                       Checkbox(
                           checked = todo.isChecked,
                           onCheckedChange = {todo.isChecked = !todo.isChecked},
                           enabled = true,
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
                       Text(
                           text = todo.primaryText,
                           style = MaterialTheme.typography.bodyLarge,
                           modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                       )
                   }
               }
            }
        }
    }
}

@Preview (
    showSystemUi = false
)
@Composable
fun TodoCardPreview() {
    ForNotesTheme (darkTheme = false) {
        TodoCard(
            todoWithItems = sampleTodoDataWithItemsInstance,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview (
    showSystemUi = true,
    device = "id:pixel_9_pro_xl"
)
@Composable
fun TodoHomePreview() {
    ForNotesTheme ( darkTheme = false ) {
        TodosHome(
            todos = emptyList(),
            windowSize = ForNotesWindowSize.Compact,
            onNavItemClick = {},
            modifier = Modifier
        )
    }
}



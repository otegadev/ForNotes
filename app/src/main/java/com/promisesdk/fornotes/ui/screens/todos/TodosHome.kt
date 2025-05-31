package com.promisesdk.fornotes.ui.screens.todos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.promisesdk.fornotes.R
import com.promisesdk.fornotes.data.TodoDataWithItems
import com.promisesdk.fornotes.data.TodoItem
import com.promisesdk.fornotes.data.TodosData
import com.promisesdk.fornotes.ui.HomeScreen
import com.promisesdk.fornotes.ui.theme.ForNotesTheme
import com.promisesdk.fornotes.ui.utils.ForNotesLabels
import com.promisesdk.fornotes.ui.utils.Screen

@Composable
fun TodosHome(
    todosList: List<TodoDataWithItems>,
    onNavItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    HomeScreen(
        screen = Screen.TodoScreen,
        itemList = {
            TodosList(
                todosList = todosList,
                onTodoClick = {},
                modifier = modifier
            )
        },
        onNavItemClick = onNavItemClick,
        modifier = modifier
    )
}

/**
 * Todos List on home screen for compact screen size
 */
@Composable
fun TodosList(
    todosList: List<TodoDataWithItems>,
    onTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = todosList,
            key = {todo -> todo.todosData.todoId}
        ) { todo ->
            TodoCard(
                todoData = todo,
                hasLabel = true,
                label = ForNotesLabels.Work,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable(
                        onClick = onTodoClick,
                        onClickLabel = stringResource(
                            R.string.navigate_to_this_todo,
                            todo.todosData.todoTitle
                        )
                    )
            )
        }
    }
}

@Composable
fun TodoCard(
    todoData: TodoDataWithItems,
    hasLabel: Boolean,
    label: ForNotesLabels,
    modifier: Modifier = Modifier
) {
    val firstFiveItems = todoData.todoItems.subList(0, 5)
    Card (modifier = modifier){
        Column (modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
            Row {
                Text(
                    text = todoData.todosData.todoTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
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
            LazyColumn {
               items (
                   items = firstFiveItems,
                   key = {todo -> todo.todoItemId}
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

val sampleTodoItems = listOf(
    TodoItem(
        todoItemId = 1, // Example ID, Room would auto-generate this in a real scenario
        todosDataId = 1, // Assuming this links to a TodoData with todoId = 1
        primaryText = "Buy groceries for the week",
        secondaryTexts = listOf("Milk", "Eggs", "Bread", "Cheese"),
        isChecked = false,
        priority = "High"
    ),
    TodoItem(
        todoItemId = 2,
        todosDataId = 1, // Also part of the same parent Todo list
        primaryText = "Finish the report for Project X",
        secondaryTexts = listOf("Include Q4 projections", "Proofread final draft"),
        isChecked = false,
        priority = "High"
    ),
    TodoItem(
        todoItemId = 3,
        todosDataId = 1,
        primaryText = "Schedule dentist appointment",
        isChecked = true, // This one is completed
        priority = "Medium"
    ),
    TodoItem(
        todoItemId = 4,
        todosDataId = 1, // Belongs to a different TodoData (e.g., todoId = 2)
        primaryText = "Call John about the weekend plan",
        secondaryTexts = emptyList(),
        isChecked = true,
        priority = "" // No specific priority
    ),
    TodoItem(
        todoItemId = 5,
        todosDataId = 1,
        primaryText = "Book flight tickets for vacation",
        secondaryTexts = listOf("Compare prices on different airlines"),
        isChecked = false,
        priority = "Medium"
    ),
    TodoItem(
        todoItemId = 6,
        todosDataId = 2, // Belongs to yet another TodoData (e.g., todoId = 3)
        primaryText = "Read a chapter of 'Atomic Habits'",
        isChecked = true,
        priority = "Low"
    ),
    TodoItem(
        todoItemId = 7,
        todosDataId = 2, // Belongs to yet another TodoData (e.g., todoId = 3)
        primaryText = "Read a chapter of 'Atomic Habits'",
        isChecked = true,
        priority = "Low"
    ),
)

val sampleTodosDataInstance = TodosData(
    todoId = 1, // Must match the todosDataId in the TodoItems we want to associate
    todoTitle = "Work Tasks for Monday",
    todoLabel = ForNotesLabels.Personal.name, // Example label, you might use your ForNotesLabels enum/class here
    todoStatus = "In Progress", // Example status
    creationTimeInMillis = System.currentTimeMillis() // Current time as an example
)

val sampleTodoDataWithItemsInstance = TodoDataWithItems(
    todosData = sampleTodosDataInstance,
    // Filter sampleTodoItems to get only those that belong to sampleTodosDataInstance
    todoItems = sampleTodoItems.filter { it.todosDataId == sampleTodosDataInstance.todoId }
)

@Preview (
    showSystemUi = false
)
@Composable
fun TodoCardPreview() {
    ForNotesTheme (darkTheme = false) {
        TodoCard(
            todoData = sampleTodoDataWithItemsInstance,
            hasLabel = true,
            label = ForNotesLabels.Work,
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
            todosList = emptyList(),
            onNavItemClick = {},
            modifier = Modifier
        )
    }
}



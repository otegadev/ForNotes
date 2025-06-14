package com.promisesdk.fornotes.data

import com.promisesdk.fornotes.ui.screens.todos.TodoStatus

// --- Sample JournalsData Instance Creation ---
val sampleJournalEntry = Journal(
    id = 1, // If you have an ID field
    name = "My Daily Reflections",
    description = "A collection of thoughts, events, and feelings from each day. This space is for personal growth and understanding patterns in my life. I plan to write here every evening before bed.",
    type = JournalType.Diary,
    entries = 100,
    creationTimeInMillis = System.currentTimeMillis() // If you have a date field
)

//Sample NotesData instance
val sampleNote = Note(
    id = 0,
    title = "Note 1: Lorem ipsum dolor smit",
    content = "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
            "Lorem ipsum dolor smit,Lorem ipsum dolor smit,",
    label = ForNotesLabels.School,
    creationTimeInMillis = 122
)

// Let's create a list of sample NotesData instances
val sampleNotes = List(10) { index ->
    Note(
        id = index,
        title = "Note ${index + 1}: Lorem ipsum dolor smit",
        content = if (index % 2 == 0)
            "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit,"
        else "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
                "Lorem ipsum dolor smit,Lorem ipsum dolor smit,",
        label = if (index % 2 == 0) ForNotesLabels.School else ForNotesLabels.Personal, // Alternating labels for variety
        creationTimeInMillis = (122 + index * 10).toLong() // Varying creation times
    )
}

val sampleTodoItems = listOf(
    TodoItem(
        id = 1, // Example ID, Room would auto-generate this in a real scenario
        todoId = 1, // Assuming this links to a TodoData with todoId = 1
        primaryText = "Buy groceries for the week",
        secondaryTexts = listOf("Milk", "Eggs", "Bread", "Cheese"),
        isChecked = false,
        priority = "High"
    ),
    TodoItem(
        id = 2,
        todoId = 1, // Also part of the same parent list
        primaryText = "Finish the report for Project X",
        secondaryTexts = listOf("Include Q4 projections", "Proofread final draft"),
        isChecked = false,
        priority = "High"
    ),
    TodoItem(
        id = 3,
        todoId = 1,
        primaryText = "Schedule dentist appointment",
        isChecked = true, // This one is completed
        priority = "Medium"
    ),
    TodoItem(
        id = 4,
        todoId = 1, // Belongs to a different TodoData (e.g., todoId = 2)
        primaryText = "Call John about the weekend plan",
        secondaryTexts = emptyList(),
        isChecked = true,
        priority = "" // No specific priority
    ),
    TodoItem(
        id = 5,
        todoId = 1,
        primaryText = "Book flight tickets for vacation",
        secondaryTexts = listOf("Compare prices on different airlines"),
        isChecked = false,
        priority = "Medium"
    ),
    TodoItem(
        id = 6,
        todoId = 2, // Belongs to yet another TodoData (e.g., todoId = 3)
        primaryText = "Read a chapter of 'Atomic Habits'",
        isChecked = true,
        priority = "Low"
    ),
    TodoItem(
        id = 7,
        todoId = 2, // Belongs to yet another TodoData (e.g., todoId = 3)
        primaryText = "Read a chapter of 'Atomic Habits'",
        isChecked = true,
        priority = "Low"
    ),
)

val sampleTodosDataInstance = Todo(
    id = 1, // Must match the todosDataId in the TodoItems we want to associate
    title = "Work Tasks for Monday",
    label = ForNotesLabels.Personal, // Example label, you might use your ForNotesLabels enum/class here
    status = TodoStatus.InProgress, // Example status
    creationTimeInMillis = System.currentTimeMillis() // Current time as an example
)

val sampleTodoDataWithItemsInstance = TodoWithItems(
    todo = sampleTodosDataInstance,
    // Filter sampleTodoItems to get only those that belong to sampleTodosDataInstance
    todoItems = sampleTodoItems.filter { it.todoId == sampleTodosDataInstance.id }
)
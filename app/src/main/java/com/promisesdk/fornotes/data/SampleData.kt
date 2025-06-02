package com.promisesdk.fornotes.data

import com.promisesdk.fornotes.ui.utils.ForNotesLabels
import com.promisesdk.fornotes.ui.utils.JournalType

// --- Sample JournalsData Instance Creation ---
val sampleJournalEntry = JournalsData(
    journalId = 1, // If you have an ID field
    journalName = "My Daily Reflections",
    journalDescription = "A collection of thoughts, events, and feelings from each day. This space is for personal growth and understanding patterns in my life. I plan to write here every evening before bed.",
    journalType = JournalType.Diary.journalTypeName,
    journalContent = "This is a sample journal entry.",
    creationTimeInMillis = System.currentTimeMillis() // If you have a date field
)

//Sample NotesData instance
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

// Let's create a list of sample NotesData instances
val sampleNotes = List(10) { index ->
    NotesData(
        noteId = index,
        noteTitle = "Note ${index + 1}: Lorem ipsum dolor smit",
        noteContent = if (index % 2 == 0)
            "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit," +
                    "Lorem ipsum dolor smit,Lorem ipsum dolor smit,"
        else "Lorem ipsum dolor smit, Lorem ipsum dolor smit," +
                "Lorem ipsum dolor smit,Lorem ipsum dolor smit,",
        noteLabel = if (index % 2 == 0) "School" else "Personal", // Alternating labels for variety
        creationTimeInMillis = (122 + index * 10).toLong() // Varying creation times
    )
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
        todosDataId = 1, // Also part of the same parent list
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
package com.promisesdk.fornotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ForNotesDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesData)

    @Update
    suspend fun updateNote(note: NotesData)

    @Delete
    suspend fun deleteNote(note: NotesData)

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    fun getAllNotes(): Flow<List<NotesData>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE '%' || :queryString || '%' " +
            "OR noteContent LIKE '%' || :queryString || '%' ORDER BY noteId DESC")
    fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<NotesData>>

    @Query("SELECT * FROM notes WHERE noteLabel = :queryString ORDER BY noteId DESC")
    fun getNoteByLabel(queryString: String): Flow<List<NotesData>>

    @Query("SELECT * FROM notes WHERE creationTimeInMillis = :queryString ORDER BY noteId DESC")
    fun getNoteByCreationTime(queryString: Long): Flow<List<NotesData>>

    @Query("SELECT * FROM notes WHERE noteId = :queryString")
    fun getNoteById(queryString: String): Flow<NotesData>


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodosData)

    @Update
    suspend fun updateTodo(todo: TodosData)

    @Delete
    suspend fun deleteTodo(todo: TodosData)

    @Transaction
    @Query("SELECT * FROM todos ORDER BY todoId DESC")
    fun getAllTodos(): Flow<List<TodoDataWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE todoTitle LIKE '%' || :queryString || '%' ORDER BY todoId DESC")
    fun getTodoByTodoTitle(queryString: String): Flow<List<TodoDataWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE todoLabel = :queryString ORDER BY todoId DESC")
    fun getTodoByLabel(queryString: String): Flow<List<TodoDataWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE todoStatus = :queryString ORDER BY todoId DESC")
    fun getTodoByStatus(queryString: String): Flow<List<TodoDataWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE creationTimeInMillis = :queryString ORDER BY todoId DESC")
    fun getTodoByCreationTime(queryString: Long): Flow<List<TodoDataWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE todoId = :queryString")
    fun getTodoById(queryString: String): Flow<TodoDataWithItems>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Query("SELECT * FROM todoItems WHERE todosDataId = :todosDataId ORDER BY todoItemId ASC")
    fun getAllTodoItems(todosDataId: Int): Flow<List<TodoItem>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalsData)

    @Update
    suspend fun updateJournal(journal: JournalsData)

    @Delete
    suspend fun deleteJournal(journal: JournalsData)

    @Query("SELECT * FROM journals ORDER BY journalId DESC")
    fun getAllJournals(): Flow<List<JournalsData>>

    @Query("SELECT * FROM journals WHERE journalName LIKE '%' || :queryString || '%'" +
            "OR journalContent LIKE '%' || :queryString || '%' ORDER BY journalId DESC")
    fun getJournalByJournalNameOrContent(queryString: String): Flow<List<JournalsData>>

    @Query("SELECT * FROM journals WHERE journalType = :queryString ORDER BY journalId DESC")
    fun getJournalByJournalType(queryString: String): Flow<List<JournalsData>>

    @Query("SELECT * FROM journals WHERE creationTimeInMillis = :queryString ORDER BY journalId DESC")
    fun getJournalByCreationTime(queryString: Long): Flow<List<JournalsData>>

    @Query("SELECT * FROM journals WHERE journalId = :queryString")
    fun getJournalById(queryString: String): Flow<JournalsData>
}
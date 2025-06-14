package com.promisesdk.fornotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ForNotesDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query(
        "SELECT * FROM notes WHERE title LIKE '%' || :queryString || '%' " +
                "OR content LIKE '%' || :queryString || '%' ORDER BY id DESC"
    )
    fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE label = :queryString ORDER BY id DESC")
    fun getNoteByLabel(queryString: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE creationTimeInMillis = :queryString ORDER BY id DESC")
    fun getNoteByCreationTime(queryString: Long): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :queryString")
    fun getNoteById(queryString: String): Flow<Note>


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Transaction
    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllTodos(): Flow<List<TodoWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE title LIKE '%' || :queryString || '%' ORDER BY id DESC")
    fun getTodoByTodoTitle(queryString: String): Flow<List<TodoWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE label = :queryString ORDER BY id DESC")
    fun getTodoByLabel(queryString: String): Flow<List<TodoWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE status = :queryString ORDER BY id DESC")
    fun getTodoByStatus(queryString: String): Flow<List<TodoWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE creationTimeInMillis = :queryString ORDER BY id DESC")
    fun getTodoByCreationTime(queryString: Long): Flow<List<TodoWithItems>>

    @Transaction
    @Query("SELECT * FROM todos WHERE id = :queryString")
    fun getTodoById(queryString: String): Flow<TodoWithItems>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Query("SELECT * FROM todoItems WHERE todoId = :todoId ORDER BY id ASC")
    fun getAllTodoItems(todoId: Int): Flow<List<TodoItem>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: Journal)

    @Update
    suspend fun updateJournal(journal: Journal)

    @Delete
    suspend fun deleteJournal(journal: Journal)

    @Query("SELECT * FROM journals ORDER BY id DESC")
    fun getAllJournals(): Flow<List<Journal>>

    @Query(
        "SELECT * FROM journals WHERE name LIKE '%' || :queryString || '%' ORDER BY id DESC"
    )
    fun getJournalByJournalName(queryString: String): Flow<List<Journal>>

    @Query("SELECT * FROM journals WHERE type = :queryString ORDER BY id DESC")
    fun getJournalByJournalType(queryString: String): Flow<List<Journal>>

    @Query("SELECT * FROM journals WHERE creationTimeInMillis = :queryString ORDER BY id DESC")
    fun getJournalByCreationTime(queryString: Long): Flow<List<Journal>>

    @Query("SELECT * FROM journals WHERE id = :id")
    fun getJournalById(id: Int): Flow<Journal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getEntryById(id: Int): Flow<Entry>

    @Query("SELECT * FROM entries WHERE name LIKE '%' || :name || '%' ORDER BY creationTimeInMillis DESC")
    fun getEntriesByName(name: String): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE creationTimeInMillis LIKE '%' || :creationTime || '%' ORDER BY id DESC")
    fun getEntriesByCreationTime(creationTime: Long): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE content LIKE '%'|| :content || '%' ORDER BY id DESC")
    fun getEntriesByContent(content: String): Flow<List<Entry>>
}
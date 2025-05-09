package com.promisesdk.fornotes.data

import kotlinx.coroutines.flow.Flow

interface ForNotesRepository {
    suspend fun insertNote(note: NotesData)

    suspend fun updateNote(note: NotesData)

    suspend fun deleteNote(note: NotesData)

    fun getAllNotes(): Flow<List<NotesData>>

    fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<NotesData>>

    fun getNoteByLabel(queryString: String): Flow<List<NotesData>>

    fun getNoteByCreationTime(queryString: Long): Flow<List<NotesData>>

    fun getNoteById(queryString: String): Flow<NotesData>

    suspend fun insertTodo(todo: TodosData)

    suspend fun updateTodo(todo: TodosData)

    suspend fun deleteTodo(todo: TodosData)

    fun getAllTodos(): Flow<List<TodoDataWithItems>>

    fun getTodoByTodoTitle(queryString: String): Flow<List<TodoDataWithItems>>

    fun getTodoByLabel(queryString: String): Flow<List<TodoDataWithItems>>

    fun getTodoByStatus(queryString: String): Flow<List<TodoDataWithItems>>

    fun getTodoByCreationTime(queryString: Long): Flow<List<TodoDataWithItems>>

    fun getTodoById(queryString: String): Flow<TodoDataWithItems>

    suspend fun insertTodoItem(todoItem: TodoItem)

    suspend fun updateTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(todoItem: TodoItem)

    fun getAllTodoItems(todosDataId: Int): Flow<List<TodoItem>>

    suspend fun insertJournal(journal: JournalsData)

    suspend fun updateJournal(journal: JournalsData)

    suspend fun deleteJournal(journal: JournalsData)

    fun getAllJournals(): Flow<List<JournalsData>>

    fun getJournalByJournalNameOrContent(queryString: String): Flow<List<JournalsData>>

    fun getJournalByJournalType(queryString: String): Flow<List<JournalsData>>

    fun getJournalByCreationTime(queryString: Long): Flow<List<JournalsData>>

    fun getJournalById(queryString: String): Flow<JournalsData>
}

class OfflineForNotesRepository(private val forNotesDao: ForNotesDao): ForNotesRepository {
    override suspend fun insertNote(note: NotesData) {
        forNotesDao.insertNote(note)
    }

    override suspend fun updateNote(note: NotesData) {
        forNotesDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NotesData) {
        forNotesDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<NotesData>> {
        return forNotesDao.getAllNotes()
    }

    override fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<NotesData>> {
        return forNotesDao.getNoteByNoteTitleOrContent(queryString)
    }

    override fun getNoteByLabel(queryString: String): Flow<List<NotesData>> {
        return forNotesDao.getNoteByLabel(queryString)
    }

    override fun getNoteByCreationTime(queryString: Long): Flow<List<NotesData>> {
        return forNotesDao.getNoteByCreationTime(queryString)
    }

    override fun getNoteById(queryString: String): Flow<NotesData> {
        return forNotesDao.getNoteById(queryString)
    }

    override suspend fun insertTodo(todo: TodosData) {
        forNotesDao.insertTodo(todo)
    }

    override suspend fun updateTodo(todo: TodosData) {
        forNotesDao.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodosData) {
        forNotesDao.deleteTodo(todo)
    }

    override fun getAllTodos(): Flow<List<TodoDataWithItems>> {
        return forNotesDao.getAllTodos()
    }

    override fun getTodoByTodoTitle(queryString: String): Flow<List<TodoDataWithItems>> {
        return forNotesDao.getTodoByTodoTitle(queryString)
    }

    override fun getTodoByLabel(queryString: String): Flow<List<TodoDataWithItems>> {
        return forNotesDao.getTodoByLabel(queryString)
    }

    override fun getTodoByStatus(queryString: String): Flow<List<TodoDataWithItems>> {
        return forNotesDao.getTodoByStatus(queryString)
    }

    override fun getTodoByCreationTime(queryString: Long): Flow<List<TodoDataWithItems>> {
        return forNotesDao.getTodoByCreationTime(queryString)
    }

    override fun getTodoById(queryString: String): Flow<TodoDataWithItems> {
        return forNotesDao.getTodoById(queryString)
    }

    override suspend fun insertTodoItem(todoItem: TodoItem) {
        forNotesDao.insertTodoItem(todoItem)
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        forNotesDao.updateTodoItem(todoItem)
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        forNotesDao.deleteTodoItem(todoItem)
    }

    override suspend fun insertJournal(journal: JournalsData) {
        forNotesDao.insertJournal(journal)
    }

    override fun getAllTodoItems(todosDataId: Int): Flow<List<TodoItem>> {
        return forNotesDao.getAllTodoItems(todosDataId)
    }

    override suspend fun updateJournal(journal: JournalsData) {
        forNotesDao.updateJournal(journal)
    }

    override suspend fun deleteJournal(journal: JournalsData) {
        forNotesDao.deleteJournal(journal)
    }

    override fun getAllJournals(): Flow<List<JournalsData>> {
        return forNotesDao.getAllJournals()
    }

    override fun getJournalByJournalNameOrContent(queryString: String): Flow<List<JournalsData>> {
        return forNotesDao.getJournalByJournalNameOrContent(queryString)
    }

    override fun getJournalByJournalType(queryString: String): Flow<List<JournalsData>> {
        return forNotesDao.getJournalByJournalType(queryString)
    }

    override fun getJournalByCreationTime(queryString: Long): Flow<List<JournalsData>> {
        return forNotesDao.getJournalByCreationTime(queryString)
    }

    override fun getJournalById(queryString: String): Flow<JournalsData> {
        return forNotesDao.getJournalById(queryString)
    }

}
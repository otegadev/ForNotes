package com.promisesdk.fornotes.data

import kotlinx.coroutines.flow.Flow

interface ForNotesRepository {
    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getAllNotes(): Flow<List<Note>>

    fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<Note>>

    fun getNoteByLabel(queryString: String): Flow<List<Note>>

    fun getNoteByCreationTime(queryString: Long): Flow<List<Note>>

    fun getNoteById(queryString: String): Flow<Note>

    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getAllTodos(): Flow<List<TodoWithItems>>

    fun getTodoByTodoTitle(queryString: String): Flow<List<TodoWithItems>>

    fun getTodoByLabel(queryString: String): Flow<List<TodoWithItems>>

    fun getTodoByStatus(queryString: String): Flow<List<TodoWithItems>>

    fun getTodoByCreationTime(queryString: Long): Flow<List<TodoWithItems>>

    fun getTodoById(queryString: String): Flow<TodoWithItems>

    suspend fun insertTodoItem(todoItem: TodoItem)

    suspend fun updateTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(todoItem: TodoItem)

    fun getAllTodoItems(todosDataId: Int): Flow<List<TodoItem>>

    suspend fun insertJournal(journal: Journal)

    suspend fun updateJournal(journal: Journal)

    suspend fun deleteJournal(journal: Journal)

    fun getAllJournals(): Flow<List<Journal>>

    fun getJournalByJournalNameOrContent(queryString: String): Flow<List<Journal>>

    fun getJournalByJournalType(queryString: String): Flow<List<Journal>>

    fun getJournalByCreationTime(queryString: Long): Flow<List<Journal>>

    fun getJournalById(id: Int): Flow<Journal>

    suspend fun insertEntry(entry: Entry)

    suspend fun updateEntry(entry: Entry)

    suspend fun deleteEntry(entry: Entry)

    fun getAllEntries(): Flow<List<Entry>>

    fun getEntryById(id : Int): Flow<Entry>

    fun getEntriesByName(name: String): Flow<List<Entry>>

    fun getEntriesByCreationTime(creationTime: Long): Flow<List<Entry>>

    fun getEntriesByContent(content: String): Flow<List<Entry>>
}

class OfflineForNotesRepository(private val forNotesDao: ForNotesDao): ForNotesRepository {
    override suspend fun insertNote(note: Note) {
        forNotesDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        forNotesDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        forNotesDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return forNotesDao.getAllNotes()
    }

    override fun getNoteByNoteTitleOrContent(queryString: String): Flow<List<Note>> {
        return forNotesDao.getNoteByNoteTitleOrContent(queryString)
    }

    override fun getNoteByLabel(queryString: String): Flow<List<Note>> {
        return forNotesDao.getNoteByLabel(queryString)
    }

    override fun getNoteByCreationTime(queryString: Long): Flow<List<Note>> {
        return forNotesDao.getNoteByCreationTime(queryString)
    }

    override fun getNoteById(queryString: String): Flow<Note> {
        return forNotesDao.getNoteById(queryString)
    }

    override suspend fun insertTodo(todo: Todo) {
        forNotesDao.insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        forNotesDao.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        forNotesDao.deleteTodo(todo)
    }

    override fun getAllTodos(): Flow<List<TodoWithItems>> {
        return forNotesDao.getAllTodos()
    }

    override fun getTodoByTodoTitle(queryString: String): Flow<List<TodoWithItems>> {
        return forNotesDao.getTodoByTodoTitle(queryString)
    }

    override fun getTodoByLabel(queryString: String): Flow<List<TodoWithItems>> {
        return forNotesDao.getTodoByLabel(queryString)
    }

    override fun getTodoByStatus(queryString: String): Flow<List<TodoWithItems>> {
        return forNotesDao.getTodoByStatus(queryString)
    }

    override fun getTodoByCreationTime(queryString: Long): Flow<List<TodoWithItems>> {
        return forNotesDao.getTodoByCreationTime(queryString)
    }

    override fun getTodoById(queryString: String): Flow<TodoWithItems> {
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

    override suspend fun insertJournal(journal: Journal) {
        forNotesDao.insertJournal(journal)
    }

    override fun getAllTodoItems(todosDataId: Int): Flow<List<TodoItem>> {
        return forNotesDao.getAllTodoItems(todosDataId)
    }

    override suspend fun updateJournal(journal: Journal) {
        forNotesDao.updateJournal(journal)
    }

    override suspend fun deleteJournal(journal: Journal) {
        forNotesDao.deleteJournal(journal)
    }

    override fun getAllJournals(): Flow<List<Journal>> {
        return forNotesDao.getAllJournals()
    }

    override fun getJournalByJournalNameOrContent(queryString: String): Flow<List<Journal>> {
        return forNotesDao.getJournalByJournalName(queryString)
    }

    override fun getJournalByJournalType(queryString: String): Flow<List<Journal>> {
        return forNotesDao.getJournalByJournalType(queryString)
    }

    override fun getJournalByCreationTime(queryString: Long): Flow<List<Journal>> {
        return forNotesDao.getJournalByCreationTime(queryString)
    }

    override fun getJournalById(id: Int): Flow<Journal> {
        return forNotesDao.getJournalById(id)
    }

    override suspend fun insertEntry(entry: Entry) {
        return forNotesDao.insertEntry(entry)
    }

    override suspend fun updateEntry(entry: Entry) {
        return forNotesDao.updateEntry(entry)
    }

    override suspend fun deleteEntry(entry: Entry) {
        return forNotesDao.deleteEntry(entry)
    }

    override fun getAllEntries(): Flow<List<Entry>> {
        return forNotesDao.getAllEntries()
    }

    override fun getEntryById(id: Int): Flow<Entry> {
        return forNotesDao.getEntryById(id)
    }

    override fun getEntriesByName(name: String): Flow<List<Entry>> {
        return forNotesDao.getEntriesByName(name)
    }

    override fun getEntriesByCreationTime(creationTime: Long): Flow<List<Entry>> {
        return forNotesDao.getEntriesByCreationTime(creationTime)
    }

    override fun getEntriesByContent(content: String): Flow<List<Entry>> {
        return forNotesDao.getEntriesByContent(content)
    }

}
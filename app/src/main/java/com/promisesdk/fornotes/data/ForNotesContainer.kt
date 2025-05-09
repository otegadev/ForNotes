package com.promisesdk.fornotes.data

import android.content.Context

interface ForNotesContainer {
    val forNotesRepository: ForNotesRepository
}

class DefaultForNotesContainer(context: Context): ForNotesContainer {
    override val forNotesRepository: ForNotesRepository by lazy {
        OfflineForNotesRepository(ForNotesDatabase.getDatabase(context).forNotesDao)
    }
}
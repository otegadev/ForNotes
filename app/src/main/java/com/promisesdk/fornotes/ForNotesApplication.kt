package com.promisesdk.fornotes

import android.app.Application
import com.promisesdk.fornotes.data.DefaultForNotesContainer
import com.promisesdk.fornotes.data.ForNotesContainer

class ForNotesApplication: Application() {
    lateinit var container: ForNotesContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultForNotesContainer(this)
    }
}
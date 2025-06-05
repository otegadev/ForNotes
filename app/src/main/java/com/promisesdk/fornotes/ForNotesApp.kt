package com.promisesdk.fornotes

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.promisesdk.fornotes.ui.navigation.ForNotesNavigationHost
import com.promisesdk.fornotes.ui.utils.ForNotesWindowSize

@Composable
fun ForNotesApp(
    windowSize: WindowWidthSizeClass
) {
    val windowSize = when (windowSize) {
        WindowWidthSizeClass.Compact -> ForNotesWindowSize.Compact
        WindowWidthSizeClass.Medium -> ForNotesWindowSize.Medium
        WindowWidthSizeClass.Expanded -> ForNotesWindowSize.Expanded
        else -> ForNotesWindowSize.Compact
    }

    ForNotesNavigationHost(
        windowSize = windowSize
    )
}
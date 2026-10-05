package com.alicanbatur.tmdb.presentation.components

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatterUtils {
    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val displayDateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    fun displayString(fromApiDate: String): String {
        val date = runCatching { apiDateFormat.parse(fromApiDate) }.getOrNull() ?: return fromApiDate
        return displayDateFormat.format(date)
    }
}

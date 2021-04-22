package com.example.trasparenciagov.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun String.toDate(patterns: String = "dd/MM/yyyy"): Date? {
    return try {
        val formatter = SimpleDateFormat(patterns, Locale.getDefault())
        formatter.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.toTextDate() = toDate("yyyy-MM-dd")?.toText("dd/MM/yyyy")

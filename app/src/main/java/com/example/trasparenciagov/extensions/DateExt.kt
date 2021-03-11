package com.example.trasparenciagov.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Date.toText(patterns: String = "dd/MM/yyyy"): String {
    return try {
        SimpleDateFormat(patterns, Locale.getDefault()).format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
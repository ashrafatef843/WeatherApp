package com.example.weatherapp.common.presentation.util

import com.example.weatherapp.common.const.ICON_URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return format.format(date)
}

fun String.toIconURL(): String {
   return "$ICON_URL$this.PNG"
}
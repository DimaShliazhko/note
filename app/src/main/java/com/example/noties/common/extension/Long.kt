package com.example.noties.common.extension

import java.text.SimpleDateFormat
import java.util.*


fun Long.toDate(dateFormat: String? = "dd/MM/yyyy hh:mm"): String {
    val formatter = SimpleDateFormat(dateFormat)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}
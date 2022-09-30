package com.example.noties.common.utils

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class AlarmUtils @Inject constructor(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun createAlarm(time: Long, title: String, id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(NOTE_TITLE, title)
            putExtra(NOTE_ID, id)
            action = ACTION
        }
        val pendingIntent = PendingIntent.getBroadcast(context, id.toInt(), intent, 0)
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, pendingIntent)
    }

    fun cancelAlarm( id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id.toInt(), intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}
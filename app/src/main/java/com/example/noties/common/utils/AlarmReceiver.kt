package com.example.noties.common.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver @Inject constructor(
) : BroadcastReceiver() {

    @Inject
    lateinit var notificationUtils: NotificationUtils
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(NOTE_TITLE)
        Intent(context, SpeechService::class.java).apply {
            putExtra(NOTE_TITLE, title)
            context.startService(this)
        }
        createNotification(intent)
    }

    private fun createNotification(intent: Intent) {
        val title = intent.getStringExtra(NOTE_TITLE)
        val noteId = intent.getLongExtra(NOTE_ID, -1)
        notificationUtils.crateInitialSync(title, noteId)
    }

}
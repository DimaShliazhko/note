package com.example.noties.common.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.example.noties.MainActivity
import com.example.noties.R
import com.example.noties.common.navigation.Screen
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationUtils @Inject constructor(
    private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    fun createNotificationChannels() {
        if (supportNotificationChannels()) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Use for count"
                }
            )
        }
    }

    fun crateInitialSync(title: String?, noteId: Long) {
        notificationManager.notify(
            noteId.toInt(),
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText(title)
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(openNote(noteId))
                .build()
        )
    }

    private fun openNote(noteId: Long): PendingIntent? {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "$MY_URI/$MY_ARG=$noteId".toUri(),
            context, MainActivity::class.java
        ).apply {
            putExtra(ROUTE, Screen.EditScreen.route)
        }
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return pendingIntent
    }

    private fun supportNotificationChannels(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val CHANNEL_NAME = "COUNT_ID"
    }
}
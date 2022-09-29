package com.example.noties.common.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.noties.R
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

    fun crateInitialSync() {
        notificationManager.notify(
            NOTIFICATION_ID,
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Notification")
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build()
        )
    }

    private fun supportNotificationChannels(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val CHANNEL_NAME = "COUNT_ID"
        const val NOTIFICATION_ID = 0
    }
}
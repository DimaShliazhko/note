package com.example.noties.common.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationUtils: NotificationUtils

    override fun onReceive(p0: Context?, p1: Intent?) {
        notificationUtils.crateInitialSync()
    }
}
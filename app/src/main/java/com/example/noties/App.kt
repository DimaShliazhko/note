package com.example.noties

import android.app.Application
import com.example.noties.common.utils.NotificationUtils
import com.example.noties.common.utils.exception.GlobalExceptionHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var notificationUtils: NotificationUtils

    override fun onCreate() {
        super.onCreate()
        notificationUtils.createNotificationChannels()
        GlobalExceptionHandler.initialize(this, MainActivity::class.java)
    }
}
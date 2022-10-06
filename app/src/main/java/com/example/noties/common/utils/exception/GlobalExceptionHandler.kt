package com.example.noties.common.utils.exception

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import java.lang.Thread.UncaughtExceptionHandler
import kotlin.system.exitProcess

class GlobalExceptionHandler private constructor(
    private val appContext: Context,
    private val defaultHandler: UncaughtExceptionHandler,
    private val activityToBeLaunched: Class<*>
) : UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            launchActivity(appContext, activityToBeLaunched, throwable)
            exitProcess(0)
        } catch (e: Exception) {
            defaultHandler.uncaughtException(thread, throwable)
        }
    }

    private fun launchActivity(context: Context, activityToBeLaunched: Class<*>, throwable: Throwable) {
        val crashIntent = Intent(context, activityToBeLaunched).also {
            it.putExtra(INTENT_DATA_NAME, Gson().toJson(throwable))
        }
        crashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        crashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(crashIntent)
    }

    companion object {
        private const val INTENT_DATA_NAME = "crash_data"

        fun initialize(
            appContext: Context,
            activityToBeLaunched: Class<*>
        ) {
            val handler = GlobalExceptionHandler(
                appContext,
                Thread.getDefaultUncaughtExceptionHandler() as UncaughtExceptionHandler,
                activityToBeLaunched
            )
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }

        fun getThrowableFromIntent(intent: Intent): Throwable? {
            return try {
                Gson().fromJson(intent.getStringExtra(INTENT_DATA_NAME), Throwable::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}
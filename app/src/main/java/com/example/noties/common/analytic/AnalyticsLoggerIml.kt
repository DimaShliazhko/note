package com.example.noties.common.analytic

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class AnalyticsLoggerIml : AnalyticsLogger, LifecycleEventObserver {

   private var route: String? = null
    override fun registrationLifecycleOwner(currentRoute: String?, lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
        route = currentRoute
    }

    override fun removeLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d("ActivityLifecycleDemo", "$route ON_CREATE")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d("ActivityLifecycleDemo", "$route ON_PAUSE")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d("ActivityLifecycleDemo", "$route ON_STOP")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.d("ActivityLifecycleDemo", "$route ON_RESUME")
            }
            else -> Unit
        }
    }
}
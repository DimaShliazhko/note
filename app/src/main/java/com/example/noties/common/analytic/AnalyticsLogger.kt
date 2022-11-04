package com.example.noties.common.analytic

import androidx.lifecycle.LifecycleOwner

interface AnalyticsLogger {
    fun registrationLifecycleOwner(currentRoute : String?, lifecycleOwner: LifecycleOwner)

    fun removeLifecycleOwner( lifecycleOwner: LifecycleOwner)}
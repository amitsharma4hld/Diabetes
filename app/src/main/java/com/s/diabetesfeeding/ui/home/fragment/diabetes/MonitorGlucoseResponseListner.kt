package com.s.diabetesfeeding.ui.home.fragment.diabetes

import com.s.diabetesfeeding.data.db.entities.Data

interface MonitorGlucoseResponseListner {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(message: String)
}
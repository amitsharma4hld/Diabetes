package com.s.diabetesfeeding.ui.home

import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.MonitorbloodGlucoseRepository
import com.s.diabetesfeeding.util.lazyDeferred

class MonitorBloodGlucoseViewModel(
    repository: MonitorbloodGlucoseRepository
): ViewModel() {
    val monitorbloodGlucose by lazyDeferred {
        repository.getMonitorbloodGlucose()
    }
    val symptom by lazyDeferred {
        repository.getSymptom()
    }
}
package com.s.diabetesfeeding.data.network.responses

import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.data.db.entities.Symptom

data class MonitorbloodGlucoseResponse(
    val monitorblood_glucose: List<List<MonitorbloodGlucose>>,
    val symptoms: List<List<Symptom>>
)
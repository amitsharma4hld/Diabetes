package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonitorbloodGlucose(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val range: String,
    val time: String,
    val title: String
   /* val monitorblood_glucose: List<List<MonitorbloodGlucoseX>>*/
    /* val symptoms: List<List<Symptom>> */
)
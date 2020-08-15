package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonitorbloodGlucose(
    val id: String,
    val range: String,
    val time: String,
    val title: String
    /*val monitorblood_glucose: List<List<MonitorbloodGlucoseX>>,
    val symptoms: List<List<Symptom>>*/
){
    @PrimaryKey(autoGenerate = true)
    var uid:Int = CURRENT_USER_ID
}
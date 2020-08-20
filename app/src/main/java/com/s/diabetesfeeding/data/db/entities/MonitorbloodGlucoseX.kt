package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonitorbloodGlucoseX(
    val id: String,
    val range: String,
    val time: String,
    val title: String
){
    @PrimaryKey(autoGenerate = true)
    var uid:Int = CURRENT_USER_ID
}
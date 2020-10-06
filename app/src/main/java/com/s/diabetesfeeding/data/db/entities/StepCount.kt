package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class StepCount (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val stepsCount:Int,
    val comment:String,
    var offsetDateTime: OffsetDateTime
)
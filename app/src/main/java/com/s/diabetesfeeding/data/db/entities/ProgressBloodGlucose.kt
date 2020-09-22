package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressBloodGlucose (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemsCatId:Int,
    val range: String,
    val time: String,
    val title: String,
    val value:String,
    val dateTime: OffsetDateTime,
    val day: String
)
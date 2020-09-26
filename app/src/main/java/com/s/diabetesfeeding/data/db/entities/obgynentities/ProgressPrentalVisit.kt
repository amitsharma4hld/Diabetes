package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressPrentalVisit (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val prentalVisiId:Int,
    val measurementOf: String,
    val value:String,
    val unit: String,
    val dateTime: OffsetDateTime,
    val day: String
)
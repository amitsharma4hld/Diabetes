package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressBreastFeeding (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var breastfeedingTime: String,
    var breastfeedingTimerCount: String,
    var breastfeedingType: String,
    var dateTime:OffsetDateTime
)
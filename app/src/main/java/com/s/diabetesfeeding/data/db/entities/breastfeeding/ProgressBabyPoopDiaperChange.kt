package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressBabyPoopDiaperChange (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var temId:Int,
    var poop_pee_time: String,
    var isPoop: Boolean,
    var isPee: Boolean,
    var dateTime: OffsetDateTime
)
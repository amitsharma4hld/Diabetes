package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class BabyWeight (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val weight:String,
    val score: Int,
    val date: OffsetDateTime
)
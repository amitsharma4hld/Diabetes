package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity()
class InsulinToday(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val unit: Double? = null,
    val score: Int,
    val date: OffsetDateTime
    )
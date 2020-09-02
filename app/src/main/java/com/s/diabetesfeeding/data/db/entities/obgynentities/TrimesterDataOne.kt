package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class TrimesterDataOne (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val comment: String,
    var date: String?=null,
    var isChecked: Boolean
)
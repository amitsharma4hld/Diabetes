package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class TrimesterDataOne (
    @PrimaryKey
    val id: Int,
    val title: String,
    var comment: String,
    var date: String,
    var isChecked: Boolean
)
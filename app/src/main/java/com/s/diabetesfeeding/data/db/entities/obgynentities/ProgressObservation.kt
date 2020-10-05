package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressObservation (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    var isChecked: Boolean,
    var comment:String,
    var offsetDateTime: OffsetDateTime
)
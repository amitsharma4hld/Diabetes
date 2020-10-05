package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
class ProgressSymptoms (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Symptom: String,
    var isChecked: Boolean,
    var comment:String,
    var offsetDateTime: OffsetDateTime

)
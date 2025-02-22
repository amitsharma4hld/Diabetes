package com.s.diabetesfeeding.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SymptomsData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Symptom: String,
    var isChecked: Boolean,
    var comment:String
)
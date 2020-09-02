package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Observation (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    var isChecked: Boolean

)
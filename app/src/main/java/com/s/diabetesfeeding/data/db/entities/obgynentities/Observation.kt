package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Observation (
    @PrimaryKey
    val id: Int,
    val title: String,
    var isChecked: Boolean,
    var comment:String

)
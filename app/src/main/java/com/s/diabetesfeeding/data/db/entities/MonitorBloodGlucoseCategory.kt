package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MonitorBloodGlucoseCategory(
    @PrimaryKey
    val catId:Int,
    val title: String
)

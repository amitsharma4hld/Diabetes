package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ObservationBreastFeed (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    var value: String,
    val isBoolean: Boolean,
    val unit: String

)
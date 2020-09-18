package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BloodGlucoseCategoryItem(
    @PrimaryKey
    val catItemId: Int,
    val itemsCatId: Int,
    val id: String,
    val range: String,
    val time: String,
    val title: String,
    var value: String,
    var score: Int,
    var isBlank: Boolean
)
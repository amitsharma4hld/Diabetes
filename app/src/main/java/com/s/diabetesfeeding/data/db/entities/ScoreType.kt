package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ScoreType (
    @PrimaryKey
    val id: Int,
    val score_type:String
)
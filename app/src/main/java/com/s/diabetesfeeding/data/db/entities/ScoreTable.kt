package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity
data class ScoreTable (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val user_id : Int,
    val sub_menuId : Int,
    val score : Int,
    val date_time : OffsetDateTime? = null
)
package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeMenus(
    @PrimaryKey
    val id: Int,
    val menuName: String

)
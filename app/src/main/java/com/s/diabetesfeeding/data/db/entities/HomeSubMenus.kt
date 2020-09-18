package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HomeSubMenus (
    @PrimaryKey(autoGenerate = false)
    val  id : Int,
    val subMenuName : String,
    val homeMenuId : Int,
    val score_type_id: Int
    )
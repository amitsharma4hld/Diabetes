package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["sub_menuId","id"])
class ScoreWithCategoryCrossRef (
    val sub_menuId:Int,
    val id:Int
)
package com.s.diabetesfeeding.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MenusWithSubMenus (
    @Embedded val homeMenus : HomeMenus,
    @Relation(
        parentColumn = "id", // ownerid
        entityColumn = "homeMenuId"// dogsOwnerId
    )
    val homeSubMenus : List<HomeSubMenus>
)


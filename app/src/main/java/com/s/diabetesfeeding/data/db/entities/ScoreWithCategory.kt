package com.s.diabetesfeeding.data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ScoreWithCategory (
    @Embedded val homeSubMenus: HomeSubMenus,
    @Relation(
        parentColumn = "id", // ownerid
        entityColumn = "sub_menuId",// dogsOwnerId
        associateBy = Junction(ScoreWithCategoryCrossRef::class)

    )
    val ScoreTable : List<ScoreTable>
)
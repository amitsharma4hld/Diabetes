package com.s.diabetesfeeding.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProgressDataWithCategory (
    @Embedded val monitorBloodGlucoseCategory: MonitorBloodGlucoseCategory,
    @Relation(
        parentColumn = "catId", // ownerid
        entityColumn = "itemsCatId",
        entity = ProgressBloodGlucose::class
    )
    val progressBloodGlucose: List<ProgressBloodGlucose>
)
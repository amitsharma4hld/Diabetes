package com.s.diabetesfeeding.data.db.entities.obgynentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class PrentalVisitRecord (
    @PrimaryKey
    var id : Int,
    val measurementOf: String,
    var value: String,
    val unit: String
):Serializable
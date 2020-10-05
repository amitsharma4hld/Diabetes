package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class BabyPoopData(
    @PrimaryKey
    var id : Int,
    var temId:Int,
    var poop_pee_time: String,
    var isPoop: Boolean,
    var isPee: Boolean
): Serializable

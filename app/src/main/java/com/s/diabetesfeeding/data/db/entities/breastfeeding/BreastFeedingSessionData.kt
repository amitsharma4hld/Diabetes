package com.s.diabetesfeeding.data.db.entities.breastfeeding

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class BreastFeedingSessionData (
    var breastfeedingTime: String,
    var breastfeedingTimerCount: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

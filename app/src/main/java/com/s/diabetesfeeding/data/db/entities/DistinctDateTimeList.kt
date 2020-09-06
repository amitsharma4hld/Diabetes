package com.s.diabetesfeeding.data.db.entities

import androidx.room.Ignore
import androidx.room.RoomWarnings
import org.threeten.bp.OffsetDateTime

data class DistinctDateTimeList (
    //@Ignore
    //@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    val date_time:OffsetDateTime? = null
)
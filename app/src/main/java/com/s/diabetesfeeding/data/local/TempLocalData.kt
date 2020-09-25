package com.s.diabetesfeeding.data.local

import org.threeten.bp.OffsetDateTime

data class TempLocalData (
    val date_time: OffsetDateTime? = null,
    val formattedDate: String,
    val isBlank:Boolean? = true
)

package com.s.diabetesfeeding.util

import android.content.Context
import android.widget.Toast
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.OffsetDateTime
import java.util.*



    fun Context.getDateFromOffsetDateTime (offsetDateTime: OffsetDateTime): Calendar {
        return  DateTimeUtils.toGregorianCalendar(offsetDateTime.toZonedDateTime())
    }


/*val parseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        parseDate.parse(dateToString[0])*/

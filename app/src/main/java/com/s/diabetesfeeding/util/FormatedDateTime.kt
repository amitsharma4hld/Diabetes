package com.s.diabetesfeeding.util

import android.annotation.SuppressLint
import android.content.Context
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDateFromOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC");
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDayFromOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getTimeZone("UTC");
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}

/*val parseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        parseDate.parse(dateToString[0])*/

 fun Context.getCurrentDateInString():String{
    val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
        java.util.Date())
    return currentDate
}

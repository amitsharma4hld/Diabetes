package com.s.diabetesfeeding.util

import android.annotation.SuppressLint
import android.content.Context
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDateFromOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getDefault()
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getStandardFormattedDateForAllScreen(convertedDate: String): String {
    val mSimpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
    mSimpleDateFormat.timeZone = TimeZone.getDefault()
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(mSimpleDateFormat.parse(convertedDate))
}

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDayFromOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getDefault()
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDDMMYYYYOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getDefault()
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}
/*val parseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        parseDate.parse(dateToString[0])*/

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun getDateInMMYYYYOffsetDateTime (offsetDateTime: OffsetDateTime): String {
    val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    mSimpleDateFormat.timeZone = TimeZone.getDefault()
    return SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(mSimpleDateFormat.parse(offsetDateTime.toString()))
}

 fun Context.getCurrentDateInString():String{
    val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
        java.util.Date())
    return currentDate
}

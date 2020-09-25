package com.s.diabetesfeeding.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.threeten.bp.OffsetDateTime

private const val KEY_SAVED_AT = "key_saved_at"
private const val KEY_SAVED_FORMATTED_DATE = "key_saved_formated_date"
private const val KEY_SAVED_DATE_TIME_OFFSET = "key_saved_date_time"
private const val LEY_SAVED_IS_LOGGED_IN = "key_saved_is_login"



class PreferenceProvider(
    context: Context
) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    fun savelastSavedAt(savedAt: String) {
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }

    fun saveformattedDate(formattedDate: String) {
        preference.edit().putString(
            KEY_SAVED_FORMATTED_DATE,
            formattedDate
        ).apply()
    }
    fun getSavedFormattedDate(): String? {
        return preference.getString(KEY_SAVED_FORMATTED_DATE, null)
    }

    fun saveIsLoggedIn(islogedIn: Boolean) {
        preference.edit().putBoolean(
            LEY_SAVED_IS_LOGGED_IN,
            islogedIn
        ).apply()
    }
    fun getSavedIsLoggedIn(): Boolean {
        return preference.getBoolean(LEY_SAVED_IS_LOGGED_IN, false)
    }
    fun saveOffsetDateTime(formattedDate: String) {
        preference.edit().putString(
            KEY_SAVED_DATE_TIME_OFFSET,
            formattedDate
        ).apply()
    }
    fun getOffsetDateTime(): String? {
        return preference.getString(KEY_SAVED_DATE_TIME_OFFSET, null)
    }
}
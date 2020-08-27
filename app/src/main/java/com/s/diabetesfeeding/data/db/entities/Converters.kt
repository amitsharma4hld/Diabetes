package com.s.diabetesfeeding.data.db.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type;
import java.util.ArrayList;


class Converters {
    companion object {
        private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        @TypeConverter
        @JvmStatic
        fun toOffsetDateTime(value: String?): OffsetDateTime? {
            return value?.let {
                return formatter.parse(value, OffsetDateTime::from)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromOffsetDateTime(date: OffsetDateTime?): String? {
            return date?.format(formatter)
        }
/*    @TypeConverter
    fun fromString(value: String?): MonitorbloodGlucose? {
        val listType: Type = object : TypeToken<MonitorbloodGlucose?>() {}.getType()
        return Gson().fromJson(value, listType)
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    fun arraylistToString(monitorbloodGlucose: MonitorbloodGlucose?): String? {
        val gson = Gson()
        return gson.toJson(monitorbloodGlucose?.monitorblood_glucose)
        // return date == null ? null : date.getTime();
    }*/

        @TypeConverter
        fun fromlistToString(list: List<List<MonitorbloodGlucoseX>>?): String? {
            val gson = Gson()
            return gson.toJson(list)
            // return date == null ? null : date.getTime();
        }

        @TypeConverter
        fun fromListString(value: String?): List<List<MonitorbloodGlucoseX>>? {
            val listType: Type =
                object : TypeToken<List<List<MonitorbloodGlucoseX?>>>() {}.getType()
            return Gson().fromJson(value, listType)
        }
    }
}
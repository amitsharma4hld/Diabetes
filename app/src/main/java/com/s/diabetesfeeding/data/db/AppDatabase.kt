package com.s.diabetesfeeding.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.s.diabetesfeeding.data.db.entities.*

@Database(
    entities = [Data::class,MonitorbloodGlucose::class,Symptom::class,MonitorBloodGlucoseCategory::class,BloodGlucoseCategoryItem::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getMonitorbloodGlucoseDao(): MonitorbloodGlucoseDao
    abstract fun getMonitorBloodGlucoseCatDao():MonitorBloodGlucoseCatDao

    companion object {
        @Volatile
        private var instance : AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "DiabetesDatabase.db"
            ).build()
    }

}
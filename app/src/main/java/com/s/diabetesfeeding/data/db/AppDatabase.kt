package com.s.diabetesfeeding.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyWeight
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ObservationBreastFeed
import com.s.diabetesfeeding.data.db.entities.obgynentities.*

@Database(
    entities = [
        Data::class,
        HomeMenus::class,
        HomeSubMenus::class,
        ScoreTable::class,
        MonitorbloodGlucose::class,
        InsulinToday::class,
        WeightToday::class,
        Symptom::class,
        MonitorBloodGlucoseCategory::class,
        SymptomsData::class,
        BloodGlucoseCategoryItem::class,
        Observation::class,
        TrimesterDataOne::class,
        TrimesterDataTwo::class,
        TrimesterDataThree::class,
        PostPartumData::class,
        PrentalVisitRecord::class,
        BabyWeight::class,
        ObservationBreastFeed::class,
        BreastFeedingSessionData::class,
        BabyPoopData::class
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getMonitorbloodGlucoseDao(): MonitorbloodGlucoseDao
    abstract fun getMonitorBloodGlucoseCatDao():MonitorBloodGlucoseCatDao
    abstract fun getSymptomsDao(): SymptomDao
    abstract fun getHomeMenusDao(): HomeMenusDao
    abstract fun getObgynDao(): ObgynDao
    abstract fun getBreastFeedingDao(): BreastFeedingDao
    //abstract fun getScoresWithCategory(): HomeMenusDao

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

    override fun clearAllTables() {
    }

}
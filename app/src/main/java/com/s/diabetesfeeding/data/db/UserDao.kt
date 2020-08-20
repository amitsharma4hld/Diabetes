package com.s.diabetesfeeding.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.CURRENT_USER_ID
import com.s.diabetesfeeding.data.db.entities.Data

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: Data) : Long

    @Query("SELECT * FROM data WHERE uid = $CURRENT_USER_ID")
    fun getuser() : LiveData<Data>

    @Delete
    suspend fun delete(data: Data)

}
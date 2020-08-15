package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Symptom(
    val describe: Boolean,
    val icon: String,
    val id: String,
    val title: String,
    val type: String
){
    @PrimaryKey(autoGenerate = true)
    var uid:Int = CURRENT_USER_ID
}
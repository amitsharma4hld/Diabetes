package com.s.diabetesfeeding.data.db.entities

import org.threeten.bp.OffsetDateTime


data class ScoreHashMapData (
    val homeMenuId: Int,
    val menuName:String,
    val sub_menuId:Int,
    val subMenuName:String,
    val score_type_id:Int,
    val score_type:String,
    val date_time: OffsetDateTime,
    val date_split_string: String,
    val subMenuScore:Int

    )

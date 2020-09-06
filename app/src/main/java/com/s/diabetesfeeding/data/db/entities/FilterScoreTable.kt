package com.s.diabetesfeeding.data.db.entities


import org.threeten.bp.OffsetDateTime

// select DISTINCT homeMenuId,menuName,sub_menuId,subMenuName,score,score_type,score_type_id,date_time
// from ScoreTable st LEFT  JOIN HomeSubMenus HSM on HSM.id=ST.sub_menuid
// INNER  JOIN HomeMenus HM on HM.id=HSM.homeMenuId INNER  JOIN ScoreType STY on STY.id=HSM.score_type_id

data class FilterScoreTable (
    val homeMenuId: Int,
    val menuName:String,
    val sub_menuId:Int,
    val subMenuName:String,
    val score: Int,
    val score_type:String,
    val score_type_id:Int,
    val date_time: OffsetDateTime
)




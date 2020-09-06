package com.s.diabetesfeeding.data.db.entities

data class ScoreBoardFinalData (
    val index: Int,
    var expandable : Boolean = false,
    val scoreDataList: MutableList<ScoreHashMapData>
)
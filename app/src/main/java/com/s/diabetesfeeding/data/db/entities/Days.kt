package com.s.diabetesfeeding.data.db.entities

import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBabyPoopDiaperChange
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBreastFeeding
import java.util.ArrayList

class Days (
    val id:Int,
    val date: String,
    val progressBreastFeeding:List<ProgressBreastFeeding> = ArrayList(),
    val progressDiaperChange:List<ProgressBabyPoopDiaperChange> = ArrayList()
)
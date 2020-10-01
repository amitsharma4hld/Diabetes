package com.s.diabetesfeeding.ui

import android.view.View
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressBreastFeeding

interface CellClickListener {
    fun onCellClickListener(view:View)
  //  'date','breastfeedCount','poopCount','peepCount'
    fun onBottomClickListener(day:String,breastFeedingCount:String,poopCount:String,peepCount:String)
}
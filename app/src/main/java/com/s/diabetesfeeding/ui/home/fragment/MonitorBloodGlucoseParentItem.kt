package com.s.diabetesfeeding.ui.home.fragment

import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucoseX
import com.s.diabetesfeeding.databinding.ItemMonitorBloodGlucoseParentBinding
import com.xwray.groupie.databinding.BindableItem

class MonitorBloodGlucoseParentItem  (
    private val monitorbloodGlucose: MonitorbloodGlucoseX
) : BindableItem<ItemMonitorBloodGlucoseParentBinding>(){
    override fun getLayout()= R.layout.item_monitor_blood_glucose_parent
    override fun bind(viewBinding: ItemMonitorBloodGlucoseParentBinding, position: Int) {
        viewBinding.monitorbloodGlucose = monitorbloodGlucose

    }


}
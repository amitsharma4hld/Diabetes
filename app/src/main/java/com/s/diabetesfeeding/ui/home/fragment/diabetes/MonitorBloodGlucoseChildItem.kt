package com.s.diabetesfeeding.ui.home.fragment.diabetes

import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.databinding.ItemMonitorBloodGlucoseChildBinding
import com.xwray.groupie.databinding.BindableItem

class MonitorBloodGlucoseChildItem (
    private val monitorbloodGlucose: MonitorbloodGlucose
) : BindableItem<ItemMonitorBloodGlucoseChildBinding>(){
    override fun getLayout()= R.layout.item_monitor_blood_glucose_child

    override fun bind(viewBinding: ItemMonitorBloodGlucoseChildBinding, position: Int) {
        viewBinding.monitorbloodGlucose = monitorbloodGlucose
    }

}
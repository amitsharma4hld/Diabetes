package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_parent.view.*

class MonitorBloodGlucoseMainAdapter (val context: Context,private val allCategory:List<CategoryWithItems>) :
    RecyclerView.Adapter<MonitorBloodGlucoseMainAdapter.MonitorBloodGlucoseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitorBloodGlucoseViewHolder {
        return MonitorBloodGlucoseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_monitor_blood_glucose_parent, parent, false)
        )
    }

    override fun getItemCount() = allCategory.size

    override fun onBindViewHolder(holder: MonitorBloodGlucoseViewHolder, position: Int) {
        val category = allCategory[position]
        if (position == allCategory.size-1){
            holder.view.view_rv_divider.visibility = View.GONE
        }
        setChildItem(holder.itemRecycler!!, category.bloodGlucoseCategoryItems)
    }

    class MonitorBloodGlucoseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemRecycler:RecyclerView? = null
        init {
            itemRecycler =  view.findViewById(R.id.cat_item_recycler)
        }
    }

    private fun setChildItem(recyclerView: RecyclerView, categoryItem: List<BloodGlucoseCategoryItem>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =  CategoryItemAdapter(context,categoryItem)
    }
}
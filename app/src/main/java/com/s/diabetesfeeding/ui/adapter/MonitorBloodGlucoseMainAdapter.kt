package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.entities.AllCategory
import com.s.diabetesfeeding.data.db.entities.CategoryItem
import com.s.diabetesfeeding.ui.SymptomsAdapter
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_parent.view.*
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*

class MonitorBloodGlucoseMainAdapter (val context: Context,private val allCategory:List<AllCategory>) :
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
        holder.view.view_rv_divider.visibility = View.VISIBLE

        setChildItem(holder.itemRecycler!!, category.categoryItem)
    }

    class MonitorBloodGlucoseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemRecycler:RecyclerView? = null
        init {
            itemRecycler =  view.findViewById(R.id.cat_item_recycler)
        }
    }

    private fun setChildItem(recyclerView: RecyclerView, categoryItem: List<CategoryItem>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =  CategoryItemAdapter(categoryItem)
    }
}
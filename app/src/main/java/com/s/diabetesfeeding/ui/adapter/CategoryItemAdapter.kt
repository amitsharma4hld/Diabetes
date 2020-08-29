package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*

class CategoryItemAdapter (private val context: Context,private val categoryItem: List<BloodGlucoseCategoryItem>): RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {

    class CategoryItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_monitor_blood_glucose_child, parent, false)
        )
    }

    override fun getItemCount() = categoryItem.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val categoryItems = categoryItem[position]
        if (position>=1){
            holder.view.rl_done.visibility = View.GONE
            holder.view.rl_or_done.visibility = View.VISIBLE
        }
        if (position == categoryItem.size-1){
            holder.view.rl_or_done.visibility = View.GONE
        }

        if (categoryItems.value.isEmpty()){
            holder.view.et_value.inputType = InputType.TYPE_CLASS_NUMBER
            holder.view.et_value.setText("")
        }else{
            holder.view.et_value.inputType = InputType.TYPE_NULL
            holder.view.et_value.setText(categoryItems.value)
        }
        if (position>=1){
            if(categoryItem[1].value.isNotEmpty() || categoryItem[2].value.isNotEmpty()){
                holder.view.et_value.inputType = InputType.TYPE_NULL
                Log.d("Value alredy :","Recived")
            }
        }
        holder.view.tv_title.text = categoryItems.title
        holder.view.tv_time.text = categoryItems.time
        holder.view.tv_range_value.text = categoryItems.range

        holder.view.rl_done.setOnClickListener {
            categoryItems.value = holder.view.et_value.text.toString()
            update(categoryItems)
        }
    }

    fun update(categoryItems: BloodGlucoseCategoryItem) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getMonitorBloodGlucoseCatDao().updateBloodGlucoseCategoryItem(categoryItems)
                Log.d("APPDATABASE : ","Update value is ${categoryItems.value}")
            }
        }
    }

}
package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.CategoryItem
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*

class CategoryItemAdapter (private val categoryItem: List<CategoryItem>): RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {

    class CategoryItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_monitor_blood_glucose_child, parent, false)
        )
    }

    override fun getItemCount()= categoryItem.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val categoryItem = categoryItem[position]
        holder.view.et_value.setText(categoryItem.value)
        holder.view.tv_title.text = categoryItem.title
        holder.view.tv_time.text = categoryItem.time
        holder.view.tv_range_value.text = categoryItem.range
        holder.view.rl_done.setOnClickListener {

        }
    }

}
package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.Days
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.util.logger.Log
import kotlinx.android.synthetic.main.item_history_child.view.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.rl_done

class BreastFeedHistoryChilAdapter(private val context: Context, private val categoryItem: List<Days>,private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<BreastFeedHistoryChilAdapter.ChildItemViewHolder>() {

    class ChildItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildItemViewHolder {
        return BreastFeedHistoryChilAdapter.ChildItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_child, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChildItemViewHolder, position: Int) {
        val categoryItems = categoryItem[position]
        for (i in 0 until categoryItems.progressBreastFeeding.size) {
            //convert both date by dd
            if (categoryItems.date.equals(categoryItems.progressBreastFeeding.get(i).dateTime))
              holder.itemView.breastfeedingprocess.height

        }

        for (i in 0 until categoryItems.progressDiaperChange.size) {
            //convert both date by dd/mm/yyyy
            if(categoryItems.progressDiaperChange.get(i).isPee)
            if (categoryItems.date.equals(categoryItems.progressDiaperChange.get(i).dateTime))
                holder.itemView.breastfeedingprocess.height

            if(categoryItems.progressDiaperChange.get(i).isPoop)
                if (categoryItems.date.equals(categoryItems.progressDiaperChange.get(i).dateTime))
                    holder.itemView.breastfeedingprocess.height

        }

        if (position==16){
            holder.view.cv_start.visibility = View.VISIBLE
        }else{
            holder.view.cv_start.visibility = View.GONE
        }
        holder.view.cv_start.setOnClickListener {
            cellClickListener.onCellClickListener(it)
        }
    }

    override fun getItemCount()=categoryItem.size
}
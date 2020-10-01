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
import com.s.diabetesfeeding.util.getDDMMYYYYOffsetDateTime
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import com.s.diabetesfeeding.util.logger.Log
import kotlinx.android.synthetic.main.item_history_child.view.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.rl_done
import org.threeten.bp.OffsetDateTime
import kotlin.math.roundToInt

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
        var count:Int = 0
        var poopCount:Int = 0
        var peeCount:Int = 0
        if (categoryItems.date.isNotEmpty()){
            for (i in categoryItems.progressBreastFeeding.indices) {
                if (getDDMMYYYYOffsetDateTime(OffsetDateTime.parse(categoryItems.date))
                        .equals(getDDMMYYYYOffsetDateTime(categoryItems.progressBreastFeeding[i].dateTime))){
                    count += 1
                    holder.itemView.breastfeedingprocess.layoutParams.height = dpToPx(count*5)
                }
            }
            for (i in categoryItems.progressDiaperChange.indices) {
                if(categoryItems.progressDiaperChange[i].isPoop)
                    if (getDDMMYYYYOffsetDateTime(OffsetDateTime.parse(categoryItems.date))
                            .equals(getDDMMYYYYOffsetDateTime(categoryItems.progressDiaperChange[i].dateTime))){
                        poopCount += 1
                        holder.itemView.stoolprocess.layoutParams.height = dpToPx(poopCount*5)
                    }
                if(categoryItems.progressDiaperChange[i].isPee){
                    if (getDDMMYYYYOffsetDateTime(OffsetDateTime.parse(categoryItems.date))
                            .equals(getDDMMYYYYOffsetDateTime(categoryItems.progressDiaperChange[i].dateTime))){
                        peeCount  += 1
                        holder.itemView.diperprocess.layoutParams.height = dpToPx(peeCount*5)
                    }
                }
            }

            if (getDDMMYYYYOffsetDateTime(OffsetDateTime.parse(categoryItems.date))
                    .equals(getDDMMYYYYOffsetDateTime(OffsetDateTime.now()))){
                holder.view.cv_start.visibility = View.VISIBLE
            }else{
                holder.view.cv_start.visibility = View.GONE
            }

            holder.view.cv_start.setOnClickListener {

                cellClickListener.onBottomClickListener(getDateFromOffsetDateTime(OffsetDateTime.parse(categoryItems.date)),
                    "$count Times", "$poopCount Times","$peeCount Times" )
            }
        }

    }
    private fun dpToPx(dp: Int): Int {
        val density: Float = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    override fun getItemCount()=categoryItem.size
}
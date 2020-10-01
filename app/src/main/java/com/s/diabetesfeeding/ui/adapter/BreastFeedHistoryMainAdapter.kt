package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.Days
import com.s.diabetesfeeding.data.db.entities.MonthWithDates
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.util.SpaceGridDecoration
import kotlinx.android.synthetic.main.fragment_breastfeeding.*
import kotlinx.android.synthetic.main.item_history_parent.view.*
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_parent.view.*

class BreastFeedHistoryMainAdapter(val context: Context, private val allCategory:List<MonthWithDates>,private val cellClickListener: CellClickListener):
    RecyclerView.Adapter<BreastFeedHistoryMainAdapter.HistoryMainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryMainViewHolder {
        return BreastFeedHistoryMainAdapter.HistoryMainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_parent, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryMainViewHolder, position: Int) {
        allCategory.let {
            val category = it[position]
            setChildItem(holder.itemRecycler!!, category.daysitem.reversed())
        }
    }

    private fun setChildItem(recyclerView: RecyclerView, daysitem: List<Days>) {
        recyclerView.layoutManager = GridLayoutManager(context, 7)
        recyclerView!!.addItemDecoration(SpaceGridDecoration(7, 2, false))
        recyclerView.adapter =
            BreastFeedHistoryChilAdapter(
                context,
                daysitem,
                cellClickListener
            )

    }

    override fun getItemCount() = allCategory.size

    class HistoryMainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemRecycler:RecyclerView? = null
        init {
            itemRecycler =  view.findViewById(R.id.showgraphlist)
        }
    }
}
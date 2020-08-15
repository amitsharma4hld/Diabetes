package com.s.diabetesfeeding.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.ScoreboardData
import kotlinx.android.synthetic.main.item_scoreboard.view.*

class ScoreBoardAdapter(val scores : List<ScoreboardData>): RecyclerView.Adapter<ScoreBoardAdapter.ScoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_scoreboard, parent, false)
        )
    }

    override fun getItemCount()= scores.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.view.tv_date.text = score.date
        holder.view.tv_visit.text = score.visit
        holder.view.tv_record.text = score.record
        holder.view.tv_observe.text = score.observe
        holder.view.tv_breastfeed.text = score.breastfeed
        holder.view.tv_total.text = score.total

    }

    class ScoreViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
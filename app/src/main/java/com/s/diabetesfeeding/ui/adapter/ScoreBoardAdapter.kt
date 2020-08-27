package com.s.diabetesfeeding.ui.adapter

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

    }

    class ScoreViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
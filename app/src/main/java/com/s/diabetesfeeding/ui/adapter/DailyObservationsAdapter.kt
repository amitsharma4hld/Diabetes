package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.DailyObservationData
import kotlinx.android.synthetic.main.item_daily_observations_layout.view.*


class DailyObservationsAdapter (val observations : List<DailyObservationData>) : RecyclerView.Adapter<DailyObservationsAdapter.ObservationsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationsViewHolder {
        return ObservationsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily_observations_layout, parent, false)
        )
    }

    override fun getItemCount()= observations.size

    override fun onBindViewHolder(holder: ObservationsViewHolder, position: Int) {
        val observations = observations[position]
        holder.view.tv_observation_title.text = observations.title
        holder.view.tv_observation_value.text = observations.inputValue

    }

    class ObservationsViewHolder(val view: View) : RecyclerView.ViewHolder(view)


}
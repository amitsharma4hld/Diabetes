package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ObservationBreastFeed
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.item_daily_observations_layout.view.*


class DailyObservationsAdapter (private val context: Context, val observations : List<ObservationBreastFeed>) : RecyclerView.Adapter<DailyObservationsAdapter.ObservationsViewHolder>(){

    var isSelected: Boolean = false
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
        holder.view.et_observation_value.setText(observations.value)
        if (observations.isBoolean){
            if (observations.value.equals("Yes")){
                isSelected = true
            }
            holder.view.et_observation_value.inputType = InputType.TYPE_NULL
            holder.view.et_observation_value.setOnClickListener {
                if (isSelected){
                    holder.view.et_observation_value.setText("No")
                    observations.value = "No"
                    isSelected= false
                    update(observations)
                }else if(!isSelected){
                    holder.view.et_observation_value.setText("Yes")
                    observations.value = "Yes"
                    isSelected= true
                    update(observations)
                }
            }
        }
        holder.view.et_observation_value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                observations.value = editable.toString()
                update(observations)
            }
        })

    }

    class ObservationsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun update(observationitem: ObservationBreastFeed) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getBreastFeedingDao().updateObservation(observationitem)
                Log.d("APPDATABASE : ","Update value is ${observationitem.value}")
            }
        }
    }
}
package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.InputFilter
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
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressDailyObservation
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.fragment.breastfeeding.observationList
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_pre_pregnancy_input.*
import kotlinx.android.synthetic.main.item_daily_observations_layout.view.*
import org.threeten.bp.OffsetDateTime


class DailyObservationsAdapter (private val context: Context, private val observations : List<ObservationBreastFeed>) : RecyclerView.Adapter<DailyObservationsAdapter.ObservationsViewHolder>(){

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

        if (observations.title == "Sleep Hours"){
            holder.view.et_observation_value.hint = "4-10"
        }else if(observations.title == "Rate your fatigue") {
            holder.view.et_observation_value.hint = "1-10"
        }else if(observations.title == "Glass of water per day") {
            holder.view.et_observation_value.hint = "4-10"
        }else if(observations.title == "Meals per day") {
            holder.view.et_observation_value.hint = "1-5"
        }else if(observations.title == "Helps with chores") {
            holder.view.et_observation_value.filters = arrayOf(InputFilter.LengthFilter(3))
            holder.view.et_observation_value.hint = "YES"
        }else if(observations.title == "Glass of alcohol per day?") {
            holder.view.et_observation_value.hint = "0-10"
        }else if(observations.title == "Smoking") {
            holder.view.et_observation_value.filters = arrayOf(InputFilter.LengthFilter(3))
            holder.view.et_observation_value.hint = "NO"
        }else if(observations.title == "Rate your stress") {
            holder.view.et_observation_value.hint = "0-10"
        }

        if (prefs.getSavedIsPreviousDate()) {
            holder.view.et_observation_value.setOnClickListener {
                it.snackbar("Previous data can not be edited")
            }
            holder.view.et_observation_value.isFocusable = false
        }

        if (observations.isBoolean){
            if (observations.value == "Yes"){
                isSelected = true
            }
            holder.view.et_observation_value.inputType = InputType.TYPE_NULL
            holder.view.et_observation_value.setOnClickListener {
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                }else
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
                updateProgressDailyObservation(observationitem)
            }
        }
    }

    private fun updateProgressDailyObservation(observationBreastFeed: ObservationBreastFeed) {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                val progressData = ProgressDailyObservation(0,observationBreastFeed.title,
                    observationBreastFeed.value,observationBreastFeed.isBoolean,observationBreastFeed.unit,currentDate)
                AppDatabase(it).getBreastFeedingDao().saveProgressDailyObservation(progressData)
            }
        }
    }

}
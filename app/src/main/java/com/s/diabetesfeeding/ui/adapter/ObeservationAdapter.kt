package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressObservation
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.showComentDialog
import com.s.diabetesfeeding.util.showObservationComentDialog
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_observation_list.view.*
import org.threeten.bp.OffsetDateTime

class ObeservationAdapter (val context: Context, val observation : List<Observation>) : RecyclerView.Adapter<ObeservationAdapter.SymptomsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        return SymptomsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_observation_list, parent, false)
        )
    }

    override fun getItemCount()= observation.size

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val observe = observation[position]
        holder.view.cb_observation.text = observe.title
        holder.view.cb_observation.isChecked = observe.isChecked
        if(observe.title == "Other"){
            holder.view.mc_describe.visibility = View.VISIBLE
        }
        holder.view.cb_observation.setOnClickListener(View.OnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                    if (holder.view.cb_observation.isChecked) {
                        holder.view.cb_observation.isChecked = false
                    }
                }else {
                    if (holder.view.cb_observation.isChecked) {
                        observe.isChecked=true
                        update(observe)
                    } else {
                        observe.isChecked=false
                        update(observe)
                    }
                }
            }else{
                it.snackbar("Can not edit patient details")
                if (holder.view.cb_observation.isChecked) {
                    holder.view.cb_observation.isChecked = false
                    Log.d("selected index:",position.toString())
                }
            }

        })
        holder.view.mc_describe.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
            }else {
                if (observe.isChecked){
                    context.showObservationComentDialog(observe)
                }

            }
        }
    }
    private fun update(observation: Observation) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getObgynDao().updateObservation(observation)
                updateProgress(observation)
            }
        }
    }
    private fun updateProgress(observ: Observation){
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData = ProgressObservation(0,observ.title,observ.isChecked,observ.comment,currentDate)
                AppDatabase(it).getObgynDao().saveProgressData(progressData) // fix this
            }
        }
    }

    class SymptomsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
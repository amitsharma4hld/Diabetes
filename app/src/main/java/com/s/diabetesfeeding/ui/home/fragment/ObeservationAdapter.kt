package com.s.diabetesfeeding.ui.home.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.item_observation_list.view.*
import kotlinx.android.synthetic.main.item_symptoms_list.view.*
import kotlinx.android.synthetic.main.item_symptoms_list.view.mc_describe

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
            if (holder.view.cb_observation.isChecked) {
                observe.isChecked=true
                update(observe)
            } else {
                observe.isChecked=false
                update(observe)
            }
        })
    }
    private fun update(observation: Observation) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getObgynDao().updateObservation(observation)
                Log.d("APPDATABASE : ","Update value is ${observation.isChecked}")
            }
        }
    }
    class SymptomsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
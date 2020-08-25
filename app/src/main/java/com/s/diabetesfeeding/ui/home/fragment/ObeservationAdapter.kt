package com.s.diabetesfeeding.ui.home.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import kotlinx.android.synthetic.main.item_symptoms_list.view.*

class ObeservationAdapter (val symptoms : List<SymptomsData>) : RecyclerView.Adapter<ObeservationAdapter.SymptomsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        return SymptomsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_observation_list, parent, false)
        )
    }

    override fun getItemCount()= symptoms.size

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val symptom = symptoms[position]
        holder.view.cb_symptom.text = symptom.Symptom
        holder.view.cb_symptom.isChecked = symptom.isChecked
        if(symptom.Symptom == "Other"){
            holder.view.mc_describe.visibility = View.VISIBLE
        }
    }

    class SymptomsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
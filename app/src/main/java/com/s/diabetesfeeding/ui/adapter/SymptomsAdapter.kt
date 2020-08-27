package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.item_symptoms_list.view.*

class SymptomsAdapter(val context:Context,val symptoms : List<SymptomsData>) : RecyclerView.Adapter<SymptomsAdapter.SymptomsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        return SymptomsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_symptoms_list, parent, false)
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

        holder.view.cb_symptom.setOnClickListener(View.OnClickListener {
            if (holder.view.cb_symptom.isChecked) {
                symptom.isChecked=true
                update(symptom)
            } else {
                symptom.isChecked=false
                update(symptom)
            }
        })
    }

    fun update(symptoms: SymptomsData) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getSymptomsDao().updateSymptom(symptoms)
                Log.d("APPDATABASE : ","Update value is ${symptoms.isChecked}")
            }
        }
    }

    class SymptomsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
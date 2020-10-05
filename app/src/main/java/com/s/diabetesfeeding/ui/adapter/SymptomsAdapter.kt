package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.showComentDialog
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_symptoms_list.view.*
import org.threeten.bp.OffsetDateTime


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
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                if (holder.view.cb_symptom.isChecked) {
                    holder.view.cb_symptom.isChecked = false
                }
            }else{
                if (holder.view.cb_symptom.isChecked) {
                    symptom.isChecked=true
                    update(symptom)
                } else {
                    symptom.isChecked=false
                    update(symptom)
                }
            }

        })
        holder.view.mc_describe.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
            }else {
                context.showComentDialog(symptom)
            }
        }
    }

  private fun update(symptoms: SymptomsData) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getSymptomsDao().updateSymptom(symptoms)
                updateProgress(symptoms)
            }
        }
    }
   private fun updateProgress(symptoms: SymptomsData){
       Coroutines.io {
           context.let {
               val currentDate = OffsetDateTime.now()
               val progressData = ProgressSymptoms(0,symptoms.Symptom,symptoms.isChecked,symptoms.comment,currentDate)
               AppDatabase(it).getSymptomsDao().saveProgressData(progressData)
           }
       }
    }

    class SymptomsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
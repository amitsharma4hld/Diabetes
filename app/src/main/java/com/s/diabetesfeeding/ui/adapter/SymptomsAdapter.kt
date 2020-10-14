package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.Gravity
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
import com.s.diabetesfeeding.util.logger.Log
import com.s.diabetesfeeding.util.showComentDialog
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_symptoms_list.view.cb_symptom
import kotlinx.android.synthetic.main.item_symptoms_list.view.mc_describe
import kotlinx.android.synthetic.main.item_symptoms_with_image_header_one.view.*
import kotlinx.android.synthetic.main.item_symptoms_with_image_list.view.*
import org.threeten.bp.OffsetDateTime


class SymptomsAdapter(
    val context: Context,
    val symptoms: List<SymptomsData>,
    header: View
) : RecyclerView.Adapter<SymptomsAdapter.SymptomsViewHolder>(){
    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1
    private val ITEM_VIEW_TYPE_SEPRATE_LINE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return SymptomsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_symptoms_with_image_header_one, parent, false)
            )
        }
        if (viewType == ITEM_VIEW_TYPE_SEPRATE_LINE) {
            return SymptomsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_seprate_line, parent, false)
            )
        }

        return SymptomsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_symptoms_with_image_list, parent, false)
        )
    }
    fun isHeader(position: Int): Boolean {
        return position == 0
    }
    fun isSecondHeader(position: Int): Boolean {
        return position == 11
    }
    fun isNoSymptoms(position: Int): Boolean {
        return position == 19
    }
    fun isfillSpan(position: Int): Boolean {
        return position == 10
    }
    fun isfillSpanTwo(position: Int): Boolean {
        return position == 18
    }
    override fun getItemCount()= symptoms.size -1

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        if (isHeader(position)) {
            return
        }
        if (isSecondHeader(position)) {
            holder.view.tv_header.text = "Hyperglycemia"
            return
        }
        if (isNoSymptoms(position)) {
            return
        }

        val symptom = symptoms[position-1]
        //holder.view.cb_symptom.text = symptom.Symptom

        holder.view.cb_symptom.isChecked = symptom.isChecked
        when (symptom.Symptom) {
            "Shaky" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.shaky)
            }
            "Fast heartbeat" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.fast_heartbeat)
            }
            "Sweating" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.sweating)
            }
            "Dizzy" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.dizzy)
            }
            "Anxious" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.anxious)
            }
            "Hungry" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.hungry)
            }
            "Blurry Vision" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.blurryvision)
            }
            "Weakness or Fatigue" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.weakness)
            }
            "Headache" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.headache)
            }
            "Irritable" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.irritable)
            }
            "Extreme Thirst" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.extreme_thirst)
            }
            "Need To Urinate Often" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.need_to_urinate_often)
            }
            "Dry Skin" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.dry_skin)
            }

            "Drowsy" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.drowsy)
            }
            "Slow Healing Wound" -> {
                holder.view.iv_symptom_image.setImageResource(R.drawable.slow_healing)
            }
           /* "No Symptoms" -> {
               // holder.view.cb_symptom.gravity = Gravity.START
                //holder.view.ll_divider_line.visibility = View.VISIBLE
                holder.view.cb_symptom.text = symptom.Symptom
                holder.view.iv_symptom_image.visibility = View.GONE
            }
            "Other" -> {
              //  holder.view.cb_symptom.gravity = Gravity.START
                holder.view.mc_describe.visibility = View.VISIBLE
                holder.view.cb_symptom.text = symptom.Symptom
                holder.view.iv_symptom_image.visibility = View.GONE
            }*/
        }

        holder.view.cb_symptom.setOnClickListener(View.OnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                if (holder.view.cb_symptom.isChecked) {
                    holder.view.cb_symptom.isChecked = false
                    Log.d("selected index:",position.toString())
                }
            }else{
                if (holder.view.cb_symptom.isChecked) {
                    Log.d("selected index:",position.toString())
                    symptom.isChecked=true
                    update(symptom)
                } else {
                    symptom.isChecked=false
                    update(symptom)
                }
            }

        })

     /*   holder.view.mc_describe.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
            }else {
                if (symptom.isChecked){
                    //color_graph_bg
                    context.showComentDialog(symptom)
                }
            }
        }*/
    }

    override fun getItemViewType(position: Int): Int {
     //   return super.getItemViewType(position)
    //    return if (isHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
        return when {
            isHeader(position) -> {
                ITEM_VIEW_TYPE_HEADER
            }
            isSecondHeader(position) -> {
                ITEM_VIEW_TYPE_HEADER
            }
            isNoSymptoms(position) -> {
                ITEM_VIEW_TYPE_SEPRATE_LINE
            }
            else -> ITEM_VIEW_TYPE_ITEM
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
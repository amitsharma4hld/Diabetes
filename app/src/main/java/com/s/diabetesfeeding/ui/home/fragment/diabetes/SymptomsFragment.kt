package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.SymptomsAdapter
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.item_symptoms_list.view.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SymptomsFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        Date()
    )

    var symptomsList: List<SymptomsData> = ArrayList()
    private val SymptomsScore: Int = 5
    var isSymptomsScoreSaved : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewMonitorBloodGlucose.isNestedScrollingEnabled = false
        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date_symptoms.text=currentDate
        }else{
            tv_today_date_symptoms.text = prefs.getSavedFormattedDate()
        }

        mcv_symptoms_done.setOnClickListener {
            if (!prefs.getSavedDoctorId()?.isNotBlank()!!){
                if (prefs.getSavedIsPreviousDate()) {
                    it.snackbar("Previous data can not be edited")
                }else{
                    if (!isSymptomsScoreSaved){
                        updateScore()
                        requireActivity().shortToast("Score Updated")
                    }else{
                        view?.snackbar("Already Saved For Today")
                        requireActivity().onBackPressed()
                    }
                }
            }else{
                it.snackbar("Can not edit patient details")
            }

        }

        tv_what_can_you_do.setOnClickListener {
           requireActivity().alertDialog("Help","message")
        }
        var currentDate:String = ""
        currentDate = if (!prefs.getOffsetDateTime().isNullOrEmpty()){
            val dateToString = prefs.getOffsetDateTime()!!.toString().split("T")
            dateToString[0]
        }else{
            getCurrentDateInString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                symptomsList =  AppDatabase(requireActivity().applicationContext).getSymptomsDao().getSymptom()
               // showSymptoms(symptomsList)
                showGridRecords(symptomsList)
            }
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getScoreByCategory(4).isNotEmpty()){
                    for (i in AppDatabase(it).getHomeMenusDao().getScoreByCategory(4).indices){
                        val date = AppDatabase(it).getHomeMenusDao().getScoreByCategory(4)[i].date_time
                        val dateToString = date.toString().split("T")
                        if (currentDate == dateToString[0]) {
                            isSymptomsScoreSaved = true
                            return@let
                        }
                    }
                }
            }
        }

    }

    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 4, SymptomsScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }

  /* private fun showSymptoms(symptoms: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            SymptomsAdapter(requireActivity(), symptoms, header)
   }*/

    private fun showGridRecords(symptoms: List<SymptomsData>) {
        val manager = GridLayoutManager(requireActivity(), 3)
        recyclerViewMonitorBloodGlucose.layoutManager = manager

        val header: View = LayoutInflater.from(requireActivity()).inflate(
            R.layout.item_symptoms_with_image_header_one, recyclerViewMonitorBloodGlucose, false
        )
        val adapter = SymptomsAdapter(requireActivity(),symptoms,header)
        recyclerViewMonitorBloodGlucose.adapter = adapter
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
              //  return if (SymptomsAdapter(requireActivity(),symptoms,header).isHeader(position)) manager.spanCount else 1
                return when {
                    SymptomsAdapter(requireActivity(),symptoms,header).isHeader(position) -> {
                        manager.spanCount
                    }
                    SymptomsAdapter(requireActivity(),symptoms,header).isSecondHeader(position) -> {
                        manager.spanCount
                    }
                    SymptomsAdapter(requireActivity(),symptoms,header).isNoSymptoms(position) -> {
                        manager.spanCount
                    }
                    SymptomsAdapter(requireActivity(),symptoms,header).isfillSpan(position) -> {
                        3
                    }
                    SymptomsAdapter(requireActivity(),symptoms,header).isfillSpanTwo(position) -> {
                        3
                    }
                    else -> {
                        1
                    }
                }
            }
        }
        if (symptoms.isNotEmpty()){
            if (symptoms.size>19){
                cb_no_symptom.isChecked = symptoms[19].isChecked
                cb_other_symptom.isChecked = symptoms[20].isChecked
            }
        }

        mc_describe.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
            }else {
                if (symptoms[20].isChecked){
                    context?.showComentDialog(symptoms[20])
                }
            }
        }
        cb_no_symptom.setOnClickListener(View.OnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                if (cb_no_symptom.isChecked) {
                    cb_no_symptom.isChecked = false
                }
            }else{
                if (cb_no_symptom.isChecked) {
                    symptoms[19].isChecked=true
                    update(symptoms[19])
                } else {
                    cb_no_symptom.isChecked=false
                    update(symptoms[19])
                }
            }

        })
        cb_other_symptom.setOnClickListener(View.OnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                if (cb_other_symptom.isChecked) {
                    cb_other_symptom.isChecked = false
                }
            }else{
                if (cb_other_symptom.isChecked) {
                    symptoms[20].isChecked=true
                    update(symptoms[20])
                } else {
                    cb_other_symptom.isChecked=false
                    update(symptoms[20])
                }
            }

        })

      /*  SymptomsAdapter(requireActivity(),symptoms,header)

        recyclerViewMonitorBloodGlucose.layoutManager = GridLayoutManager(activity, 3)
        recyclerViewMonitorBloodGlucose!!.addItemDecoration(SpaceGridDecoration(2, 8, false))
        recyclerViewMonitorBloodGlucose.adapter =
        SymptomsAdapter(requireActivity(), symptoms, header)*/
    }
/*
    mLayoutManager = GridLayoutManager(this, 2)
    mLayoutManager.setSpanSizeLookup(object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (mAdapter.getItemViewType(position)) {
                MyAdapter.HEADER -> 2
                MyAdapter.ITEM -> 1
                else -> 1
            }
        }
    })
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms, container, false)
    }



    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }
    private fun update(symptoms: SymptomsData) {
        Coroutines.io {
            context.let {
                AppDatabase(requireActivity()).getSymptomsDao().updateSymptom(symptoms)
                updateProgress(symptoms)
            }
        }
    }
    private fun updateProgress(symptoms: SymptomsData){
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData = ProgressSymptoms(0,symptoms.Symptom,symptoms.isChecked,symptoms.comment,currentDate)
                AppDatabase(requireActivity()).getSymptomsDao().saveProgressData(progressData)
            }
        }
    }
}
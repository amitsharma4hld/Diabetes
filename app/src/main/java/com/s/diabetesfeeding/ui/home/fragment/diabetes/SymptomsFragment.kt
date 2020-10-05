package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.SymptomsAdapter
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SymptomsFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var symptomsList: List<SymptomsData> = ArrayList()
    private val SymptomsScore: Int = 5
    var isSymptomsScoreSaved : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date_symptoms.text=currentDate
        }else{
            tv_today_date_symptoms.text = prefs.getSavedFormattedDate()
        }

        mcv_symptoms_done.setOnClickListener {
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
                showSymptoms(symptomsList)
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

   private fun showSymptoms(symptoms: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            SymptomsAdapter(requireActivity(),symptoms)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms, container, false)
    }

    private fun getCurrentDateInString():String{
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            java.util.Date())
        return currentDate
    }
}
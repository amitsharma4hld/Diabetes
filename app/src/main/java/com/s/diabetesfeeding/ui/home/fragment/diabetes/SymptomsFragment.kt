package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.InsulinToday
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.ui.adapter.SymptomsAdapter
import com.s.diabetesfeeding.util.*
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
    val SymptomsScore: Int = 5
    var isSymptomsScoreSaved : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date_symptoms.text = currentDate

        mcv_symptoms_done.setOnClickListener {
            if (!isSymptomsScoreSaved){
                updateScore()
                requireActivity().shortToast("Score Updated")
            }else{
                view?.snackbar("Already Saved For Today")
                requireActivity().onBackPressed()
            }
        }
        tv_what_can_you_do.setOnClickListener {
           requireActivity().alertDialog("Help","message")
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
                        var currentDate = it.getCurrentDateInString()
                        if (currentDate.equals(dateToString[0])) {
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
            SymptomsAdapter(requireActivity().applicationContext,symptoms)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms, container, false)
    }

}
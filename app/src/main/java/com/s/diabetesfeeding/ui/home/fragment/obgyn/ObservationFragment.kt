package com.s.diabetesfeeding.ui.home.fragment.obgyn

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
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.ui.home.fragment.ObeservationAdapter
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getCurrentDateInString
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_observation.*
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.fragment_symptoms.recyclerViewMonitorBloodGlucose
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


class ObservationFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    var isObservationScored:Boolean = false
    val observationScore: Int = 20
    var observationList: List<Observation> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                observationList =  AppDatabase(requireActivity().applicationContext).getObgynDao().getAllObservation()
                showObservation(observationList)
            }
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getScoreByCategory(6).isNotEmpty()){
                    for (i in AppDatabase(it).getHomeMenusDao().getScoreByCategory(6).indices){
                        val date = AppDatabase(it).getHomeMenusDao().getScoreByCategory(6)[i].date_time
                        val dateToString = date.toString().split("T")
                        var currentDate = it.getCurrentDateInString()
                        if (currentDate.equals(dateToString[0])) {
                            isObservationScored = true
                            return@let
                        }
                    }
                }
            }

        }
        mcv_observation_done.setOnClickListener {
            if (!isObservationScored){
                updateScore()
                requireActivity().shortToast("Score Updated")
            }else{
                view?.snackbar("Already Saved For Today")
                requireActivity().onBackPressed()
            }
        }
    }

    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 6, observationScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }

    private fun showObservation(observations: List<Observation>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            ObeservationAdapter(requireActivity().applicationContext,observations)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observation, container, false)
    }

}
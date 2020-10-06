package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ObservationBreastFeed
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ProgressDailyObservation
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.DailyObservationsAdapter
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_daily_observation.*
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime


var observationList: List<ObservationBreastFeed> = ArrayList()

class DailyObservationFragment : Fragment() {

        val dailyObsevartion:Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_observation, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            observationList =  AppDatabase(requireActivity().applicationContext).getBreastFeedingDao().getAllObservation()
            showObservations(observationList)
        }
        mcv_done.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                (context as Activity).onBackPressed()
            }else
            updateScore()
           //requireActivity().onBackPressed()
        }
    }

    private fun showObservations(observations: List<ObservationBreastFeed>) {
        rv_dailyObservation.layoutManager = LinearLayoutManager(activity)
        rv_dailyObservation.adapter =
            DailyObservationsAdapter(
                requireContext(),
                observations
            )
    }

    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 13, dailyObsevartion, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }


}